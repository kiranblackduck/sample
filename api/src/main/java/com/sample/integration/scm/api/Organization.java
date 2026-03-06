package com.synopsys.integration.scm.api;

import java.io.Serializable;

/**
 * Organization class consists of all common fields from all SCM providers.
 * The values for these fields are coming from SCM APIs response and some fields name is different in SCM API responses.
 * <p>
 * Organization fields description
 * id: unique identifier of the organizations/groups/projects/workspaces
 * name: Name of the organizations/groups/projects/workspaces
 * description:  about the organizations/groups/projects/workspaces
 */
public class Organization implements Serializable {

    private String id;
    private String name;
    private String description;
    private String url;
    private String webUrl;
    private String displayName;

    public Organization(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    // Gitlab Organization Response without display name
    public Organization(String id, String name, String description,String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
    }
    // Gitlab Organization Response with display name
    public Organization(String id, String name, String description,String url,String displayName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.displayName = displayName;
    }
    public Organization(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getUrl() {
        return url;
    }
    public String getWebUrl() {return  webUrl;}
    public String getDisplayName() {
        return displayName;
    }
}
