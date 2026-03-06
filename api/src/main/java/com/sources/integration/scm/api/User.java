package com.synopsys.integration.scm.api;

import java.io.Serializable;
import java.util.Optional;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String ORG_NAME = "orgName";
    private static final String ORG_TYPE = "orgType";

    private final String name;
    private final String username;
    private final String orgName;
    private final String orgType;

    public User(String name, String username, String orgName, String orgType) {
        this.name = name;
        this.username = username;
        this.orgName = orgName;
        this.orgType = orgType;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public String getUsername() {
        return username;
    }

    public Optional<String> getOrgName() {
        return Optional.ofNullable(orgName);
    }

    public Optional<String> getOrgType() {
        return Optional.ofNullable(orgType);
    }

    @Override
    public String toString() {
        return "User{" +
                NAME + "='" + name + '\'' +
                ", " + USERNAME + "='" + username + '\'' +
                ", " + ORG_NAME + "='" + orgName + '\'' +
                ", " + ORG_TYPE + "='" + orgType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;

        User user = (User) o;

        return null != username ? username.equals(user.username) : null == user.username;
    }

    @Override
    public int hashCode() {
        return null != username ? username.hashCode() : 0;
    }
}
