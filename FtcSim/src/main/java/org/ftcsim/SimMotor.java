package org.ftcsim;

import com.qualcomm.robotcore.exception.TargetPositionNotSetException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/** A simulated DC motor with an encoder. */
public class SimMotor implements DcMotor {
    /** goBILDA 5203 312 RPM: 537.7 ticks/rev at 312 rev/min. */
    public static final double GOBILDA_312_MAX_TICKS_PER_SECOND = 537.7 * 312 / 60;
    /** Same default as the real SDK. */
    public static final int TARGET_TOLERANCE = 5;

    private final String name;
    private final double maxTicksPerSecond;
    private Direction direction = Direction.FORWARD;
    private RunMode mode = RunMode.RUN_WITHOUT_ENCODER;
    private ZeroPowerBehavior zeroPowerBehavior = ZeroPowerBehavior.BRAKE;
    private double power;
    private double requestedPower;
    private double shaftTicks; // physical shaft position, ignores direction
    private Integer targetPosition;

    public SimMotor(String name, double maxTicksPerSecond) {
        this.name = name;
        this.maxTicksPerSecond = maxTicksPerSecond;
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
    public void setMode(RunMode mode) {
        if (mode == RunMode.RUN_TO_POSITION && targetPosition == null) {
            throw new TargetPositionNotSetException();
        }
        if (mode == RunMode.STOP_AND_RESET_ENCODER) {
            shaftTicks = 0;
            power = 0;
        }
        this.mode = mode;
    }

    @Override
    public RunMode getMode() {
        return mode;
    }

    @Override
    public void setTargetPosition(int position) {
        targetPosition = position;
    }

    @Override
    public int getTargetPosition() {
        return targetPosition == null ? 0 : targetPosition;
    }

    @Override
    public boolean isBusy() {
        SimClock.costOfRead();
        return mode == RunMode.RUN_TO_POSITION && Math.abs(targetPosition - position()) > TARGET_TOLERANCE;
    }

    @Override
    public int getCurrentPosition() {
        SimClock.costOfRead();
        return (int) Math.round(position());
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        this.zeroPowerBehavior = zeroPowerBehavior;
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return zeroPowerBehavior;
    }

    /** Physical shaft speed in ticks per second. Positive = the motor's own forward, ignoring setDirection(). */
    public double shaftVelocity() {
        double effort;
        switch (mode) {
            case STOP_AND_RESET_ENCODER:
                return 0;
            case RUN_TO_POSITION:
                double error = targetPosition - position();
                if (Math.abs(error) <= TARGET_TOLERANCE) {
                    return 0;
                }
                // Slow down near the target, like the SDK's built-in controller.
                effort = Math.abs(power) * Math.signum(error) * Math.min(1.0, Math.abs(error) / 100.0);
                break;
            default:
                effort = power;
        }
        return directionSign() * effort * maxTicksPerSecond;
    }

    void update(double dt) {
        shaftTicks += shaftVelocity() * dt;
    }

    private double directionSign() {
        return direction == Direction.FORWARD ? 1 : -1;
    }

    /** Encoder position as the code sees it (flipped when the motor is REVERSED). */
    private double position() {
        return directionSign() * shaftTicks;
    }
}
