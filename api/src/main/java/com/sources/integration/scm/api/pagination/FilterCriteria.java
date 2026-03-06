package com.synopsys.integration.scm.api.pagination;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Immutable criteria for include/exclude filtering operations.
 *
 * <p>This class represents filtering criteria that support both include and exclude
 * operations simultaneously. It is designed to be thread-safe and performant by
 * using immutable collections and avoiding defensive copying on each access.
 *
 * <p>Key features:
 * <ul>
 *   <li>Immutable design for thread safety</li>
 *   <li>Builder pattern for fluent construction</li>
 *   <li>Automatic null and blank value filtering</li>
 *   <li>Duplicate removal for consistent results</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 * FilterCriteria criteria = FilterCriteria.builder()
 *     .includeValues("Java", "Python", "Go")
 *     .excludeValues("JavaScript")
 *     .build();
 *
 * // Or using the factory methods
 * FilterCriteria languages = FilterCriteria.withInclude("Java", "Python");
 * FilterCriteria topics = FilterCriteria.withExclude("deprecated");
 * </pre>
 *
 * @see AdvancedFilter for usage in repository filtering
 */
public final class FilterCriteria {

    /** Empty FilterCriteria instance for reuse */
    public static final FilterCriteria EMPTY = new FilterCriteria(Collections.emptyList(), Collections.emptyList());

    private final List<String> includeValues;
    private final List<String> excludeValues;

    /**
     * Private constructor - use builder() or factory methods to create instances.
     */
    private FilterCriteria(List<String> includeValues, List<String> excludeValues) {
        this.includeValues = includeValues;
        this.excludeValues = excludeValues;
    }

    // ========== Factory Methods ==========

    /**
     * Creates a new builder for constructing FilterCriteria.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates FilterCriteria with only include values.
     *
     * @param values the values to include
     * @return FilterCriteria with the specified include values
     */
    public static FilterCriteria withInclude(String... values) {
        return builder().includeValues(values).build();
    }

    /**
     * Creates FilterCriteria with only include values.
     *
     * @param values the list of values to include
     * @return FilterCriteria with the specified include values
     */
    public static FilterCriteria withInclude(List<String> values) {
        return builder().includeValues(values).build();
    }

    /**
     * Creates FilterCriteria with only exclude values.
     *
     * @param values the values to exclude
     * @return FilterCriteria with the specified exclude values
     */
    public static FilterCriteria withExclude(String... values) {
        return builder().excludeValues(values).build();
    }

    /**
     * Creates FilterCriteria with only exclude values.
     *
     * @param values the list of values to exclude
     * @return FilterCriteria with the specified exclude values
     */
    public static FilterCriteria withExclude(List<String> values) {
        return builder().excludeValues(values).build();
    }

    // ========== Public Accessors ==========

    /**
     * Returns the include values.
     *
     * @return immutable list of include values, never null
     */
    public List<String> getIncludeValues() {
        return includeValues;
    }

    /**
     * Returns the exclude values.
     *
     * @return immutable list of exclude values, never null
     */
    public List<String> getExcludeValues() {
        return excludeValues;
    }

    /**
     * Checks if this criteria has no include or exclude values.
     *
     * @return true if both include and exclude lists are empty
     */
    public boolean isEmpty() {
        return includeValues.isEmpty() && excludeValues.isEmpty();
    }

    /**
     * Checks if this criteria has include values.
     *
     * @return true if include values are present
     */
    public boolean hasIncludeValues() {
        return !includeValues.isEmpty();
    }

    /**
     * Checks if this criteria has exclude values.
     *
     * @return true if exclude values are present
     */
    public boolean hasExcludeValues() {
        return !excludeValues.isEmpty();
    }

    // ========== Object Methods ==========

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterCriteria that = (FilterCriteria) o;
        return Objects.equals(includeValues, that.includeValues) &&
               Objects.equals(excludeValues, that.excludeValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(includeValues, excludeValues);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "FilterCriteria.EMPTY";
        }
        return String.format("FilterCriteria{include=%s, exclude=%s}", includeValues, excludeValues);
    }

    // ========== Builder Class ==========

    /**
     * Builder for constructing FilterCriteria instances with fluent API.
     *
     * <p>The builder automatically filters out null and blank values and removes
     * duplicates to ensure consistent criteria.
     */
    public static class Builder {
        private List<String> includeValues = Collections.emptyList();
        private List<String> excludeValues = Collections.emptyList();

        /**
         * Sets the include values for this criteria.
         *
         * @param values the values to include (null and blank values are filtered out)
         * @return this builder for chaining
         */
        public Builder includeValues(String... values) {
            if (values == null || values.length == 0) {
                return includeValues(Collections.emptyList());
            }
            return includeValues(Arrays.asList(values));
        }

        /**
         * Sets the include values for this criteria.
         *
         * @param values the list of values to include (null and blank values are filtered out)
         * @return this builder for chaining
         */
        public Builder includeValues(List<String> values) {
            this.includeValues = cleanValues(values);
            return this;
        }

        /**
         * Sets the exclude values for this criteria.
         *
         * @param values the values to exclude (null and blank values are filtered out)
         * @return this builder for chaining
         */
        public Builder excludeValues(String... values) {
            if (values == null || values.length == 0) {
                return excludeValues(Collections.emptyList());
            }
            return excludeValues(Arrays.asList(values));
        }

        /**
         * Sets the exclude values for this criteria.
         *
         * @param values the list of values to exclude (null and blank values are filtered out)
         * @return this builder for chaining
         */
        public Builder excludeValues(List<String> values) {
            this.excludeValues = cleanValues(values);
            return this;
        }

        /**
         * Builds the FilterCriteria instance.
         *
         * @return immutable FilterCriteria instance
         */
        public FilterCriteria build() {
            if (includeValues.isEmpty() && excludeValues.isEmpty()) {
                return EMPTY;
            }

            return new FilterCriteria(includeValues, excludeValues);
        }

        /**
         * Cleans and validates the input values by filtering out null/blank values
         * and removing duplicates.
         *
         * @param values the input values to clean
         * @return immutable list of cleaned values
         */
        private static List<String> cleanValues(List<String> values) {
            if (values == null || values.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> cleanedValues = values.stream()
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isBlank())
                    .distinct()
                    .collect(Collectors.toUnmodifiableList());

            return cleanedValues.isEmpty() ? Collections.emptyList() : cleanedValues;
        }

    }
}