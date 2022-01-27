package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Filters {
    /**
     * Util class which representing base filters
     */
    public static Filter flightStartDateTimeFilter(LocalDateTime dateTime) {
        // Method represents filter, which suppose to choose min start datetime of flight
        return (Flight flight) ->
                flight.getSegments().stream()
                        .limit(1)
                        .allMatch(segment -> segment.getDepartureDate().compareTo(dateTime) >= 0);
    }

    public static Filter departureBeforeArrivalFilter() {
        return flight ->
                flight.getSegments().stream()
                        .noneMatch(segment -> segment
                                .getArrivalDate()
                                .compareTo(segment.getDepartureDate()) < 0
                        );
    }

    public static Filter flightEndDateTimeFilter(LocalDateTime dateTime) {
        return (Flight flight) ->
                flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().compareTo(dateTime) <= 0);
    }

    public static Filter flightDurationFilter(Duration duration) {
        // Set max duration of flight
        return (Flight flight) -> {
            List<Segment> segments = flight.getSegments();
            Duration flightDuration = Duration.between(
                    segments.get(0).getDepartureDate(),
                    segments.get(segments.size() - 1).getArrivalDate()
            );
            return flightDuration.compareTo(duration) <= 0;
        };
    }

    public static Filter countOfSegments(int maxCountOfSegments) {
        return (Flight flight) ->
                flight.getSegments().size() / 2 <= maxCountOfSegments;
    }

    public static Filter timeInFly(Duration duration) {
        return (Flight flight) ->
                flight.getSegments().stream()
                        .map((a) -> Duration.between(a.getArrivalDate(), a.getDepartureDate()))
                        .reduce(Duration.ZERO, (a, b) -> a.plus(b))
                        .compareTo(duration) <= 0;
    }

    public static Filter timeInGround(Duration maxDurationInGround) {
        return (Flight flight) -> {
            Duration currentDuration = Duration.ZERO;
            List<Segment> segments = flight.getSegments();
            for (int i = 0; i < segments.size() - 1; i++) {
                currentDuration = currentDuration.plus(Duration.between(
                        segments.get(i).getArrivalDate(),
                        segments.get(i + 1).getDepartureDate()
                ));
            }
            return currentDuration.compareTo(maxDurationInGround) <= 0;
        };
    }
}
