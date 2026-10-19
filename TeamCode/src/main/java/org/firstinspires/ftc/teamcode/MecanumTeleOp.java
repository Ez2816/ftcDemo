package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Driver controls (gamepad1):
 *   left stick     drive and strafe
 *   right stick X  turn
 *   right bumper   hold for slow mode
 *   A / B          open / close claw
 */
@TeleOp(name = "Mecanum TeleOp", group = "Demo")
public class MecanumTeleOp extends OpMode {
    static final double SLOW_MODE_SCALE = 0.4;

    private RobotHardware robot;

    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);
        robot.claw.setPosition(RobotHardware.CLAW_CLOSED);
        telemetry.addLine("Ready. Press START.");
    }

    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_y; // stick up is negative
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        if (gamepad1.right_bumper) {
            drive *= SLOW_MODE_SCALE;
            strafe *= SLOW_MODE_SCALE;
            turn *= SLOW_MODE_SCALE;
        }

        robot.drive(drive, strafe, turn);

        if (gamepad1.a) {
            robot.claw.setPosition(RobotHardware.CLAW_OPEN);
        }
        if (gamepad1.b) {
            robot.claw.setPosition(RobotHardware.CLAW_CLOSED);
        }

        telemetry.addData("Drive", "%.2f", drive);
        telemetry.addData("Strafe", "%.2f", strafe);
        telemetry.addData("Turn", "%.2f", turn);
        telemetry.addData("Slow mode", gamepad1.right_bumper);
        telemetry.addData("Claw", "%.2f", robot.claw.getPosition());
    }

    @Override
    public void stop() {
        robot.stopDrive();
    }
}
