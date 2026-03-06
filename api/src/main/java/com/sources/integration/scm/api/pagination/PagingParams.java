package com.synopsys.integration.scm.api.pagination;

import java.util.Objects;
import java.util.Optional;

/** Represents Paging paramters.
 *
 */
public class PagingParams {
    private final int pageNumber;
    private final int itemsPerPage;
    private final SCMFilter filterTerm;
    // FIXME include sortby and order (asc, desc)

    /**
     * @param pageNumber   The page number, starting at 1. E.g. Assume <i>itemsPerPage</i> = 10, <i>pageNumber</i> = 1 will represent items 1-10, <i>pageNumber</i> = 2 will represent items 11-20, etc.
     * @param itemsPerPage The maximum number of items to be returned within a single page.
     * @return A newly initialized {@link PagingParams}.
     */
    public static PagingParams unfiltered(int pageNumber, int itemsPerPage) {
        return new PagingParams(pageNumber, itemsPerPage, null);
    }

    /**
     * @param pageNumber   The page number, starting at 1. E.g. Assume <i>itemsPerPage</i> = 10, <i>pageNumber</i> = 1 will represent items 1-10, <i>pageNumber</i> = 2 will represent items 11-20, etc.
     * @param itemsPerPage The maximum number of items to be returned within a single page.
     * @param filterTerm   The filter term to filter by which to filter the request results. Must not be null.
     * @return A newly initialized {@link PagingParams}.
     */
    public static PagingParams filtered(int pageNumber, int itemsPerPage, SCMFilter filterTerm) {
        Objects.requireNonNull(filterTerm);
        return new PagingParams(pageNumber, itemsPerPage, filterTerm);
    }

    private PagingParams(int pageNumber, int itemsPerPage, SCMFilter filterTerm) {
        this.pageNumber = pageNumber;
        this.itemsPerPage = itemsPerPage;
        this.filterTerm = filterTerm;
    }

    /**
     * @return A new {@link PagingParams} instance with the <i>pageNumber</i> incremented by 1.
     */
    public PagingParams next() {
        return new PagingParams(pageNumber + 1, itemsPerPage, filterTerm);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public Optional<SCMFilter> getFilterTerm() {
        return Optional.ofNullable(filterTerm);
    }

}
