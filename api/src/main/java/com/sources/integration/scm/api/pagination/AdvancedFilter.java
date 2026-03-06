package com.synopsys.integration.scm.api.pagination;

import com.synopsys.integration.scm.tools.enums.OrganizationTypeEnum;
import com.synopsys.integration.scm.tools.enums.FilterEnum;
import com.synopsys.integration.scm.tools.enums.VisibilityEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.synopsys.integration.scm.api.pagination.ValidationUtils.normalizeCriterionName;
import static com.synopsys.integration.scm.api.pagination.ValidationUtils.requireNonBlank;
import static com.synopsys.integration.scm.api.pagination.ValidationUtils.requireNonEmpty;
import static com.synopsys.integration.scm.api.pagination.ValidationUtils.requireNonNull;
import static com.synopsys.integration.scm.api.pagination.ValidationUtils.trimToNull;


/**
 * Advanced filter that provides filtering capabilities for searches.
 *
 * @see FilterCriteria for include/exclude value handling
 * @see AdvancedFilter.Builder for construction details
 */
public class AdvancedFilter extends SCMFilter {
    
    /** Optional organization name for filtering repositories by specific user/organization */
    private final String organizationName;

    /** Organization type constraint (USER or ORGANIZATION) */
    private final OrganizationTypeEnum organizationType;

    /** Map of search criteria (language, topic, license, etc.) to their include/exclude values */
    private final Map<String, FilterCriteria> criteria;

    /** List of visibility constraints (PUBLIC, PRIVATE) */
    private final List<VisibilityEnum> visibilities;

    /** Optional search term for searching repositories by name, description, or other searchable fields */
    private final String searchTerm;

