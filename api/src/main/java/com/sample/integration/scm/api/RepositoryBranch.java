package com.synopsys.integration.scm.api;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
/**
 * Repository Branch class consists of all common fields from all SCM providers with respect to branches of a repository.
 * The values for these fields are coming from SCM APIs response and some fields name is different in SCM API responses.
 */
public class RepositoryBranch implements Serializable {

    private String name;
    private String sha;
    private Boolean isLocked;

    public RepositoryBranch() {}

    public RepositoryBranch(String name, String sha) {
        this.name = name;
        this.sha = sha;
    }
    // This Constructor is for ADO Branches
    public RepositoryBranch(String name, String sha, Boolean isLocked) {
        this.name = name;
        this.sha = sha;
        this.isLocked = isLocked;
    }

    public String getName() {
        return name;
    }

    public String getSha() {
        return sha;
    }

    public Boolean getIsLocked() { return isLocked; }
}
