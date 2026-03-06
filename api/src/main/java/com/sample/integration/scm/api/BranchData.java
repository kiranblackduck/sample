package com.synopsys.integration.scm.api;

import java.io.Serializable;

/**
 * Request object for branch operations in SCM repositories.
 * This class encapsulates all parameters needed for create and delete branch operations.
 * Fields are optional based on the operation being performed.
 */
public class BranchData implements Serializable {
    private String featureBranch;
    private String defaultBranch;
    // Used for ADO branch creation
    private String oldObjectId;
    private String newObjectId;

    // Default no-argument constructor for serialization frameworks.
    public BranchData() {}

    public BranchData(String featureBranch, String defaultBranch) {
        this.featureBranch = featureBranch;
        this.defaultBranch = defaultBranch;
    }
    // Used this constructor for ADO branch creation
    public BranchData(String featureBranch,String oldObjectId, String newObjectId) {
        this.featureBranch = featureBranch;
        this.oldObjectId = oldObjectId;
        this.newObjectId = newObjectId;
    }

    public static BranchData forCreate(String featureBranch, String defaultBranch) {
        return new BranchData(featureBranch, defaultBranch);
    }

    /**
     * Static factory method for creating a branch deletion request.
     *
     * @param featureBranch The name of the branch to delete
     */
    public static BranchData forDelete(String featureBranch) {
        return new BranchData(featureBranch, null);
    }

    public String getFeatureBranch() {
        return featureBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public void setFeatureBranch(String featureBranch) {
        this.featureBranch = featureBranch;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public String getOldObjectId() { return oldObjectId; }

    public void setOldObjectId(String oldObjectId) { this.oldObjectId = oldObjectId; }

    public String getNewObjectId() { return newObjectId; }

    public void setNewObjectId(String newObjectId) { this.newObjectId = newObjectId; }
}
