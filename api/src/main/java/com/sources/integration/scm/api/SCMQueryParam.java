package com.synopsys.integration.scm.api;

import java.io.Serializable;

/**
 * SCMQueryParam class consists of all Optional Query params fields from all SCM providers.
 * <p>
 * SCMQueryParam fields description
 * topLevelOnly: optional query param to list top level groups list
 * includeSubgroups: Optional query param to include subgroups while getting repository list
 */
public class SCMQueryParam implements Serializable {

    private Boolean topLevelOnly;
    private Boolean includeSubgroups;

    public SCMQueryParam() {
        // Default constructor
    }

    public SCMQueryParam(Boolean topLevelOnly, Boolean includeSubgroups) {
        this.topLevelOnly = topLevelOnly;
        this.includeSubgroups = includeSubgroups;
    }

    public Boolean getTopLevelOnly() {
        return topLevelOnly;
    }

    public void setTopLevelOnly(Boolean topLevelOnly) {
        this.topLevelOnly = topLevelOnly;
    }

    public Boolean getIncludeSubgroups() {
        return includeSubgroups;
    }

    public void setIncludeSubgroups(Boolean includeSubgroups) {
        this.includeSubgroups = includeSubgroups;
    }
}