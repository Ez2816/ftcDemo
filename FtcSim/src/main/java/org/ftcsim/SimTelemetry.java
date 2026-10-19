package org.ftcsim;

import java.util.ArrayList;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Pretend Driver Station telemetry. Accepts lines like the real one; the tests don't read them. */
public class SimTelemetry implements Telemetry {
    private static final Item ITEM = new Item() {};
    private static final Line LINE = new Line() {};

    private final List<String> pending = new ArrayList<>();

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
        pending.clear();
        return true;
    }

    @Override
    public void clear() {
        pending.clear();
    }
}
