package com.qualcomm.robotcore.util;

public final class Range {
    private Range() {}

    public static double clip(double number, double min, double max) {
        return Math.max(min, Math.min(max, number));
    }

    public static float clip(float number, float min, float max) {
        return Math.max(min, Math.min(max, number));
    }

    public static int clip(int number, int min, int max) {
        return Math.max(min, Math.min(max, number));
    }

    /** Linearly maps n from the range [x1, x2] to the range [y1, y2]. */
    public static double scale(double n, double x1, double x2, double y1, double y2) {
        double a = (y1 - y2) / (x1 - x2);
        double b = y1 - x1 * a;
        return n * a + b;
    }
}
