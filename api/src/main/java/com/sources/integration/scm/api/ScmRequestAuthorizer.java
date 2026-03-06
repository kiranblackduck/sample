package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface ScmRequestAuthorizer {

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_VALUE_TOKEN = "token %s";

    boolean requiresRefresh(HttpResponse<?> response);

    OAuth2AccessTokenData refreshAuth(OAuth2AccessTokenData accessToken) throws ScmException;

    HttpRequest.Builder appendAuth(AccessToken accessToken, HttpRequest.Builder requestBuilder) throws ScmException;

}
