package com.synopsys.integration.scm.api;

import com.synopsys.integration.scm.tools.AccessToken;
import com.synopsys.integration.scm.tools.exception.ScmException;
import com.synopsys.integration.scm.tools.oauth2.OAuth2AccessTokenData;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Interface for file operations in SCM repositories.
 * Provides methods to create, update, and delete files across different SCM providers.
 *
 * This interface supports workflow injection operations for GitHub Actions, BitBucket Pipelines,
 * GitLab CI, and Azure DevOps pipelines.
 *
 * @param accessToken The access token for authentication
 * @param repositoryID The repository identifier (owner/repo name)
 * @param request The file operation request containing filePath, content, branch, and commitMessage
 * @return RepositoryFileMetadata containing the created file metadata
 * @throws ScmException If the operation fails due to authentication, authorization, or network issues
 */
public interface FileService {


    // Creates a new file in the specified repository and branch.
    RepositoryFileMetadata createFile(
            AccessToken accessToken,
            RepositoryID repositoryID,
            FileCommitRequest request
    ) throws ScmException;

    // Updates an existing file in the specified repository and branch.
    RepositoryFileMetadata updateFile(
            AccessToken accessToken,
            RepositoryID repositoryID,
            FileCommitRequest request
    ) throws ScmException;

    // Retrieves file information and content from the repository.
    RepositoryFileMetadata getFile(
            AccessToken accessToken,
            RepositoryID repositoryID,
            FileCommitRequest request
    ) throws ScmException;

    // Performs a direct commit to the default branch by checking if file exists and creating or updating accordingly.
    RepositoryFileMetadata directCommit(
            AccessToken accessToken,
            RepositoryID repositoryID,
            FileCommitRequest request
    ) throws ScmException;

    // This will handle batch file operations (create, update, delete) in a single commit for GitHub, BitBucket, GitLab, and Azure DevOps.
    MultiFileCommitResponse createCommitForMultipleFiles(
        AccessToken accessToken,
        RepositoryID repositoryID,
        MultiFileCommitRequest multiFileCommitRequest
    ) throws ScmException;

    // Sets the OAuth refresh token callback for handling token refresh during operations.
    void setOAuthRefreshTokenCallback(Consumer<Optional<OAuth2AccessTokenData>> oAuthRefreshTokenCallback);
}
