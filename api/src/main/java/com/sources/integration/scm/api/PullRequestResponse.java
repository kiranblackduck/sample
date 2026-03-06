package com.synopsys.integration.scm.api;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Generic Pull Request/Merge Request response object that represents a PR/MR across all SCM providers.
 * This is an IMMUTABLE response object.
 *
 * Supports:
 * - GitLab (Merge Request)
 * - GitHub (Pull Request)
 * - Bitbucket (Pull Request)
 * - Azure DevOps (Pull Request)
 */
public class PullRequestResponse implements Serializable {

    private String id;
    private String title;
    private String description;
    private String state;
    private String sourceBranch;
    private String targetBranch;
    private String webUrl;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String authorName;
    private String authorUsername;
    private String mergeStatus;
    private String sha;

    public PullRequestResponse() {
    }

    public PullRequestResponse(String id, String title, String state, String webUrl) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.webUrl = webUrl;
    }
    /**
     * Full constructor with all fields.
     *
     * @param id              Unique identifier of the PR/MR
     * @param title           Title of the PR/MR
     * @param description     Description of the PR/MR
     * @param state           State (open, closed, merged, locked)
     * @param sourceBranch    Source branch (branch with changes)
     * @param targetBranch    Target branch (branch to merge into)
     * @param webUrl          Web URL to view the PR/MR
     * @param createdAt       Creation timestamp of PR/MR
     * @param updatedAt       Last update timestamp of PR/MR
     * @param authorName      Author's display name
     * @param authorUsername  Author's username
     * @param mergeStatus     Merge status (can_be_merged, cannot_be_merged, etc.)
     * @param sha             Commit SHA of the source branch head
     */
    public PullRequestResponse(
        String id,
        String title,
        String description,
        String state,
        String sourceBranch,
        String targetBranch,
        String webUrl,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        String authorName,
        String authorUsername,
        String mergeStatus,
        String sha
    ) {
        this.id = Objects.requireNonNull(id, "Pull Request ID cannot be null");
        this.title = Objects.requireNonNull(title, "Pull Request title cannot be null");
        this.description = description;
        this.state = state;
        this.sourceBranch = Objects.requireNonNull(sourceBranch, "Source branch cannot be null");
        this.targetBranch = Objects.requireNonNull(targetBranch, "Target branch cannot be null");
        this.webUrl = webUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorName = authorName;
        this.authorUsername = authorUsername;
        this.mergeStatus = mergeStatus;
        this.sha = sha;
    }

    /**
     * Gets the unique identifier of the PR/MR.
     * - GitLab: iid (internal ID)
     * - GitHub: number
     * - Bitbucket: id
     * - Azure: pullRequestId
     *
     * @return The PR/MR identifier
     */
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getSourceBranch() {
        return sourceBranch;
    }

    public String getTargetBranch() {
        return targetBranch;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Gets the merge status.
     * - GitLab: can_be_merged, cannot_be_merged, unchecked
     * - GitHub: clean, unstable, dirty
     * - Bitbucket: SUCCESSFUL, FAILED
     * - Azure: succeeded, conflicts, queued
     */
    public String getMergeStatus() {
        return mergeStatus;
    }

    public String getSha() {
        return sha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PullRequestResponse)) return false;
        PullRequestResponse that = (PullRequestResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sourceBranch, that.sourceBranch) &&
                Objects.equals(targetBranch, that.targetBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceBranch, targetBranch);
    }

    @Override
    public String toString() {
        return String.format(
            "PullRequestResponse{id='%s', title='%s', state='%s', sourceBranch='%s', targetBranch='%s', webUrl='%s'}",
            id, title, state, sourceBranch, targetBranch, webUrl
        );
    }
}
