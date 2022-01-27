package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

public class FlightManager {
    private BaseFilter filters;

    private List<Flight> flights;

    public FlightManager(List<Flight> flights) {
        filters = new BaseFilter();
        this.flights = flights;
    }

    public FlightManager(BaseFilter filters, List<Flight> flights) {
        this.filters = filters;
        this.flights = flights;
    }

    public List<Flight> getFilteredFlights() {
        return flights.stream()
                .filter(filters::filter)
                .collect(Collectors.toList());
    }

    public BaseFilter getFilters() {
        return filters;
    }

    public void setFilters(BaseFilter filters) {
        this.filters = filters;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }


}

