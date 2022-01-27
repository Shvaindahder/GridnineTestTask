package com.gridnine.testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FlightManagerTest {

    BaseFilter filter;
    FlightManager manager;

    List<Flight> initFlights;
    List<Flight> expectedFlights;

    @BeforeEach
    void setUp() {
        filter = new BaseFilter();
        filter.addFilter(Filters.flightStartDateTimeFilter(LocalDateTime.now()));
        filter.addFilter(Filters.departureBeforeArrivalFilter());

        initFlights = FlightBuilder.createFlights();

        manager = new FlightManager(filter, initFlights);

        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        expectedFlights = Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

    @Test
    void getFilteredFlights() {
        List<Flight> filteredFlights = manager.getFilteredFlights();
        assertEquals(filteredFlights.size(), expectedFlights.size());
    }
}