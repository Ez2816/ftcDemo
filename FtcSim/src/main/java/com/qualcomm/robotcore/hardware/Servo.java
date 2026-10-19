package com.qualcomm.robotcore.hardware;

public interface Servo extends HardwareDevice {
    enum Direction { FORWARD, REVERSE }

    double MIN_POSITION = 0.0;
    double MAX_POSITION = 1.0;

    /** Position from 0.0 to 1.0. Values outside that range are clipped. */
    void setPosition(double position);

    double getPosition();

    void setDirection(Direction direction);

    Direction getDirection();
}
