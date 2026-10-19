package org.ftcsim;

/**
 * Simulated time. Nothing in the sim uses the real wall clock, so tests are
 * fast and give the same result every run.
 */
public final class SimClock {
    /** Every hardware or timer read costs this much simulated time, like real code does. */
    public static final double READ_COST_SECONDS = 0.001;

    private static long nanos;
    private static SimRobot world;

    private SimClock() {}

    static void attach(SimRobot robot) {
        world = robot;
        nanos = 0;
    }

    public static long nanoTime() {
        return nanos;
    }

    public static double seconds() {
        return nanos / 1e9;
    }

    /** Moves time forward and lets the robot's physics catch up. */
    public static void advance(double seconds) {
        nanos += Math.round(seconds * 1e9);
        if (world != null) {
            world.onTimeAdvanced(seconds);
        }
    }

    public static void costOfRead() {
        advance(READ_COST_SECONDS);
    }
}
