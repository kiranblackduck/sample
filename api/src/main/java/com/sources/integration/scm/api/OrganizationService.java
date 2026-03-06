package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.api.pagination.PagingParams;
import com.synopsys.integration.scm.api.pagination.ScmPage;
import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Interface to handle all methods with user's organizations.
 */
public interface OrganizationService {

    /**
     * Method to retrieve organizations.
     * Note: Applicable only to Azure cloud as it requires RepositoryID info.
     *
     * @param accessToken  Specifies Access Token Data
     *                     {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     *                     or
     *                     {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param repositoryID Consists of Organization name
     * @param pagingParams Specifies the page number.
     * @return {@link ScmPage<Organization>}
     * @throws ScmException
     */
    @Deprecated
    ScmPage<Organization> retrievePage(AccessToken accessToken, RepositoryID repositoryID, PagingParams pagingParams) throws ScmException;

    /**
     * Defining method definition to retrieve organizations/groups/projects/workspaces.
     *
     * @param accessToken  Specifies Access Token Data
     *                     {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     *                     or
     *                     {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param pagingParams Specifies the page number.
     * @return A page of organizations.
     */
    ScmPage<Organization> retrievePage(AccessToken accessToken, PagingParams pagingParams)
            throws ScmException;
    /**
     * Defining method definition to retrieve organizations/groups/projects/workspaces with Query param filter.
     *
     * @param accessToken  Specifies Access Token Data
     *                     {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     *                     or
     *                     {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param pagingParams Specifies the page number.
     * @return A page of organizations.
     */
    ScmPage<Organization> retrievePage(AccessToken accessToken, PagingParams pagingParams, SCMQueryParam queryParam)
            throws ScmException;
    /**
     * Defining method definition to retrieve organization/group/project/workspace by URL.
     *
     * @param accessToken  Specifies Access Token Data
     *                     {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     *                     or
     *                     {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     */
    Organization getOrganizationByUrl(AccessToken accessToken, String url) throws ScmException;

    /**
     * Defining method definition to retrieve organization/group/project/workspace by Name.
     *
     * @param accessToken  Specifies Access Token Data
     *                     {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     *                     or
     *                     {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     */
    Organization getOrganizationByName(AccessToken accessToken, String OrgName) throws ScmException;

    void setOAuthRefreshTokenCallback(Consumer<Optional<OAuth2AccessTokenData>> oAuthRefreshTokenCallback);
}