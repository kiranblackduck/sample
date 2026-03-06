package com.synopsys.integration.scm.api;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

/**
 * Repository class consists of all common fields from all SCM providers for a source code repository.
 * The values for these fields are coming from SCM APIs response and some fields name is different in SCM API responses.
 *
 * Repository fields description
 * id: Unique identifier of the repository /projects
 * name: Name of the repository/projects
 * description:  information about the repository/projects
 * defaultBranchName: Base branch name of the repository/projects
 * browserUrl:  A unique url used to locate a repository/projects on webpage
 * cloneSshUrl: A remote cloning url of the repository/projects in SSH format
 * cloneHttpsUrl: A remote  cloning url of the repository/projects in HTTPS format
 * lastUpdatedTime: When was the repository last updated
 * createdTime: Repository created datetime
 * groupId: Name of the Organization/Project/Workspace where the repo created.
 * repoType: Stores information about the types of repository .
 * repositorySlug: A repository slug is a URL-friendly version of a repository name of Bitbucket Datacenter. For other scm's it will be set to null
     * Azure: type will be set to "Organization" while making HTTP request organization is mandatory.
     * Bitbucket Cloud :
     *       Type: User,Team
     * Bitbucket Datacenter:
     *      Type: PERSONAL,NORMAL
     * https://developer.atlassian.com/server/bitbucket/rest/v811/api-group-project/#api-api-latest-projects-projectkey-get
     * GitHub Ent /GitHub Standard  : Information about the type of repositories.
     *       Type: Organization,User
     * Gitlab Self Hosted/GitLab Cloud : Information about the group, subgroup id or username.
     *      Type: Group,User
 * size : Size of the repo.
 * isPrivateRepository: A flag which denotes about the projects/repo visibility
 * isForkedRepository: A flag which represents about forked status
 */

public class Repository implements Serializable {
    private String organizationURL;
    private String projectURL;
    private String repositorySlug;
    private String id;
    private String name;
    private String displayName;
    private String description;
    private String defaultBranchName;
    private String branchName;
    private String browserUrl;
    private String cloneSshUrl;
    private String cloneHttpsUrl;
    private Instant lastUpdatedTime;
    private Instant createdTime;
    @Deprecated(forRemoval = true)
    private String groupId;
    private String organization;
    private String project;
    // Size is not available for BitBucket data center
    private long size;
    private String repositoryType;
    private boolean isPrivateRepository;
    private boolean isForkedRepository;
    private String organizationId;
    private String projectId;

    public Repository() {
    }

    public Repository(
        String id,
        String name,
        String description,
        String defaultBranchName,
        String browserUrl,
        String cloneSshUrl,
        String cloneHttpsUrl,
        Instant lastUpdatedTime,
        String groupId,
        String repositoryType
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultBranchName = defaultBranchName;
        this.browserUrl = browserUrl;
        this.cloneSshUrl = cloneSshUrl;
        this.cloneHttpsUrl = cloneHttpsUrl;
        this.lastUpdatedTime = lastUpdatedTime;
        this.groupId = groupId;
        this.organization = groupId;
        this.repositoryType = repositoryType;
    }
    // Gitlab and GitHub Repository constructor without display name
    public  Repository(
            String id,
            String name,
            String description,
            String defaultBranchName,
            String browserUrl,
            String cloneSshUrl,
            String cloneHttpsUrl,
            Instant createdTime,
            Instant lastUpdatedTime,
            long size,
            boolean isPrivateRepository,
            boolean isForkedRepository,
            String repositoryType,
            String organizationId,
            String organization,
            String organizationURL
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultBranchName = defaultBranchName;
        this.browserUrl = browserUrl;
        this.cloneSshUrl = cloneSshUrl;
        this.cloneHttpsUrl = cloneHttpsUrl;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.size = size;
        this.isPrivateRepository = isPrivateRepository;
        this.isForkedRepository = isForkedRepository;
        this.repositoryType = repositoryType;
        this.organizationId = organizationId;
        this.groupId = organization;
        this.organization = organization;
        this.organizationURL= organizationURL;
    }
    // Gitlab Repository constructor with display name
    public  Repository(
            String id,
            String name,
            String description,
            String defaultBranchName,
            String browserUrl,
            String cloneSshUrl,
            String cloneHttpsUrl,
            Instant createdTime,
            Instant lastUpdatedTime,
            long size,
            boolean isPrivateRepository,
            boolean isForkedRepository,
            String repositoryType,
            String organizationId,
            String organization,
            String organizationURL,
            String displayName
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultBranchName = defaultBranchName;
        this.browserUrl = browserUrl;
        this.cloneSshUrl = cloneSshUrl;
        this.cloneHttpsUrl = cloneHttpsUrl;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.size = size;
        this.isPrivateRepository = isPrivateRepository;
        this.isForkedRepository = isForkedRepository;
        this.repositoryType = repositoryType;
        this.organizationId = organizationId;
        this.groupId = organization;
        this.organization = organization;
        this.organizationURL= organizationURL;
        this.displayName = displayName;
    }
// Used by Azure
    public  Repository(
            String id,
            String name,
            String description,
            String defaultBranchName,
            String branchName,
            String browserUrl,
            String cloneSshUrl,
            String cloneHttpsUrl,
            Instant createdTime,
            Instant lastUpdatedTime,
            long size,
            boolean isPrivateRepository,
            boolean isForkedRepository,
            String repositoryType,
            String organization,
            String projectId,
            String project,
            String projectURL
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultBranchName = defaultBranchName;
        this.branchName = branchName;
        this.browserUrl = browserUrl;
        this.cloneSshUrl = cloneSshUrl;
        this.cloneHttpsUrl = cloneHttpsUrl;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.projectId= projectId;
        this.groupId = project;
        this.project = project;
        this.organization = organization;
        this.size = size;
        this.isPrivateRepository = isPrivateRepository;
        this.isForkedRepository = isForkedRepository;
        this.repositoryType = repositoryType;
        this.projectURL= projectURL;
    }

