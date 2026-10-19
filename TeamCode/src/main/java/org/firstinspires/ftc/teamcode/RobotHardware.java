package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * All of the robot's hardware in one place, shared by every OpMode.
 * Device names must match the robot configuration on the Driver Station.
 */
public class RobotHardware {
    // goBILDA 5203 312 RPM motor with 96 mm mecanum wheels.
    public static final double TICKS_PER_REV = 537.7;
    public static final double WHEEL_DIAMETER_INCHES = 3.78;
    public static final double TICKS_PER_INCH = TICKS_PER_REV / (WHEEL_DIAMETER_INCHES * Math.PI);

    public static final double CLAW_OPEN = 0.65;
    public static final double CLAW_CLOSED = 0.25;

    public final DcMotor leftFront;
    public final DcMotor rightFront;
    public final DcMotor leftBack;
    public final DcMotor rightBack;
    public final Servo claw;

    public RobotHardware(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        claw = hardwareMap.get(Servo.class, "claw");

        // Left motors are mounted mirrored, so reverse them to drive straight.
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        for (DcMotor motor : driveMotors()) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    public DcMotor[] driveMotors() {
        return new DcMotor[] {leftFront, rightFront, leftBack, rightBack};
    }

    public void setDriveMode(DcMotor.RunMode mode) {
        for (DcMotor motor : driveMotors()) {
            motor.setMode(mode);
        }
    }

    /**
     * Robot-centric mecanum drive.
     *
     * @param drive  forward is positive
     * @param strafe right is positive
     * @param turn   clockwise is positive
     */
    public void drive(double drive, double strafe, double turn) {
        double lf = drive + strafe + turn;
        double rf = drive - strafe - turn;
        double lb = drive - strafe + turn;
        double rb = drive + strafe - turn;

        // Scale everything down together if any wheel would go over 100%.
        double max = Math.max(1.0, Math.max(Math.max(Math.abs(lf), Math.abs(rf)), Math.max(Math.abs(lb), Math.abs(rb))));

        leftFront.setPower(lf / max);
        rightFront.setPower(rf / max);
        leftBack.setPower(lb / max);
        rightBack.setPower(rb / max);
    }

    public void stopDrive() {
        drive(0, 0, 0);
    }
}
