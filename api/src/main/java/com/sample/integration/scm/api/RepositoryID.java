package com.synopsys.integration.scm.api;

/***
 * RepositoryID classes is used to process the identifier concepts for various scm provider to fetch results.
 * i.e ->
 * ---------------------------GITHUB STANDARD AND GITHUB ENTERPRISE------------------------------
 * In {@code GHERepositoryService}  & {@code GHSRepositoryService } class
 *          repositoryOwner stores provided organization name and owner information interchangeably.
 *          identifier      stores repository information.
 *
 * -----------------------------GITLAB------------------------------------------------------------
 *
 * In {@code  GLSHRepositoryService}
 *          repositoryOwner stores groupId of gitlab groups.
 *          identifier      stores projects information
 *
 * ____________________________BITBUCKET DATA CENTER_____________________________________________
 *
 *In {@code  BBDCRepositoryService}
 *          repositoryOwner stores project slug/id of projects.
 *          identifier      stores repository slug information
 *
 *  ____________________________AZURE_____________________________________________________________
 *          repositoryOwner stores organization name/ID
 *          project         stores project name/ID
 *          identifier      stores repository name/ID
 *
 *  <a href="https://sig-confluence.internal.synopsys.com/pages/viewpage.action?pageId=818329236">confluence link</a>
 */
public class RepositoryID {

    private final String identifier;
    @Deprecated(forRemoval=true)
    private final String repositoryOwner;
    private final String organization;
    private final String project;

    private RepositoryID(String repositoryOwner, String project, String identifier) {
        this.repositoryOwner = repositoryOwner;
        this.organization = repositoryOwner;
        this.identifier = identifier;
        this.project = project;
    }

    /***
     *@Deprecated(forRemoval = true)
     * use {@link #byOrganizationAndProjectAndIdentifier(String, String, String)} instead.
     * creates a {@link RepositoryID} instance using the provided attributes.
     * @param repositoryOwner provided ownername/orgname/groupId.
     * @param project provided project name/ID
     * @param identifier provided repoName/projectID.
     * @return {@link RepositoryID}
     */
    @Deprecated(forRemoval=true)
    public static RepositoryID byRepositoryOwnerProjectIdentifier(String repositoryOwner, String project, String identifier) {
        return new RepositoryID(repositoryOwner, project, identifier);
    }

    /***
     * creates a {@link RepositoryID} instance using the provided attributes.
     * @param organization provided ownername/orgname/groupId.
     * @param project provided project name/ID
     * @param identifier provided repoName/projectID.
     * @return {@link RepositoryID}
     */
    // Only for Azure specific
    public static RepositoryID byOrganizationAndProjectAndIdentifier(String organization, String project, String identifier) {
        return new RepositoryID(organization, project, identifier);
    }

    /***
     * creates a {@link RepositoryID} instance using the provided attributes.
     * use {@link #byOrganizationAndProject(String, String)} instead.
     * @param repositoryOwner provided ownername/orgname/groupId.
     * @param project provided project name/ID
     * @return {@link RepositoryID}
     */
    @Deprecated(forRemoval=true)
    public static RepositoryID byRepositoryOwnerAndProject(String repositoryOwner, String project) {
        return new RepositoryID(repositoryOwner, project, null);
    }
    /***
     * creates a {@link RepositoryID} instance using the provided attributes.
     * @param organization provided ownername/orgname/groupId.
     * @param project provided project name/ID
     * @return {@link RepositoryID}
     */
    public static RepositoryID byOrganizationAndProject(String organization, String project) {
        return new RepositoryID(organization, project, null);
    }

    /***
     * creates a {@link RepositoryID} instance using the provided attributes.
     * use {@link #byOrganizationAndIdentifier(String, String)} instead.
     * @param repositoryOwner provided ownername/orgname/groupId.
     * @param identifier provided repoName/projectID.
     * @return {@link RepositoryID}
     */
    @Deprecated(forRemoval=true)
    public static RepositoryID byRepositoryOwnerAndIdentifier(String repositoryOwner, String identifier) {
        return new RepositoryID(repositoryOwner, null, identifier);
    }

    /***
     * creates a {@link RepositoryID} instance using the provided attributes.
     * @param organization provided ownername/orgname/groupId.
     * @param identifier provided repoName/projectID.
     * @return {@link RepositoryID}
     */
    public static RepositoryID byOrganizationAndIdentifier(String organization, String identifier) {
        return new RepositoryID(organization, null, identifier);
    }
    /**
     * Constructor to initialize the {@link RepositoryID} by Id
     * This is mandatory attribute to retrieve the branches for a given repository.
     *
     * @param identifier unique identifier of an repository.
     * @return {@link RepositoryID}
     */
    public static RepositoryID byIdentifier(String identifier) {
        return new RepositoryID(null,null, identifier);
    }

    /**
     * Constructor to initialize the {@link RepositoryID} by Id
     * use {@link #byOrganization(String)} instead.
     * This is mandatory attribute to retrieve the branches for a given repository.
     *
     * @param repositoryOwner unique identifier of an owner/org information.
     * @return {@link RepositoryID}
     */
    @Deprecated(forRemoval=true)
    public static RepositoryID byRepositoryOwner(String repositoryOwner) {
        return new RepositoryID(repositoryOwner,null, null);
    }

    /**
     * Constructor to initialize the {@link RepositoryID} by Id
     * This is mandatory attribute to retrieve the branches for a given repository.
     *
     * @param organization unique identifier of an owner/org information.
     * @return {@link RepositoryID}
     */
    public static RepositoryID byOrganization(String organization) {
        return new RepositoryID(organization,null, null);
    }

    /***
     * @return a repositoryOwner.
     */
    public String getOrganization() {
        return organization;
    }

    /***
     * @return a identifier.
     */
    public String getIdentifier() {
        return identifier;
    }


    /***
     * use {@link #getOrganization()} instead.
     * @return a repositoryOwner.
     */
    @Deprecated(forRemoval=true)
    public String getRepositoryOwner() {
        return repositoryOwner;
    }

    /**
     * @return project name/ID
     */
    public String getProject() {
        return project;
    }
}
