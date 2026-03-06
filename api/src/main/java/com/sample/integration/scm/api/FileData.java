package com.synopsys.integration.scm.api;

import java.io.Serializable;

import static com.synopsys.integration.scm.tools.CommonConstants.SCM_FILE_ENCODING;

/**
 * Represents individual file data for multi-file operations.
 * Used within MultiFileCommitRequest for batch file operations.
 * Supports create, update, and delete actions across different SCM providers.
 */
public class FileData implements Serializable {
    private String filePath;
    private String content;
    private String sha;
    private String encoding;  // base64 encoding
    private String oldObjectId;  // Optional: for Azure DevOps
    private String changeType;  // Optional: for Azure DevOps
    private String contentType;  // Optional: for Azure DevOps
    private String action;  // create, update, or delete

    public FileData() {
        this.encoding = SCM_FILE_ENCODING;  // Default to base64
    }
    
    public FileData(String filePath, String content) {
        this.filePath = filePath;
        this.content = content;
        this.encoding = SCM_FILE_ENCODING;
    }

    // Static factory method for creating a file.
    public static FileData forCreate(String filePath, String content) {
        FileData fileData = new FileData(filePath, content);
        fileData.setAction("create");
        return fileData;
    }

    // Static factory method for updating a file.
    public static FileData forUpdate(String filePath, String content) {
        FileData fileData = new FileData(filePath, content);
        fileData.setAction("update");
        return fileData;
    }

    // Static factory method for deleting a file.
    public static FileData forDelete(String filePath) {
        FileData fileData = new FileData();
        fileData.setFilePath(filePath);
        fileData.setAction("delete");
        return fileData;
    }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getSha() { return sha; }

    public void setSha(String sha) { this.sha = sha; }

    public String getOldObjectId() { return oldObjectId; }

    public void setOldObjectId(String oldObjectId) { this.oldObjectId = oldObjectId; }

    public String getEncoding() { return encoding; }

    public void setEncoding(String encoding) { this.encoding = encoding; }

    public String getChangeType() { return changeType; }

    public void setChangeType(String changeType) { this.changeType = changeType; }

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action; }
}
