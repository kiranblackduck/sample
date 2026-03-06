package com.synopsys.integration.scm.api.cloning;

/** Represents Cloning paramters */
public class CloneRepository {

    /** @param uri Specifies clone url of SCM repository, supports cloning with https url only */
    private String uri;
    /** @param directory Specifies the directory path for cloning repository */
    private String directory;
    /** @param branch Specifies the repository branch to be cloned */
    private String branch;
    /** @param depth limits the size fo your clone and checkout only the last X commits */
    private int depth;

    public CloneRepository(String uri, String directory, String branch, int depth) {
        this.uri = uri;
        this.directory = directory;
        this.branch = branch;
        this.depth = depth;
    }

    public String getUri() {
        return uri;
    }

    public String getDirectory() {
        return directory;
    }

    public String getBranch() {
        return branch;
    }

    public int getDepth() {
        return depth;
    }
}