    public Repository(
        String id,
        String name,
        String description,
        String defaultBranchName,
        String browserUrl,
        String cloneSshUrl,
        String cloneHttpsUrl,
        Instant createdTime,
        Instant lastUpdatedTime,
        String groupId,
        boolean isPrivateRepository,
        boolean isForkedRepository,
        String repositoryType
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultBranchName = defaultBranchName;
        this.browserUrl = browserUrl;
        this.cloneSshUrl = cloneSshUrl;
        this.cloneHttpsUrl = cloneHttpsUrl;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.groupId = groupId;
        this.organization = groupId;
        this.isPrivateRepository = isPrivateRepository;
        this.isForkedRepository = isForkedRepository;
        this.repositoryType = repositoryType;
    }
    // Bitbucket Old constructor without project details
    public Repository(
            String id,
            String name,
            String description,
            String defaultBranchName,
            String browserUrl,
            String cloneSshUrl,
            String cloneHttpsUrl,
            Instant createdTime,
            Instant lastUpdatedTime,
            String groupId,
            boolean isPrivateRepository,
            boolean isForkedRepository,
            String repositoryType,
            String organizationId,
            String organizationURL
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultBranchName = defaultBranchName;
        this.browserUrl = browserUrl;
        this.cloneSshUrl = cloneSshUrl;
        this.cloneHttpsUrl = cloneHttpsUrl;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.groupId = groupId;
        this.organization = groupId;
        this.isPrivateRepository = isPrivateRepository;
        this.isForkedRepository = isForkedRepository;
        this.repositoryType = repositoryType;
        this.organizationId = organizationId;
        this.organizationURL= organizationURL;
    }
    // Bitbucket Cloud and data center uses this constructor
    public Repository(
            String id,
            String name,
            String description,
            String defaultBranchName,
            String browserUrl,
            String cloneSshUrl,
            String cloneHttpsUrl,
            Instant createdTime,
            Instant lastUpdatedTime,
            long size,
            String groupId,
            boolean isPrivateRepository,
            boolean isForkedRepository,
            String repositoryType,
            String organizationId,
            String organizationURL,
            String project,
            String projectId,
            String projectURL
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultBranchName = defaultBranchName;
        this.browserUrl = browserUrl;
        this.cloneSshUrl = cloneSshUrl;
        this.cloneHttpsUrl = cloneHttpsUrl;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.groupId = groupId;
        this.organization = groupId;
        this.isPrivateRepository = isPrivateRepository;
        this.isForkedRepository = isForkedRepository;
        this.repositoryType = repositoryType;
        this.organizationId = organizationId;
        this.organizationURL= organizationURL;
        this.project= project;
        this.projectId= projectId;
        this.projectURL= projectURL;
    }

    @Override
    public String toString() {
        // FIXME this could be better
        return name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() { return displayName; }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public String getDefaultBranchName() {
        return defaultBranchName;
    }

    public String getbranchName() {
        return branchName;
    }

    public String getBrowserUrl() {
        return browserUrl;
    }

    public String getRepositoryType() {
        return repositoryType;
    }

    public String getCloneSshUrl() {
        return cloneSshUrl;
    }

    public String getCloneHttpsUrl() {
        return cloneHttpsUrl;
    }

    public Instant getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public long getSize() {
        return size;
    }

    public boolean isPrivateRepository() {
        return isPrivateRepository;
    }

    public boolean isForkedRepository() {
        return isForkedRepository;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }
    public String getRepositorySlug() {
        return repositorySlug;
    }
    @Deprecated(forRemoval = true)
    /*
    Use getProjectName() method instead of getProject() method
     */
    public String getProject() { return project; }
    public String getOrganization() {
        return organization;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getOrganizationURL() {
        return organizationURL;
    }

    public String getProjectURL() {
        return projectURL;
    }
    
    public String getProjectName(){
        return project;
    }
}
