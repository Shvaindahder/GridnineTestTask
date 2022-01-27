package com.gridnine.testing;

@FunctionalInterface
public interface Filter {
    boolean filter(Flight flight);
}
