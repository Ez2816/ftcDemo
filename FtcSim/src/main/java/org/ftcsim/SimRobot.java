package org.ftcsim;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** A pretend robot: its configured hardware, gamepads, and Driver Station telemetry. */
public class SimRobot {
    public final HardwareMap hardwareMap = new HardwareMap();
    public final Gamepad gamepad1 = new Gamepad();
    public final Gamepad gamepad2 = new Gamepad();
    public final SimTelemetry telemetry = new SimTelemetry();

    private final Map<String, SimMotor> motors = new LinkedHashMap<>();
    private final Map<String, SimServo> servos = new LinkedHashMap<>();

    /** Adds a motor to the robot configuration, like "Configure Robot" on the Driver Station. */
    public SimMotor addMotor(String name) {
        SimMotor motor = new SimMotor(name);
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

    public SimMotor motor(String name) {
        return motors.get(name);
    }

    public SimServo servo(String name) {
        return servos.get(name);
    }

    public Collection<SimMotor> motors() {
        return motors.values();
    }

    /** What the SDK does when an OpMode ends. */
    public void stopAllMotors() {
        motors.values().forEach(motor -> motor.setPower(0));
    }
}
