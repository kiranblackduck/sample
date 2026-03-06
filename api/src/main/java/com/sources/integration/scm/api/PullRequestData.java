package com.synopsys.integration.scm.api;

import java.io.Serializable;

/**
 * Data class for creating pull requests across different SCM providers.
 * Contains all necessary information to create a pull request.
 */
public class PullRequestData implements Serializable {

    private String title;
    private String description;
    private String sourceBranch;
    private String targetBranch;
    private boolean removeSourceBranch;

    public PullRequestData() {}

    public PullRequestData(String title, String description, String sourceBranch, String targetBranch) {
        this.title = title;
        this.description = description;
        this.sourceBranch = sourceBranch;
        this.targetBranch = targetBranch;
        this.removeSourceBranch = false;
    }

    public PullRequestData(String title, String description, String sourceBranch, String targetBranch, boolean removeSourceBranch) {
        this.title = title;
        this.description = description;
        this.sourceBranch = sourceBranch;
        this.targetBranch = targetBranch;
        this.removeSourceBranch = removeSourceBranch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceBranch() {
        return sourceBranch;
    }

    public void setSourceBranch(String sourceBranch) {
        this.sourceBranch = sourceBranch;
    }

    public String getTargetBranch() {
        return targetBranch;
    }

    public void setTargetBranch(String targetBranch) {
        this.targetBranch = targetBranch;
    }

    public boolean removeSourceBranch() {
        return removeSourceBranch;
    }

    public void setremoveSourceBranch(boolean removeSourceBranch) {
        this.removeSourceBranch = removeSourceBranch;
    }
}