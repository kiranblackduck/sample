package com.synopsys.integration.scm.api;

import java.io.Serializable;

import static com.synopsys.integration.scm.tools.CommonConstants.SCM_FILE_ENCODING;

/**
 * Request object for file commit operations in SCM repositories.
 * This class encapsulates all parameters needed for create and update file operations.
 *
 * This is a MUTABLE request object with selective setters for fields that may need
 * programmatic modification after deserialization.
 */
public class FileCommitRequest implements Serializable {
    private String filePath;
    private String content;
    private String branch;
    private String encoding ;  // Constant - always base64 for security
    private String commitMessage;
    private String authorEmail;
    private String authorName;
    private String oldObjectId;
    private String changeType;
    private String contentType;
    private String sha;  // SHA of existing file for update operations

    // Default no-argument constructor required for JSON deserialization frameworks.
    public FileCommitRequest() {}

    /**
     * All-args constructor for programmatic creation.
     *
     * @param filePath      The file path - Required for all operations
     * @param content       The file content - Required for create/update
     * @param branch        The branch name - Required for all operations
     * @param commitMessage The commit message - Required for create/update
     * @param authorEmail   The author's email address
     * @param authorName    The author's name
     */
    public FileCommitRequest(String filePath, String content, String branch, String commitMessage,
                             String authorEmail, String authorName) {
        this.filePath = filePath;
        this.content = content;
        this.branch = branch;
        this.encoding = SCM_FILE_ENCODING;
        this.commitMessage = commitMessage;
        this.authorEmail = authorEmail;
        this.authorName = authorName;
    }
    // This Constructor is for ADO File operations
    public FileCommitRequest(String filePath, String content, String branch, String commitMessage,
                             String oldObjectId, String changeType,String contentType) {
        this.filePath = filePath;
        this.content = content;
        this.branch = branch;
        this.encoding = SCM_FILE_ENCODING;
        this.commitMessage = commitMessage;
        this.oldObjectId = oldObjectId;
        this.changeType = changeType;
        this.contentType = contentType;
    }

    // Static factory method for creating a file creation request.
    public static FileCommitRequest forCreate(String filePath, String content, String branch,
                                              String commitMessage, String authorEmail, String authorName) {
        return new FileCommitRequest(filePath, content, branch, commitMessage, authorEmail, authorName);
    }

    // Static factory method for creating a ADO File
    public static FileCommitRequest forCreate(String filePath, String content, String branch,
                                              String commitMessage, String oldObjectId, String changeType,String contentType) {
        return new FileCommitRequest(filePath, content, branch, commitMessage, oldObjectId, changeType,contentType);
    }

    // Static factory method for creating a file update request.
    public static FileCommitRequest forUpdate(String filePath, String content, String branch,
                                              String commitMessage, String authorEmail, String authorName) {
        return new FileCommitRequest(filePath, content, branch, commitMessage, authorEmail, authorName);
    }

    public String getFilePath() {
        return filePath;
    }

    public String getContent() {
        return content;
    }

    public String getBranch() {
        return branch;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getOldObjectId() { return oldObjectId; }

    public String getChangeType() { return changeType; }

    public String getContentType() { return contentType; }

    public String getSha() { return sha; }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setOldObjectId(String oldObjectId) { this.oldObjectId = oldObjectId; }

    public void setChangeType(String changeType) { this.changeType = changeType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public void setSha(String sha) { this.sha = sha; }
}
