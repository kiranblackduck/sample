package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.api.pagination.PagingParams;
import com.synopsys.integration.scm.api.pagination.ScmPage;
import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Interface to handle all methods with projects.
 * This interface is used to retrieve projects from SCM providers using organization name
 *
 * As of now its applicable only to Azure cloud
 */
public interface ProjectService {
    ScmPage<Project> retrievePage(AccessToken accessToken, RepositoryID repositoryID, PagingParams pagingParams) throws ScmException;
    Project retrieveOne(AccessToken accessToken, String url) throws ScmException;
    void setOAuthRefreshTokenCallback(Consumer<Optional<OAuth2AccessTokenData>> var1);
}
