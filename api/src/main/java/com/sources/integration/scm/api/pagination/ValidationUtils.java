package com.synopsys.integration.scm.api.pagination;

import com.synopsys.integration.scm.tools.validator.CommonValidator;

import java.util.Collection;
import java.util.Objects;

/**
 * Utility class for common validation operations.
 * Follows DRY principle by centralizing validation logic.
 */
public final class ValidationUtils {

    private ValidationUtils() {
        // Utility class
    }

    /**
     * Validates that a string is not null or empty after trimming.
     *
     * @param value the string to validate
     * @param fieldName the field name for error messages
     * @throws IllegalArgumentException if value is null or empty
     */
    public static void requireNonBlank(String value, String fieldName) {
        if (CommonValidator.isBlank(value)) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    /**
     * Validates that a collection is not null or empty.
     *
     * @param collection the collection to validate
     * @param fieldName the field name for error messages
     * @throws IllegalArgumentException if collection is null or empty
     */
    public static void requireNonEmpty(Collection<?> collection, String fieldName) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    /**
     * Validates that an object is not null.
     *
     * @param object the object to validate
     * @param fieldName the field name for error messages
     * @throws IllegalArgumentException if object is null
     */
    public static void requireNonNull(Object object, String fieldName) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }

    /**
     * Trims a string and returns null if empty.
     *
     * @param value the string to trim
     * @return trimmed string or null if empty
     */
    public static String trimToNull(String value) {
        if (CommonValidator.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    /**
     * Normalizes a criterion name (trim and lowercase).
     *
     * @param criterionName the criterion name
     * @return normalized criterion name
     */
    public static String normalizeCriterionName(String criterionName) {
        requireNonBlank(criterionName, "Criterion name");
        return criterionName.trim().toLowerCase();
    }
}