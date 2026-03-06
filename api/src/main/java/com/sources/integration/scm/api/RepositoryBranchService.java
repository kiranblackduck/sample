package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.api.pagination.PagingParams;
import com.synopsys.integration.scm.api.pagination.ScmPage;
import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Interface to handle all methods with respect to branches of a repository.
 */
public interface RepositoryBranchService {

    /**
     * Defining method to retrieve all branches for given repository ID.
     * @param accessToken  Specifies  Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param repositoryID Specifies which Scm provider identity need to be used to fetch branch. E.g. GitHub - Repository owner & Repository.
     * @param pagingParams Specifies which page of (filtered) repositories to retrieve.
     * @return {@link ScmPage<RepositoryBranch>} A page of repository branches, filtered by the provided parameters.
     * @throws ScmException When http communication fails for specific Scm
     */
    ScmPage<RepositoryBranch> retrievePage(AccessToken accessToken, RepositoryID repositoryID, PagingParams pagingParams) throws ScmException;

    /**
     * Defining method to retrieve single branch for given repository ID
     * @param accessToken  Specifies Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * or
     * {@link com.synopsys.integration.scm.tools.pat.PATTokenData}
     * @param repositoryID Specifies which Scm provider identity need to be used to fetch branch. E.g. GitHub - Repository owner & Repository.
     * @param branch       Specifies which branch details need to fetched
     * @return {@link RepositoryBranch }A repository branch
     * @throws ScmException         When http communication fails for specific Scm
     */
    RepositoryBranch retrieveOne(AccessToken accessToken, RepositoryID repositoryID, String branch) throws ScmException;

    /**
     * Defining method to create a new branch for given repository ID
     * @param accessToken  Specifies Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * @param repositoryID Specifies which Scm provider identity need to be used to create branch. E.g. GitHub - Repository owner & Repository.
     * @param branchData Specifies the branch creation parameters (branch name and ref)
     * @return {@link RepositoryBranch} The created repository branch
     * @throws ScmException         When http communication fails for specific Scm
     */
    RepositoryBranch createBranch(AccessToken accessToken, RepositoryID repositoryID, BranchData branchData) throws ScmException;

    /**
     * Defining method to delete a branch for given repository ID
     * @param accessToken  Specifies Access Token Data
     * {@link com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData}
     * @param repositoryID Specifies which Scm provider identity need to be used to delete branch. E.g. GitHub - Repository owner & Repository.
     * @param branchData Specifies the branch deletion parameters (branch name)
     * @throws ScmException         When http communication fails for specific Scm
     */
    void deleteBranch(AccessToken accessToken, RepositoryID repositoryID, BranchData branchData) throws ScmException;

    void setOAuthRefreshTokenCallback(Consumer<Optional<OAuth2AccessTokenData>> oAuthRefreshTokenCallback);


}
