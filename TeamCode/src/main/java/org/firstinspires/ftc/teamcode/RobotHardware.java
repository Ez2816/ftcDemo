package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * All of the robot's hardware in one place.
 * Device names must match the robot configuration on the Driver Station.
 */
public class RobotHardware {
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

        for (DcMotor motor : new DcMotor[] {leftFront, rightFront, leftBack, rightBack}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    /**
     * Mecanum drive.
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

        // If any wheel would go over 100%, scale them all down together.
        double max = Math.max(Math.max(Math.abs(lf), Math.abs(rf)), Math.max(Math.abs(lb), Math.abs(rb)));
        if (max > 1.0) {
            lf /= max;
            rf /= max;
            lb /= max;
            rb /= max;
        }

        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftBack.setPower(lb);
        rightBack.setPower(rb);
    }

    public void stopDrive() {
        drive(0, 0, 0);
    }
}
