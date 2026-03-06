package com.synopsys.integration.scm.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Request object for multi-file commit operations in SCM repositories.
 * This class encapsulates parameters needed for batch file operations
 * (create, update, delete) in a single commit.
 *
 * Supports multiple SCM providers including GitLab, GitHub, Azure DevOps, etc.
 */
public class MultiFileCommitRequest implements Serializable {
    private List<FileData> files;
    private String branch;
    private String commitMessage;
    private String authorEmail;
    private String authorName;

    public MultiFileCommitRequest() {
        this.files = new ArrayList<>();
    }

    public MultiFileCommitRequest(List<FileData> files, String branch, String commitMessage,
                                  String authorEmail, String authorName) {
        this.files = !Objects.isNull(files) ? files : new ArrayList<>();
        this.branch = branch;
        this.commitMessage = commitMessage;
        this.authorEmail = authorEmail;
        this.authorName = authorName;
    }

    public static MultiFileCommitRequest create(List<FileData> files, String branch, String commitMessage,
                                                String authorEmail, String authorName) {
        return new MultiFileCommitRequest(files, branch, commitMessage, authorEmail, authorName);
    }

    public List<FileData> getFiles() {
        return files;
    }

    public void setFiles(List<FileData> files) {
        this.files = files;
    }

    public void addFile(FileData file) {
        if (Objects.isNull(this.files)) {
            this.files = new ArrayList<>();
        }
        this.files.add(file);
    }

    public String getBranch() { return branch; }

    public void setBranch(String branch) { this.branch = branch; }

    public String getCommitMessage() { return commitMessage; }

    public void setCommitMessage(String commitMessage) { this.commitMessage = commitMessage; }

    public String getAuthorEmail() { return authorEmail; }

    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public String getAuthorName() { return authorName; }

    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
