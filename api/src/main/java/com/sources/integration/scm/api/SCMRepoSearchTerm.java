package com.synopsys.integration.scm.api;

import com.google.gson.JsonObject;
import com.synopsys.integration.scm.tools.ErrorCodes;
import com.synopsys.integration.scm.tools.exception.InvalidInputException;

import java.util.Arrays;
import java.util.Map;

/**
 * interface to add search string and pass it to SCM providers in order to search given string in the repositories.
 * such as name, description and other fields depending upon on the SCM providers.
 * SEARCH_STRING will be used to search repositories with repository name
 * ORG_NAME will be used to search repositories inside Organization(GitHub)/Group(GitLab)/Project(Bitbucket Data Center)/Workspace(Bitbucket Cloud)
 */
public interface SCMRepoSearchTerm {

    public enum SearchCriteria {
        SEARCH_STRING,
        ORG_NAME;
    }

    default void isEmptySearchTermMap(Map<SearchCriteria, String> searchTerms, String attributeNames) throws InvalidInputException {
        if (null == searchTerms || searchTerms.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", String.format("Validation failed for: %s", attributeNames));
            throw new InvalidInputException(ErrorCodes.UNPROCESSABLE_ENTITY, jsonObject.toString());
        }
    }
    public Map<SearchCriteria, String> getSearchTerms();
}
