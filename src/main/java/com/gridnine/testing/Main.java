package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static List<Flight> getCorrectFlights(List<Flight> flights) {
        BaseFilter filters = new BaseFilter();
        filters.addFilter(flight ->
                flight.getSegments().stream()
                        .noneMatch(segment -> segment
                                .getArrivalDate()
                                .compareTo(segment.getDepartureDate()) < 0
                        )
        );

        FlightManager flightManager = new FlightManager(filters, flights);

        return flightManager.getFilteredFlights();
    }

    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightManager flightManager = new FlightManager(flights);

        System.out.println("Original List: ");
        for (Flight flight: flights) {
            System.out.println(flight);
        }
        System.out.println("---------------------------");

        System.out.println("Expected flights");

        BaseFilter expectedFlightsBaseFilter = new BaseFilter();
        expectedFlightsBaseFilter.addFilter(Filters.flightStartDateTimeFilter(LocalDateTime.now()));
        flightManager.setFilters(expectedFlightsBaseFilter);

        List<Flight> expectedFlights = flightManager.getFilteredFlights();

        for (Flight flight: expectedFlights) {
            System.out.println(flight);
        }

        System.out.println("---------------------------");

        System.out.println("Departure before arrival");
        List<Flight> departureBeforeArrivalFlights = getCorrectFlights(flights);
        for (Flight flight: departureBeforeArrivalFlights) {
            System.out.println(flight);
        }
        System.out.println("---------------------------");

        System.out.println("In ground less than 2 hours");

        BaseFilter twoHoursInGroundBaseFilter = new BaseFilter();
        twoHoursInGroundBaseFilter.addFilter(Filters.timeInGround(Duration.ofHours(2)));
        flightManager.setFilters(twoHoursInGroundBaseFilter);

        List<Flight> flightsWith2HoursInGround = flightManager.getFilteredFlights();
        for (Flight flight: flightsWith2HoursInGround) {
            System.out.println(flight);
        }
        System.out.println("---------------------------");

        System.out.println("Many filters (ALL)");

        BaseFilter allFilters = new BaseFilter();
        allFilters.addFilter(Filters.flightStartDateTimeFilter(LocalDateTime.now()));
        allFilters.addFilter(Filters.timeInGround(Duration.ofHours(2)));
        allFilters.addFilter(Filters.departureBeforeArrivalFilter());

        flightManager.setFilters(allFilters);

        List<Flight> allFiltersFlight = flightManager.getFilteredFlights();

        for (Flight flight: allFiltersFlight) {
            System.out.println(flight);
        }

    }
}
