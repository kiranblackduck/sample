package com.synopsys.integration.scm.api.pagination;

import com.synopsys.integration.scm.tools.enums.OrganizationTypeEnum;
import com.synopsys.integration.scm.tools.enums.VisibilityEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converter for transforming AdvancedFilter to query parameter maps.
 * Follows Single Responsibility Principle by handling only conversion logic.
 */
public class FilterMapConverter {

    /**
     * Converts an AdvancedFilter to a map of query parameters.
     *
     * @param filter the filter to convert
     * @return immutable map of query parameters
     */
    public static Map<String, String> toQueryParameters(AdvancedFilter filter) {
        Map<String, String> map = new HashMap<>();

        addAccountFilters(map, filter);
        addCriteriaFilters(map, filter);
        addVisibilityFilters(map, filter);
        addSearchTermFilter(map, filter);

        return Map.copyOf(map);
    }

    private static void addAccountFilters(Map<String, String> map, AdvancedFilter filter) {
        addIfNotNull(map, "account_name", filter.getOrganizationName());

        OrganizationTypeEnum organizationType = filter.getOrganizationType();
        if (organizationType != null) {
            map.put("type", organizationType.organizationTypeValue);
        }
    }

    private static void addCriteriaFilters(Map<String, String> map, AdvancedFilter filter) {
        for (String criterionName : filter.getActiveCriteria()) {
            addCriteriaToMap(map, filter, criterionName);
        }
    }

    private static void addCriteriaToMap(Map<String, String> map, AdvancedFilter filter, String criterionName) {
        List<String> includeValues = filter.getIncludeValues(criterionName);
        if (!includeValues.isEmpty()) {
            String key = criterionName + "_include";
            String value = String.join(",", includeValues);
            map.put(key, value);
        }

        List<String> excludeValues = filter.getExcludeValues(criterionName);
        if (!excludeValues.isEmpty()) {
            String key = criterionName + "_exclude";
            String value = String.join(",", excludeValues);
            map.put(key, value);
        }
    }

    private static void addVisibilityFilters(Map<String, String> map, AdvancedFilter filter) {
        List<VisibilityEnum> visibilities = filter.getVisibilityList();
        if (!visibilities.isEmpty()) {
            String visibilityValues = visibilities.stream()
                .map(v -> v.visibilityValue)
                .collect(Collectors.joining(","));
            map.put("visibility", visibilityValues);
        }
    }

    private static void addSearchTermFilter(Map<String, String> map, AdvancedFilter filter) {
        addIfNotNull(map, "search_term", filter.getSearchTerm());
    }

    private static void addIfNotNull(Map<String, String> map, String key, String value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}