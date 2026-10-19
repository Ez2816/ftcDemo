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
        return true;
    }

    @Override
    public void clear() {
        pending.clear();
    }

    /** What the Driver Station showed after the last update(). */
    public List<String> lines() {
        return screen;
    }
}
