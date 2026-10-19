package org.ftcsim;

/**
 * Where the robot is on the field, relative to where it started.
 * x = inches forward, y = inches to the left, heading = degrees counter-clockwise.
 */
public record Pose(double x, double y, double heading) {
    @Override
    public String toString() {
        return String.format("Pose(x=%.1f in, y=%.1f in, heading=%.1f deg)", x, y, heading);
    }
}
