package com.synopsys.integration.scm.api;

import com.google.gson.JsonObject;
import com.synopsys.integration.scm.tools.CommonConstants;
import com.synopsys.integration.scm.tools.ErrorCodes;
import com.synopsys.integration.scm.tools.SuccessCodes;
import com.synopsys.integration.scm.tools.enums.RateLimitEnum;
import com.synopsys.integration.scm.tools.exception.*;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;
import com.synopsys.integration.scm.tools.pat.PATTokenData;
import com.synopsys.integration.scm.tools.validator.CommonValidator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import com.google.gson.Gson;


public class ScmHttpClient {
    public static final String ERROR_KEY = "error";
    public static final String INCORRECT_CLIENT_CREDENTIALS = "incorrect_client_credentials";
    private static Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(ScmHttpClient.class);

    public static final HttpResponse.BodyHandler<String> STRING_BODY_HANDLER = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);
    public static final String RESPONSE_BODY_FORMAT = "{\"message\":\"%s\"}";
    private final HttpClient httpClient;
    private final ScmRequestAuthorizer requestAuthorizer;

    private OAuth2CallbackHandler oAuth2CallbackHandler;

    public ScmHttpClient(HttpClient httpClient, ScmRequestAuthorizer requestAuthorizer, OAuth2CallbackHandler oAuth2CallbackHandler) {
        this.httpClient = httpClient;
        this.requestAuthorizer = requestAuthorizer;
        this.oAuth2CallbackHandler = oAuth2CallbackHandler;
    }
    public ScmHttpClient(HttpClient httpClient, ScmRequestAuthorizer requestAuthorizer) {
        this.httpClient = httpClient;
        this.requestAuthorizer = requestAuthorizer;
    }

    public <A, T> HttpResponse<T> send(A accessTokenData, HttpRequest.Builder requestBuilder, HttpResponse.BodyHandler<T> responseBodyHandler,Consumer<Optional<OAuth2AccessTokenData>> callback) throws ScmException{
        if (accessTokenData instanceof OAuth2AccessTokenData) {
            OAuth2AccessTokenData oAuth2AccessTokenData = (OAuth2AccessTokenData) accessTokenData;
            CommonValidator.validateParam(oAuth2AccessTokenData.getAccessToken(), "accessToken");
            return sendWithOAuth2(oAuth2AccessTokenData, requestBuilder, responseBodyHandler,callback);
        } else {
            PATTokenData patTokenData = (PATTokenData) accessTokenData;
            CommonValidator.validateParam(patTokenData.getAccessToken(), "accessToken");
            return sendWithPat(patTokenData, requestBuilder, responseBodyHandler);
        }
    }

    public <A, T> HttpResponse<T> send(A accessTokenData, HttpRequest.Builder requestBuilder, HttpResponse.BodyHandler<T> responseBodyHandler) throws ScmException{
        return this.send(accessTokenData,requestBuilder,responseBodyHandler, null);
    }

    /***
     * sendWithOAuth2 process the OAuth2 request
     * @param oAuth2AccessTokenData provided {@link OAuth2AccessTokenData}
     * @param requestBuilder provided http request with URL
     * @param responseBodyHandler response body contents
     * @param callback If access token expires, It returns a newly generated access token using refresh token via callback method
     *  For GithubEnt & GithubStandard via OAuth application, access token expiration timestamp is not supported so callback need to empty or null;
     *  if callback is null then the library throws {@link AuthorizationException}
     * @return HttpResponse
     */
    public <T> HttpResponse<T> sendWithOAuth2(OAuth2AccessTokenData oAuth2AccessTokenData, HttpRequest.Builder requestBuilder, HttpResponse.BodyHandler<T> responseBodyHandler, Consumer<Optional<OAuth2AccessTokenData>> callback) throws ScmException{
        Instant now = Instant.now();
        if (oAuth2AccessTokenData.getAccessTokenExpiration().isPresent() &&
                now.isAfter(oAuth2AccessTokenData.getAccessTokenExpiration().get())) {
            logger.info("Token expired, library trying to generate new access token");
            if(callback != null && oAuth2CallbackHandler != null){
                oAuth2AccessTokenData = oAuth2CallbackHandler.refreshAuth(oAuth2AccessTokenData);
                callback.accept(Optional.of(oAuth2AccessTokenData));
                logger.debug("library generated new access token");
            }
            else{
                logger.debug("OAuth Refresh Token callback method is not provided!!! so failed to generate token");
                throw new AuthorizationException(ErrorCodes.UNAUTHORIZED, "Library tried to generate access token but failed due to missing OAuth Refresh Token callback method");
            }
        }
        HttpResponse response = buildAndSend(oAuth2AccessTokenData, requestBuilder, responseBodyHandler);
        return  response;
    }


    public <T> HttpResponse<T> sendWithPat(PATTokenData patTokenData, HttpRequest.Builder requestBuilder, HttpResponse.BodyHandler<T> responseBodyHandler) throws ScmException {
        HttpRequest request = requestAuthorizer.appendAuth(patTokenData, requestBuilder).build();
        try {
            return checkResponse(httpClient.send(request, responseBodyHandler));
        } catch (IOException | InterruptedException e) {
            throw handleException(e);
        }
    }

    public <T> HttpResponse<T> send(HttpRequest accessTokenRequest, HttpResponse.BodyHandler<T> responseBodyHandler) throws ScmException {
        try {
            return checkResponse(httpClient.send(accessTokenRequest, responseBodyHandler));
        } catch (IOException | InterruptedException e) {
            throw handleException(e);
        }
    }

    private <T> HttpResponse<T> buildAndSend(OAuth2AccessTokenData oAuth2AccessTokenData, HttpRequest.Builder requestBuilder, HttpResponse.BodyHandler<T> responseBodyHandler) throws ScmException {
        HttpRequest request = requestAuthorizer.appendAuth(oAuth2AccessTokenData, requestBuilder).build();
        logger.info("Calling SCM provider...");
        try {
            return checkResponse(httpClient.send(request, responseBodyHandler));
        } catch (IOException |InterruptedException e) {
            throw handleException(e);
        }
    }

    private ScmException handleException(Exception e) throws ScmException {
        logger.error(e.getLocalizedMessage(), e);
        if(e instanceof InterruptedException){
            throw new ScmException(ErrorCodes.INTERNAL_SERVER_ERROR, String.format(RESPONSE_BODY_FORMAT, e.getLocalizedMessage()));
        }
        else if(e instanceof ConnectException){
            throw new ScmException(ErrorCodes.SERVICE_UNAVAILABLE,String.format(RESPONSE_BODY_FORMAT,e.getCause()));
        }
        else {
            throw new ScmException(ErrorCodes.BAD_REQUEST,String.format(RESPONSE_BODY_FORMAT,e.getCause()));
        }
    }

    private <T> HttpResponse<T> checkResponse(HttpResponse<T> response) throws ScmException {
        if (!SuccessCodes.values.contains(response.statusCode())) {
            String responseBody = response.body().toString();
            int responseStatusCode = response.statusCode();
            responseStatusCode = getResponseStatusCode(responseBody, responseStatusCode);
            Map<RateLimitEnum, String> rateLimitHeaders = extractRateLimitHeaders(response);
            int rateLimitRemaining = -1;
            if (rateLimitHeaders != null && rateLimitHeaders.size() > 0) {
                String rateLimitRemainingValue = rateLimitHeaders.get(RateLimitEnum.RATE_LIMIT_REMAINING);
                rateLimitRemaining = (rateLimitRemainingValue != null && !rateLimitRemainingValue.isEmpty()) ? Integer.parseInt(rateLimitRemainingValue) : rateLimitRemaining;
            }
            ErrorCodes httpCode = ErrorCodes.valueOf(responseStatusCode);
            if (httpCode != null) {
                /** Checking the primary and secondary rate limits for GitHub. In case of primary rate limit, GitHub
                 *  will send 403 response code and in response headers x-ratelimit-remaining will be 0.
                 *  In case of secondary rate limit, GitHub will send 403 response code and response body will contain
                 *  secondary rate limit error message.*/
                if (ErrorCodes.FORBIDDEN.equals(httpCode) && (rateLimitRemaining == 0 ||
                        responseBody.contains(CommonConstants.ScmRateLimitConstants.SECONDARY_RATE_LIMIT))) {
                    throw new TooManyRequestsException(ErrorCodes.TOO_MANY_REQUESTS, responseBody, rateLimitHeaders);
                }
                switch (httpCode) {
                    case BAD_REQUEST:
                        throw new BadRequestException(httpCode, responseBody);
                    case TEMPORARILY_MOVED:
                        throw new ResourceMovedException(httpCode,responseBody);
                    case UNAUTHORIZED:
                        throw new AuthorizationException(httpCode, responseBody);
                    case FORBIDDEN:
                        throw new ForbiddenException(httpCode, responseBody);

                    case NOT_FOUND:
                    case METHOD_NOT_ALLOWED:
                    case CONFLICT:
                    case PRECONDITION_FAILED:
                        throw new ScmException(httpCode, responseBody);

                    case UNPROCESSABLE_ENTITY:
                        throw new InvalidInputException(httpCode, responseBody);

                    case TOO_MANY_REQUESTS:
                        throw new TooManyRequestsException(httpCode, responseBody, rateLimitHeaders);

                    case INTERNAL_SERVER_ERROR:
                    case NOT_IMPLEMENTED:
                    case SERVICE_UNAVAILABLE:
                        throw new ScmServiceException(httpCode, responseBody);
                    default:
                        logger.error(responseBody);
                        throw new ScmException(responseBody);
                }
            } else {
                logger.error(responseBody);
                throw new ScmException(responseBody);
            }
        }
        if (response.statusCode() == 200) {
                validateErrorMessage(response);
       }
        return response;
    }

    private int getResponseStatusCode(String responseBody, int responseStatusCode) {
        // For Azure DevOps, if the user is not authorized to access the resource, the response code should be 403 instead of 401. But Azure DevOps sends 401 response code.
        if(responseBody.contains("TF400813: The user is not authorized to access this resource") && responseStatusCode == 401){
            responseStatusCode = ErrorCodes.FORBIDDEN.getResponseCode();
        }
        return responseStatusCode;
    }


    private <T> void validateErrorMessage(HttpResponse<T> response) throws AuthorizationException {
        String responseBody = String.valueOf(response.body());
        if(!Strings.isBlank(responseBody) && !responseBody.startsWith("[")){
            JsonObject jsonobject = gson.fromJson(responseBody, JsonObject.class);
            if(jsonobject.has(ERROR_KEY) && INCORRECT_CLIENT_CREDENTIALS.equals(jsonobject.get(ERROR_KEY).getAsString())){
                logger.error(responseBody);
                throw new AuthorizationException(ErrorCodes.UNAUTHORIZED,responseBody);
            }
        }
    }

    /** Extracting rate limit headers for each SCM */
    private <T> Map<RateLimitEnum, String> extractRateLimitHeaders(HttpResponse<T> response) {
        if (response.headers() != null) {
            Map<String, List<String>> responseHeaders = response.headers().map();
            if (responseHeaders != null && !responseHeaders.isEmpty()) {
                Map<RateLimitEnum, String> rateLimitHeaders = new HashMap<>();
                responseHeaders.forEach((key, value) -> {
                    if (CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_LIMIT_KEY.equalsIgnoreCase(key) || CommonConstants.ScmRateLimitConstants.RATE_LIMIT_LIMIT_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_LIMIT, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_REMAINING_KEY.equalsIgnoreCase(key) || CommonConstants.ScmRateLimitConstants.RATE_LIMIT_REMAINING_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_REMAINING, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_USED_KEY.equalsIgnoreCase(key) || CommonConstants.ScmRateLimitConstants.RATE_LIMIT_OBSERVED_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_USED, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_RESET_KEY.equalsIgnoreCase(key) || CommonConstants.ScmRateLimitConstants.RATE_LIMIT_RESET_TIME_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_RESET_TIME, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.RATE_LIMIT_RESET_TIME_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_RESET_DATE_TIME, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.RATE_LIMIT_RETRY_AFTER_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RETRY_AFTER, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.RATE_LIMIT_NAME_KEY.equalsIgnoreCase(key) || CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_RESOURCE_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_RESOURCE, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_INTERVAL_SECONDS_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_INTERVAL_SECONDS, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_FILL_RATE_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_FILL_RATE, value.get(0));
                    }
                    if (CommonConstants.ScmRateLimitConstants.X_RATE_LIMIT_DELAY_KEY.equalsIgnoreCase(key)) {
                        rateLimitHeaders.put(RateLimitEnum.RATE_LIMIT_DELAY_MS, value.get(0));
                    }
                });
                return rateLimitHeaders;
            }
        }
        return null;
    }
}
