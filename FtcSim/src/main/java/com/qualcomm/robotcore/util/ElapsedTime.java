package com.qualcomm.robotcore.util;

import org.ftcsim.SimClock;

/** A stopwatch. In the simulator it measures simulated time, not wall-clock time. */
public class ElapsedTime {
    private long startNanos;

    public ElapsedTime() {
        reset();
    }

    public void reset() {
        startNanos = SimClock.nanoTime();
    }

    public double seconds() {
        SimClock.costOfRead();
        return (SimClock.nanoTime() - startNanos) / 1e9;
    }

    public double milliseconds() {
        return seconds() * 1000.0;
    }

    public double time() {
        return seconds();
    }

    @Override
    public String toString() {
        return String.format("%.3f seconds", (SimClock.nanoTime() - startNanos) / 1e9);
    }
}
