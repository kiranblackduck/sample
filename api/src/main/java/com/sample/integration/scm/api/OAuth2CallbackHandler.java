package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.tools.http.QueryParamCreator;
import com.synopsys.integration.scm.tools.oauth2.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Step 2 in the OAuth 2.0 flow.
 */
public abstract class OAuth2CallbackHandler {
    private final OAuth2IdentityRequestDescriptor paramDescriptor;
    private final HttpClient httpClient;
    private final OAuth2ClientData clientData;
    private final OAuth2AccessTokenDataDeserializer tokenDeserializer;

    protected final QueryParamCreator queryParamCreator;

    /**
     * @param paramDescriptor    A descriptor of the OAuth provider's parameters and endpoint.
     * @param httpClient         An {@link HttpClient} to perform the necessary requests to complete the handshake.
     * @param clientData A persistence mechanism for access tokens, implemented by the consumer of this library.
     * @param tokenDeserializer  A utility to deserialize JSON into {@link OAuth2AccessTokenData}.
     */
    public OAuth2CallbackHandler(OAuth2IdentityRequestDescriptor paramDescriptor, HttpClient httpClient, OAuth2ClientData clientData, OAuth2AccessTokenDataDeserializer tokenDeserializer) {
        this(paramDescriptor, httpClient, clientData, tokenDeserializer, QueryParamCreator.urlEncoded());
    }

    /**
     * @param paramDescriptor    A descriptor of the OAuth provider's parameters and endpoint.
     * @param httpClient         An {@link HttpClient} to perform the necessary requests to complete the handshake.
     * @param clientData A persistence mechanism for access tokens, implemented by the consumer of this library.
     * @param tokenDeserializer  A utility to deserialize JSON into {@link OAuth2AccessTokenData}.
     * @param queryParamCreator  A common instance of a {@link QueryParamCreator}.
     */
    public OAuth2CallbackHandler(
        OAuth2IdentityRequestDescriptor paramDescriptor,
        HttpClient httpClient, OAuth2ClientData clientData,
        OAuth2AccessTokenDataDeserializer tokenDeserializer,
        QueryParamCreator queryParamCreator
    ) {
        this.paramDescriptor = paramDescriptor;
        this.httpClient = httpClient;
        this.clientData = clientData;
        this.tokenDeserializer = tokenDeserializer;
        this.queryParamCreator = queryParamCreator;
    }

    /**
     * Handler method to make a call to  url
     *
     * @param state    The string provided in the original {@link OAuth2IdentityRequest}.
     * @param authCode The string sent to your Callback URI as a query parameter called "code". The name of the parameter can be found in your {@link OAuth2IdentityRequestDescriptor}.
     * @return A new {@link OAuth2AccessTokenData} instance containing a freshly retrieved access token and its metadata.
     */
    public abstract OAuth2AccessTokenData handle(String state, String authCode) throws ScmException;

    public abstract OAuth2AccessTokenData handle(String state, String authCode,String tenantId, Boolean isEntraAccess) throws ScmException;

    protected URI buildRequestURI(String state, String code, OAuth2ClientData clientData) {
        OAuth2IdentityRequestDescriptorData oAuth2IdentityRequestDescriptorData = paramDescriptor.getOAuth2IdentityRequestDescriptorData();
        String stateParam = queryParamCreator.createQueryParam(oAuth2IdentityRequestDescriptorData.getStateParamName(), state);
        String codeParam = queryParamCreator.createQueryParam(oAuth2IdentityRequestDescriptorData.getAuthCodeParamName(), code);
        String clientIdParam = queryParamCreator.createQueryParam(oAuth2IdentityRequestDescriptorData.getClientIdParamName(), clientData.getClientId());

        // FIXME handle this more sensitively?
        String clientSecretParam = queryParamCreator.createQueryParam(oAuth2IdentityRequestDescriptorData.getClientSecretParamName(), new String(clientData.getClientSecret()));

        String combinedParams = String.join("&", stateParam, codeParam, clientIdParam, clientSecretParam);
        String requestUrl = String.format("%s%s?%s", clientData.getBaseUrl(), oAuth2IdentityRequestDescriptorData.getTokenRequestEndpointSpec(), combinedParams);
        return URI.create(requestUrl);
    }

    protected HttpRequest buildRequest(URI requestURI) {
        return HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(requestURI)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();
    }

    protected OAuth2AccessTokenData getOAuth2AccessTokenData(HttpRequest accessTokenRequest, OAuth2ClientData clientData) throws ScmException {
        ScmHttpClient scmHttpClient = new ScmHttpClient(httpClient, null,null);
        HttpResponse<String> accessTokenResponse = scmHttpClient.send(accessTokenRequest, HttpResponse.BodyHandlers.ofString());
        OAuth2AccessTokenData oAuth2AccessTokenData = tokenDeserializer.fromJson(accessTokenResponse.body());
        oAuth2AccessTokenData.setOAuth2ClientData(clientData);
        return oAuth2AccessTokenData;
    }

    public abstract OAuth2AccessTokenData refreshAuth(OAuth2AccessTokenData oAuth2AccessTokenData) throws ScmException;
}
