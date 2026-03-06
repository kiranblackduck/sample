package com.synopsys.integration.scm.api;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Response object representing a repository commit.
 * Contains commit details returned by SCM providers after a successful commit operation.
 */
public class MultiFileCommitResponse implements Serializable {
    private String id;           // Commit SHA/ID
    private String shortId;      // Short commit SHA
    private String title;        // Commit message title
    private String message;      // Full commit message
    private String authorName;
    private String authorEmail;
    private ZonedDateTime createdAt;
    private String webUrl;       // URL to view the commit

    // Default no-argument constructor for JSON deserialization.
    public MultiFileCommitResponse() {}

    // All-args constructor.
    public MultiFileCommitResponse(String id, String shortId, String title, String message,
                         String authorName, String authorEmail, ZonedDateTime createdAt, String webUrl) {
        this.id = id;
        this.shortId = shortId;
        this.title = title;
        this.message = message;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.createdAt = createdAt;
        this.webUrl = webUrl;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getShortId() { return shortId; }

    public void setShortId(String shortId) { this.shortId = shortId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getAuthorName() { return authorName; }

    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }

    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public ZonedDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; }

    public String getWebUrl() { return webUrl; }

    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }
}
