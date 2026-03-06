package com.synopsys.integration.scm.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Represents a Azure Project and Bitbucket Cloud for a given Organization.
 */
public class Project implements Serializable {

    /**
     * Unique identifier of the project.
     */
    @SerializedName("id")
    private String id;

    /**
     * Name of the project.
     */
    @SerializedName("name")
    private String name;

    /**
     * Description of the project.
     */
    @SerializedName("description")
    private String description;


    /**
     * URL of the project.
     */
    @SerializedName("url")
    private String url;

    private String webUrl;

    @SerializedName("organizationName")
    private String organizationName;

    /**
     * State of the project.
     */
    @SerializedName("state")
    private String state;

    /**
     * Revision number of the project.
     */
    @SerializedName("revision")
    private int revision;

    /**
     * Visibility status of the project.
     */
    @SerializedName("visibility")
    private String visibility;

    /**
     * Visibility status of the project.
     */
    @SerializedName("isPrivate")
    private Boolean isPrivate;

    /**
     * Created time of the project.
     */
    @SerializedName("createdTime")
    private String createdTime;


    /**
     * Last update time of the project.
     */
    @SerializedName("lastUpdateTime")
    private String lastUpdateTime;

    public Project(String id, String name,String url,String organizationName, String state, int revision, String visibility, String lastUpdateTime, String webUrl) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.organizationName = organizationName;
        this.state = state;
        this.revision = revision;
        this.visibility = visibility;
        this.lastUpdateTime = lastUpdateTime;
        this.webUrl = webUrl;
    }

    // Below constructor is used in Bitbucket Cloud
    public Project(String id, String name,String description,String url,String organizationName, Boolean isPrivate, String createdTime,String lastUpdateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.organizationName = organizationName;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.lastUpdateTime = lastUpdateTime;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getOrganizationName() {return organizationName;}

    public String getState() {
        return state;
    }

    public int getRevision() {
        return revision;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getWebUrl() { return webUrl; }

    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }

    public String getCreatedTime() { return createdTime; }

    public String getDescription() { return description; }

    public void getIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }

}
