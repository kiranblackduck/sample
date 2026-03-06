package com.synopsys.integration.scm.api;

import java.io.Serializable;

/**
 * License class represents a software license with key and name information.
 * The values for these fields are coming from SCM APIs response.
 *
 * License fields description:
 * key: Unique identifier of the license (e.g., "mit", "apache-2.0")
 * name: Human-readable name of the license (e.g., "MIT License", "Apache License 2.0")
 */
public class License implements Serializable {
    private final String key;
    private final String name;
    
    public License(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}