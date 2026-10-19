package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/** Starts holding a preloaded sample, drives forward 24 inches, and drops it. */
@Autonomous(name = "Drive Forward 24in", group = "Demo", preselectTeleOp = "Mecanum TeleOp")
public class DriveForwardAuto extends LinearOpMode {
    static final double DRIVE_INCHES = 24.0;
    static final double DRIVE_POWER = 0.5;
    static final double DRIVE_TIMEOUT_SECONDS = 5.0;

    private RobotHardware robot;

    @Override
    public void runOpMode() {
        robot = new RobotHardware(hardwareMap);
        robot.claw.setPosition(RobotHardware.CLAW_CLOSED);
        robot.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("Ready. Press START.");
        telemetry.update();

        waitForStart();

        driveStraight(DRIVE_INCHES, DRIVE_POWER, DRIVE_TIMEOUT_SECONDS);
        robot.claw.setPosition(RobotHardware.CLAW_OPEN);
        sleep(500);

        telemetry.addLine("Done");
        telemetry.update();
    }

    /** Drives straight using the motors' built-in RUN_TO_POSITION control. */
    private void driveStraight(double inches, double power, double timeoutSeconds) {
        int ticks = (int) Math.round(inches * RobotHardware.TICKS_PER_INCH);
        for (DcMotor motor : robot.driveMotors()) {
            motor.setTargetPosition(motor.getCurrentPosition() + ticks);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(Math.abs(power));
        }

        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive() && timer.seconds() < timeoutSeconds
                && robot.leftFront.isBusy() && robot.rightFront.isBusy()
                && robot.leftBack.isBusy() && robot.rightBack.isBusy()) {
            telemetry.addData("Target", ticks);
            telemetry.addData("Left front", robot.leftFront.getCurrentPosition());
            telemetry.addData("Right front", robot.rightFront.getCurrentPosition());
            telemetry.update();
        }

        robot.stopDrive();
        robot.setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
