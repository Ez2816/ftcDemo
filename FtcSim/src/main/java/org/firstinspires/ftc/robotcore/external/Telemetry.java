package org.firstinspires.ftc.robotcore.external;

/** Sends text to the Driver Station screen. Nothing shows up until update() is called. */
public interface Telemetry {
    interface Item {}

    interface Line {}

    Item addData(String caption, Object value);

    Item addData(String caption, String format, Object... args);

    Line addLine(String lineCaption);

    boolean update();

    void clear();
}
