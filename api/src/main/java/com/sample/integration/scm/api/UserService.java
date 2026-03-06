package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.util.Optional;
import java.util.function.Consumer;

public interface UserService {

    User getUserDetail(AccessToken accessToken) throws ScmException;

    void setOAuthRefreshTokenCallback(Consumer<Optional<OAuth2AccessTokenData>> oAuthRefreshTokenCallback);
}