    private AdvancedFilter(Builder builder, Map<String, FilterCriteria> builtCriteria) {
        this.organizationName = trimToNull(builder.organizationName);
        this.organizationType = builder.organizationType;
        this.criteria = Map.copyOf(builtCriteria);
        this.visibilities = List.copyOf(builder.visibilities);
        this.searchTerm = trimToNull(builder.searchTerm);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this filter into a map of query parameters for API requests.
     *
     * @return immutable map of filter parameters
     */
    public Map<String, String> getFilterMap() {
        return FilterMapConverter.toQueryParameters(this);
    }

    /**
     * Returns the set of filter types that are active in this filter.
     *
     * @return EnumSet of active filter types
     */
    public EnumSet<FilterEnum> getFilters() {
        EnumSet<FilterEnum> filters = EnumSet.noneOf(FilterEnum.class);

        if (Objects.nonNull(organizationType)) {
            filters.add(FilterEnum.TYPE);
        }

        if (!visibilities.isEmpty()) {
            filters.add(FilterEnum.VISIBILITY);
        }

        if (hasAdvancedSearchCriteria()) {
            filters.add(FilterEnum.ADVANCED_SEARCH_QUERY);
        }

        return filters;
    }

    /**
     * Checks if this filter has any advanced search criteria that warrant the ADVANCED_SEARCH_QUERY flag.
     */
    private boolean hasAdvancedSearchCriteria() {
        return !criteria.isEmpty() || Objects.nonNull(organizationName) || Objects.nonNull(searchTerm);
    }


    // ========== Public Getters ==========


    /** @return the organization name filter, or null if not set */
    public String getOrganizationName() {
        return organizationName;
    }

    /** @return the organization type filter, or null if not set */
    public OrganizationTypeEnum getOrganizationType() {
        return organizationType;
    }

    /** @return immutable list of visibility filters */
    public List<VisibilityEnum> getVisibilityList() {
        return visibilities;
    }

    /** @return the search term for repository search, or null if not set */
    public String getSearchTerm() {
        return searchTerm;
    }

    /**
     * Gets the include values for a specific criterion.
     *
     * @param criterionName the criterion to query (e.g., "language", "topic")
     * @return immutable list of include values, empty if criterion not found
     */
    public List<String> getIncludeValues(String criterionName) {
        FilterCriteria criterion = criteria.get(criterionName);
        return Objects.nonNull(criterion) ? criterion.getIncludeValues() : Collections.emptyList();
    }

    /**
     * Gets the exclude values for a specific criterion.
     *
     * @param criterionName the criterion to query (e.g., "language", "topic")
     * @return immutable list of exclude values, empty if criterion not found
     */
    public List<String> getExcludeValues(String criterionName) {
        FilterCriteria criterion = criteria.get(criterionName);
        return Objects.nonNull(criterion) ? criterion.getExcludeValues() : Collections.emptyList();
    }


    /**
     * Returns the set of criteria that have active include or exclude values.
     *
     * @return immutable set of active criterion names
     */
    public Set<String> getActiveCriteria() {
        return criteria.entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty())
            .map(Map.Entry::getKey)
            .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Builder for constructing AdvancedFilter instances with fluent API.
     *
     * <p>The builder provides a fluent interface for specifying filter criteria. Include/exclude
     * patterns must be specified using the {@code filter(criterionName).include()/.exclude()}
     * pattern for type safety and better readability.
     *
     * <p>Example usage:
     * <pre>
     * AdvancedFilter filter = AdvancedFilter.builder()
     *     .withOrganization("myorg")
     *     .filter("language").include("Java", "Python")
     *     .filter("topic").exclude("deprecated")
     *     .allPublicRepositoriesOnly()
     *     .build();
     * </pre>
     */
    public static class Builder {

        /** Optional organization name constraint */
        private String organizationName;

        /** Optional organization type constraint */
        private OrganizationTypeEnum organizationType;

        /** Map of criteria names to their builder objects - built during build() */
        private final Map<String, FilterCriteria.Builder> criteriaBuilders = new HashMap<>();

        /** List of visibility constraints */
        private List<VisibilityEnum> visibilities = new ArrayList<>();

        /** Optional search term for repository search */
        private String searchTerm;

        /**
         * Creates a new builder.
         */
        private Builder() {
        }
        

        /**
         * Filters repositories by a specific organization.
         *
         * @param organizationName the organization name
         * @return this builder for chaining
         */
        public Builder withOrganization(String organizationName) {
            return setAccountType(organizationName, OrganizationTypeEnum.ORGANIZATION);
        }

        /**
         * Filters repositories by a specific user.
         *
         * @param userName the user name
         * @return this builder for chaining
         */
        public Builder withUser(String userName) {
            return setAccountType(userName, OrganizationTypeEnum.USER);
        }

        private Builder setAccountType(String name, OrganizationTypeEnum type) {
            requireNonBlank(name, "Account name");
            if (Objects.nonNull(organizationType) && organizationType != type) {
                throw new IllegalStateException(String.format(
                    "Cannot change account type from %s to %s. Use a new builder instance for different account types.",
                    organizationType.name().toLowerCase(), type.name().toLowerCase()));
            }
            this.organizationName = name.trim();
            this.organizationType = type;
            return this;
        }


        private Builder setCriteria(String criterionName, boolean include, List<String> values) {
            if (values == null || values.isEmpty()) {
                return this; // Skip empty values
            }

            String normalized = normalizeCriterionName(criterionName);

            // Get existing criterion builder or create new one
            FilterCriteria.Builder criterionBuilder = criteriaBuilders.computeIfAbsent(normalized,
                k -> FilterCriteria.builder());

            // Set new values (will be merged with existing values)
            if (include) {
                criterionBuilder.includeValues(values);
            } else {
                criterionBuilder.excludeValues(values);
            }

            return this;
        }


        // ========== Fluent Criteria API ==========

        /**
         * Creates a fluent criteria builder for the specified criterion name.
         *
         * <p>Example usage:
         * <pre>
         * builder.filter("language").include("Java", "Python")
         *        .filter("topic").exclude("deprecated")
         * </pre>
         *
         * @param criterionName the criterion name (e.g., "language", "topic", "license")
         * @return a new CriteriaBuilder for fluent method chaining
         * @throws IllegalArgumentException if criterionName is null or empty
         */
        public CriteriaBuilder filter(String criterionName) {
            String normalizedCriterion = normalizeCriterionName(criterionName);

            return new CriteriaBuilder(this, normalizedCriterion);
        }


        /**
         * Fluent criteria builder for include/exclude operations on a specific criterion.
         *
         * <p>This class provides a type-safe, immutable builder for specifying include and exclude
         * values for a single criterion. Each instance is bound to a specific criterion name
         * and parent builder.
         *
         * <p><strong>Thread Safety:</strong> This class is immutable and thread-safe.
         *
         * @since 1.0
         */
        public static final class CriteriaBuilder {
            private final Builder parentBuilder;
            private final String criterionName;

            /**
             * Creates a new CriteriaBuilder for the specified criterion.
             *
             * @param parentBuilder the parent builder to return to after operations
             * @param criterionName the normalized criterion name (must not be null or empty)
             * @throws IllegalArgumentException if parameters are invalid
             */
            private CriteriaBuilder(Builder parentBuilder, String criterionName) {
                requireNonNull(parentBuilder, "Parent builder");
                requireNonBlank(criterionName, "Criterion name");

                this.parentBuilder = parentBuilder;
                this.criterionName = criterionName;
            }

            /**
             * Includes repositories that match any of the specified values for this criterion.
             *
             * @param values the values to include (must not be null or empty)
             * @return this CriteriaBuilder for continued method chaining on the same criterion
             * @throws IllegalArgumentException if values are null or empty
             */
            public CriteriaBuilder include(String... values) {
                requireNonEmpty(List.of(values), "Include values");
                parentBuilder.setCriteria(criterionName, true, List.of(values));
                return this;
            }

            /**
             * Includes repositories that match any of the specified values for this criterion.
             *
             * @param values the list of values to include (must not be null or empty)
             * @return this CriteriaBuilder for continued method chaining on the same criterion
             * @throws IllegalArgumentException if values are null or empty
             */
            public CriteriaBuilder include(List<String> values) {
                requireNonEmpty(values, "Include values");
                parentBuilder.setCriteria(criterionName, true, values);
                return this;
            }

            /**
             * Excludes repositories that match any of the specified values for this criterion.
             *
             * @param values the values to exclude (must not be null or empty)
             * @return this CriteriaBuilder for continued method chaining on the same criterion
             * @throws IllegalArgumentException if values are null or empty
             */
            public CriteriaBuilder exclude(String... values) {
                requireNonEmpty(List.of(values), "Exclude values");
                parentBuilder.setCriteria(criterionName, false, List.of(values));
                return this;
            }

            /**
             * Excludes repositories that match any of the specified values for this criterion.
             *
             * @param values the list of values to exclude (must not be null or empty)
             * @return this CriteriaBuilder for continued method chaining on the same criterion
             * @throws IllegalArgumentException if values are null or empty
             */
            public CriteriaBuilder exclude(List<String> values) {
                requireNonEmpty(values, "Exclude values");
                parentBuilder.setCriteria(criterionName, false, values);
                return this;
            }

            /**
             * Gets the criterion name this builder is bound to.
             *
             * @return the criterion name (never null or empty)
             */
            public String getCriterionName() {
                return criterionName;
            }
            

            /**
             * Creates a fluent criteria builder for the specified criterion name.
             *
             * @param criterionName the criterion name (e.g., "language", "topic", "license")
             * @return a new CriteriaBuilder for fluent method chaining
             * @throws IllegalArgumentException if criterionName is null or empty
             */
            public CriteriaBuilder filter(String criterionName) {
                return parentBuilder.filter(criterionName);
            }

            /**
             * Sets the visibility constraints for repository filtering.
             *
             * @param visibilities list of visibility types to include
             * @return the parent builder for chaining
             */
            public Builder withVisibilities(List<VisibilityEnum> visibilities) {
                return parentBuilder.withVisibilities(visibilities);
            }

            /**
             * Builds the AdvancedFilter with validation.
             *
             * @return a new immutable AdvancedFilter instance
             * @throws IllegalArgumentException if any criteria are invalid or conflicting
             */
            public AdvancedFilter build() {
                return parentBuilder.build();
            }
        }

        /**
         * Sets the visibility constraints for repository filtering.
         *
         * @param visibilities list of visibility types to include
         * @return this builder for chaining
         */
        public Builder withVisibilities(List<VisibilityEnum> visibilities) {
            this.visibilities = Objects.nonNull(visibilities) ? new ArrayList<>(visibilities) : new ArrayList<>();
            return this;
        }

        /** @return builder configured to only return public repositories */
        public Builder allPublicRepositoriesOnly() {
            return withVisibilities(List.of(VisibilityEnum.PUBLIC));
        }

        /** @return builder configured to only return private repositories */
        public Builder allPrivateRepositoriesOnly() {
            return withVisibilities(List.of(VisibilityEnum.PRIVATE));
        }

        /**
         * Sets the search term for repository filtering.
         * This term will be used to search repositories by name, description, or other searchable fields.
         *
         * @param searchTerm the search term to filter repositories
         * @return this builder for chaining
         */
        public Builder withSearchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
            return this;
        }

        /**
         * Builds the AdvancedFilter with validation.
         *
         * @return a new immutable AdvancedFilter instance
         * @throws IllegalArgumentException if any criteria are invalid or conflicting
         */
        public AdvancedFilter build() {

            // Build all FilterCriteria and validate for conflicts
            Map<String, FilterCriteria> builtCriteria = new HashMap<>();
            for (Map.Entry<String, FilterCriteria.Builder> entry : criteriaBuilders.entrySet()) {
                String criterionName = entry.getKey();
                FilterCriteria criterion = entry.getValue().build();
                builtCriteria.put(criterionName, criterion);
            }

            return new AdvancedFilter(this, builtCriteria);
        }
    }
}