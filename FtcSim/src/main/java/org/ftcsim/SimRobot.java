package org.ftcsim;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simulated robot: its hardware, gamepads, Driver Station telemetry, and
 * where it is on the field. Creating one resets the sim clock.
 */
public class SimRobot {
    public final HardwareMap hardwareMap = new HardwareMap();
    public final Gamepad gamepad1 = new Gamepad();
    public final Gamepad gamepad2 = new Gamepad();
    public final SimTelemetry telemetry = new SimTelemetry();

    private final Map<String, SimMotor> motors = new LinkedHashMap<>();
    private final Map<String, SimServo> servos = new LinkedHashMap<>();
    private MecanumDriveSim drive;
    private double watchdogAtSeconds = Double.POSITIVE_INFINITY;
    private String watchdogMessage;

    public SimRobot() {
        SimClock.attach(this);
    }

    public SimMotor addMotor(String name, double maxTicksPerSecond) {
        SimMotor motor = new SimMotor(name, maxTicksPerSecond);
        motors.put(name, motor);
        hardwareMap.put(name, motor);
        return motor;
    }

    public SimServo addServo(String name) {
        SimServo servo = new SimServo(name);
        servos.put(name, servo);
        hardwareMap.put(name, servo);
        return servo;
    }

    /** Connects four existing motors as a mecanum drivetrain so the robot moves on the field. */
    public void addMecanumDrive(String leftFront, String rightFront, String leftBack, String rightBack,
                                double ticksPerInch, double turnRadiusInches) {
        drive = new MecanumDriveSim(motor(leftFront), motor(rightFront), motor(leftBack), motor(rightBack),
                ticksPerInch, turnRadiusInches);
    }

    public SimMotor motor(String name) {
        SimMotor motor = motors.get(name);
        if (motor == null) {
            throw new IllegalArgumentException("No simulated motor named \"" + name + "\"");
        }
        return motor;
    }

    public SimServo servo(String name) {
        SimServo servo = servos.get(name);
        if (servo == null) {
            throw new IllegalArgumentException("No simulated servo named \"" + name + "\"");
        }
        return servo;
    }

    public Collection<SimMotor> motors() {
        return motors.values();
    }

    public Pose pose() {
        if (drive == null) {
            throw new IllegalStateException("Call addMecanumDrive() before asking for the robot's pose");
        }
        return drive.pose();
    }

    /** What the SDK does when an OpMode ends. */
    public void stopAllMotors() {
        motors.values().forEach(motor -> motor.setPower(0));
    }

    void setWatchdog(double atSeconds, String message) {
        watchdogAtSeconds = atSeconds;
        watchdogMessage = message;
    }

    void clearWatchdog() {
        watchdogAtSeconds = Double.POSITIVE_INFINITY;
    }

    void onTimeAdvanced(double dt) {
        if (drive != null) {
            drive.update(dt);
        }
        motors.values().forEach(motor -> motor.update(dt));
        if (SimClock.seconds() > watchdogAtSeconds) {
            clearWatchdog();
            throw new OpModeStuckException(watchdogMessage);
        }
    }
}
