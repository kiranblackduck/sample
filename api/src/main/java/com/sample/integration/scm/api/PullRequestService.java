package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Interface for pull request operations across SCM providers.
 * Provides common pull request functionality including creating branches, pull requests, and merging.
 */
public interface PullRequestService{
    /**
     * Create a new pull request
     * @param accessToken Access token for authentication
     * @param repositoryID Repository identifier
     * @param pullRequestData Pull request data including title, description, source and target branches
     * @return Created pull request response
     * @throws ScmException if pull request creation fails
     */
    PullRequestResponse createPullRequest(AccessToken accessToken, RepositoryID repositoryID, PullRequestData pullRequestData) throws ScmException;

    /**
     * Creates a branch, adds/updates a file, and creates a pull request in a single workflow.
     * @param accessToken Access token for authentication
     * @param repositoryID Repository identifier
     * @param branchData Branch creation parameters
     * @param fileCommitRequest File content, commit details, and filePath
     * @param pullRequestData Pull request parameters
     * @return PullRequestResponse containing the created pull request details
     * @throws ScmException if any step in the workflow fails
     */
    PullRequestResponse createBranchWithFileAndPullRequest(AccessToken accessToken, RepositoryID repositoryID, BranchData branchData, FileCommitRequest fileCommitRequest, PullRequestData pullRequestData) throws ScmException;

    PullRequestResponse createBranchWithMultipleFilesAndPullRequest(
        AccessToken accessToken,
        RepositoryID repositoryID,
        BranchData branchData,
        MultiFileCommitRequest multiFileCommitRequest,
        PullRequestData pullRequestData
    ) throws ScmException;  

    // Sets the OAuth refresh token callback for handling token refresh during operations.
    void setOAuthRefreshTokenCallback(Consumer<Optional<OAuth2AccessTokenData>> oAuthRefreshTokenCallback);
}