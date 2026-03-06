package com.synopsys.integration.scm.api;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import static com.synopsys.integration.scm.tools.CommonConstants.SCM_FILE_ENCODING;

/**
 * Represents a file in an SCM repository with metadata such as path, content, and SHA.
 * Used for file operations like create, update, and delete.
 * This is an IMMUTABLE response object.
 */
public class RepositoryFileMetadata implements Serializable {


    private String path;
    private String content;
    private String sha;
    private String branch;
    private ZonedDateTime lastModified;
    private String encoding;

    /**
     * The response is used for getting file metadata from SCM repository.
     * @param path Path to the file
     * @param content File content
     * @param sha SHA hash of the file
     * @param branch Branch name
     */
    public RepositoryFileMetadata(String path, String content, String sha, String branch) {
        this.path = Objects.requireNonNull(path, "File path cannot be null");
        this.content = content;
        this.sha = sha;
        this.branch = Objects.requireNonNull(branch, "Branch cannot be null");
        this.lastModified = ZonedDateTime.now();
        this.encoding = encoding != null ? encoding : SCM_FILE_ENCODING;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }

    public String getSha() {
        return sha;
    }

    public String getBranch() {
        return branch;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public String getEncoding() {
        return encoding;
    }

    public boolean hasValidSha() {
        return sha != null && !sha.trim().isEmpty();
    }

    public String getFileName() {
        if (path == null) return null;
        int lastSlash = path.lastIndexOf('/');
        return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
    }

    public String getDirectory() {
        if (path == null) return "";
        int lastSlash = path.lastIndexOf('/');
        return lastSlash >= 0 ? path.substring(0, lastSlash) : "";
    }

    public boolean isWorkflowFile() {
        if (path == null) return false;
        return path.startsWith(".github/workflows/") ||
                path.equals("bitbucket-pipelines.yml") ||
                path.equals(".gitlab-ci.yml") ||
                path.equals("azure-pipelines.yml");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepositoryFileMetadata)) return false;
        RepositoryFileMetadata repositoryFileMetaData = (RepositoryFileMetadata) o;
        return Objects.equals(path, repositoryFileMetaData.path) &&
                Objects.equals(sha, repositoryFileMetaData.sha) &&
                Objects.equals(branch, repositoryFileMetaData.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, sha, branch);
    }

    @Override
    public String toString() {
        return String.format("RepositoryFileMetadata{path='%s', branch='%s', sha='%s', encoding='%s'}",
                path, branch, sha, encoding);
    }
}
