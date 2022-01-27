package com.gridnine.testing;

import java.util.LinkedList;
import java.util.List;

public class BaseFilter implements Filter {

    private List<Filter> filters;

    public BaseFilter() {
        filters = new LinkedList<>();
    }

    public BaseFilter(List<Filter> filters) {
        this.filters = filters;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public boolean filter(Flight flight) {
        return filters.stream().allMatch((f) -> f.filter(flight));
    }


}
