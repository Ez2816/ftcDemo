package org.ftcsim;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/** A pretend DC motor. It remembers the power the code gave it. */
public class SimMotor implements DcMotor {
    private final String name;
    private Direction direction = Direction.FORWARD;
    private ZeroPowerBehavior zeroPowerBehavior = ZeroPowerBehavior.BRAKE;
    private double power;
    private double requestedPower;

    public SimMotor(String name) {
        this.name = name;
    }

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setPower(double power) {
        requestedPower = power;
        this.power = Range.clip(power, -1.0, 1.0);
    }

    @Override
    public double getPower() {
        return power;
    }

    /** The raw value passed to setPower(), before clipping. Catches math that asks for more than 100%. */
    public double getRequestedPower() {
        return requestedPower;
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        this.zeroPowerBehavior = zeroPowerBehavior;
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return zeroPowerBehavior;
    }
}
