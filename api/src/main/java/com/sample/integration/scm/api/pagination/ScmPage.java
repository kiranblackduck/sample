package com.synopsys.integration.scm.api.pagination;

import java.io.Serializable;
import java.util.List;

public class ScmPage<T extends Serializable> {
    private List<T> items;
    private PagingParams pageInfo;
    private boolean hasNextPage;
    private int totalCount;

    public ScmPage() {
        // For serialization
    }

    public ScmPage(List<T> items, PagingParams context, boolean hasNextPage) {
        this.items = items;
        this.pageInfo = context;
        this.hasNextPage = hasNextPage;
    }

    public ScmPage(List<T> items, PagingParams context, boolean hasNextPage, int totalCount) {
        this.items = items;
        this.pageInfo = context;
        this.hasNextPage = hasNextPage;
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public PagingParams getPageInfo() {
        return pageInfo;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
