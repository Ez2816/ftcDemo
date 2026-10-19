package com.qualcomm.robotcore.hardware;

public interface DcMotorSimple extends HardwareDevice {
    enum Direction {
        FORWARD, REVERSE;

        public Direction inverted() {
            return this == FORWARD ? REVERSE : FORWARD;
        }
    }

    void setDirection(Direction direction);

    Direction getDirection();

    /** Power from -1.0 to 1.0. Values outside that range are clipped. */
    void setPower(double power);

    double getPower();
}
