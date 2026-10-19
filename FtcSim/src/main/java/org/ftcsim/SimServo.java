package org.ftcsim;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class SimServo implements Servo {
    private final String name;
    private Direction direction = Direction.FORWARD;
    private double position;

    public SimServo(String name) {
        this.name = name;
    }

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public void setPosition(double position) {
        this.position = Range.clip(position, MIN_POSITION, MAX_POSITION);
    }

    @Override
    public double getPosition() {
        return position;
    }

    /** Where the servo horn actually points, after applying REVERSE. */
    public double physicalPosition() {
        return direction == Direction.FORWARD ? position : 1.0 - position;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }
}
