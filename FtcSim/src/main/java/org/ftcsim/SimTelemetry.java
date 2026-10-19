package org.ftcsim;

import java.util.ArrayList;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Records what would appear on the Driver Station screen. */
public class SimTelemetry implements Telemetry {
    private static final Item ITEM = new Item() {};
    private static final Line LINE = new Line() {};

    private final List<String> pending = new ArrayList<>();
    private List<String> screen = List.of();
    private boolean echo;

    @Override
    public Item addData(String caption, Object value) {
        pending.add(caption + " : " + value);
        return ITEM;
    }

    @Override
    public Item addData(String caption, String format, Object... args) {
        return addData(caption, String.format(format, args));
    }

    @Override
    public Line addLine(String lineCaption) {
        pending.add(lineCaption);
        return LINE;
    }

    @Override
    public boolean update() {
        screen = List.copyOf(pending);
        pending.clear();
        if (echo) {
            System.out.printf("[t=%6.2fs] %s%n", SimClock.seconds(), String.join(" | ", screen));
        }
        return true;
    }

    @Override
    public void clear() {
        pending.clear();
    }

    /** Print every update() to the console. */
    public void setEcho(boolean echo) {
        this.echo = echo;
    }

    /** What the Driver Station showed after the last update(). */
    public List<String> lines() {
        return screen;
    }

    /** The value shown next to a caption, or null if that caption is not on screen. */
    public String value(String caption) {
        String prefix = caption + " : ";
        for (String line : screen) {
            if (line.startsWith(prefix)) {
                return line.substring(prefix.length());
            }
        }
        return null;
    }
}
