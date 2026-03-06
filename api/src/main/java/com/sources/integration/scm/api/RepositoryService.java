package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.api.cloning.CloneRepository;
import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.api.pagination.PagingParams;
import com.synopsys.integration.scm.api.pagination.ScmPage;
import com.synopsys.integration.scm.api.SCMQueryParam;
import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Interface to handle all methods with respect to repositories.
 */
public interface RepositoryService {
    /**
     * Defining method definition to retrieve repositories by group/Organization ID
     * @param accessToken     Specifies Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param repositoryID Specifies which group of repositories to retrieve. E.g. Organization, Project, Workspace, etc.
     * @param pagingParams    Specifies which page of (filtered) repositories to retrieve.
     * @return A page of repositories, filtered by the provided parameters.
     */
    ScmPage<Repository> retrievePage(AccessToken accessToken, RepositoryID repositoryID, PagingParams pagingParams)
            throws ScmException;

    /**
     * Defining method definition to retrieve all repositories
     * @param accessToken  Specifies Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param pagingParams Specifies which page of (filtered) repositories to retrieve.
     * @return A page of repositories, filtered by the provided parameters and authenticated user.
     */
    ScmPage<Repository> retrievePage(AccessToken accessToken, PagingParams pagingParams) throws ScmException;

    /**
     * Defining method definition to search terms in repositories
     * @param accessToken  Specifies  Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param pagingParams Specifies which page of (filtered) repositories to retrieve.
     * @param searchTerm   Specified search term object with search string
     * @return
     */
    ScmPage<Repository> retrievePage(AccessToken accessToken, PagingParams pagingParams, SCMRepoSearchTerm searchTerm)
            throws ScmException;
    /**
     * Defining method definition to filter repositories using search term and inclideSubgroups query param
     * @param accessToken  Specifies  Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param pagingParams Specifies which page of (filtered) repositories to retrieve.
     * @param searchTerm   Specified search term object with search string
     * @param scmQueryParam   Specified additional query parameters for the request
     * @return
     */
    ScmPage<Repository> retrievePage(AccessToken accessToken, PagingParams pagingParams, SCMRepoSearchTerm searchTerm, SCMQueryParam queryParam)
            throws ScmException;

    ScmPage<Repository> retrievePageWithAdvancedFilter(AccessToken accessToken,
                                                       PagingParams pagingParams) throws ScmException;

    /**
     * Defining method definition to get single repository.
     * @param accessToken Specifies Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @return A repository, filtered by the provided repository ID.
     */
    Repository retrieveOne(AccessToken accessToken, RepositoryID repositoryID)
            throws ScmException;


     void setOAuthRefreshTokenCallback(Consumer<Optional<OAuth2AccessTokenData>> oAuthRefreshTokenCallback);

    /**
     * Defining method definition to clone repository with specific branch
     * @param accessTokenData Specifies Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param cloneRepository Specifies required parameters for cloning
     * {@link com.synopsys.integration.scm.api.cloning.CloneRepository}
     */
    String cloneRepository(AccessToken accessTokenData, CloneRepository cloneRepository) throws ScmException;
    
    List<License> getLicenses(AccessToken accessToken, PagingParams pagingParams) throws ScmException;
}
