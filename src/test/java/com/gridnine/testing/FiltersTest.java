package com.gridnine.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {
    BaseFilter baseFilter;

    FlightManager flightManager;

    List<Flight> initFlights;
    //A normal flight with two hour duration
    //A normal multi segment flight
    //A flight departing in the past
    //A flight that departs before it arrives
    //A flight with more than two hours ground time
    //Another flight with more than two hours ground time

    @BeforeAll
    void setUp() {
        initFlights = FlightBuilder.createFlights();
        flightManager = new FlightManager(initFlights);
    }

    @BeforeEach
    void setUpEach() {
        baseFilter = new BaseFilter();
    }

    @Test
    void flightStartDateTimeFilter() {
        LocalDateTime now = LocalDateTime.now();
        baseFilter.addFilter(Filters.flightStartDateTimeFilter(now));
        flightManager.setFilters(baseFilter);
        List<Flight> expectedFlights =
    }

    @Test
    void departureBeforeArrivalFilter() {
    }

    @Test
    void flightEndDateTimeFilter() {
    }

    @Test
    void flightDurationFilter() {
    }

    @Test
    void countOfSegments() {
    }

    @Test
    void timeInFly() {
    }

    @Test
    void timeInGround() {
    }
}