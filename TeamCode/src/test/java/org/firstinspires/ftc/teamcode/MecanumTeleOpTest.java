package org.firstinspires.ftc.teamcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.ftcsim.OpModeHarness;
import org.ftcsim.Pose;
import org.ftcsim.SimMotor;
import org.ftcsim.SimRobot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MecanumTeleOpTest {
    private SimRobot robot;
    private OpModeHarness harness;

    @BeforeEach
    void pressInitAndStart() {
        robot = TestRobot.create();
        harness = new OpModeHarness(robot, new MecanumTeleOp());
        harness.init();
        harness.start();
    }

    @Test
    void leftStickUpDrivesForward() {
        robot.gamepad1.left_stick_y = -1; // stick up
        harness.loopFor(1.0);

        Pose pose = robot.pose();
        assertTrue(pose.x() > 20, "should drive forward, but ended at " + pose);
        assertEquals(0, pose.y(), 1.0, "should not drift sideways: " + pose);
        assertEquals(0, pose.heading(), 2.0, "should not turn: " + pose);
    }

    @Test
    void leftStickRightStrafesRight() {
        robot.gamepad1.left_stick_x = 1;
        harness.loopFor(1.0);

        Pose pose = robot.pose();
        assertTrue(pose.y() < -20, "should strafe right (negative y), but ended at " + pose);
        assertEquals(0, pose.x(), 1.0, "should not drive forward or back: " + pose);
        assertEquals(0, pose.heading(), 2.0, "should not turn: " + pose);
    }

    @Test
    void rightStickRightTurnsClockwise() {
        robot.gamepad1.right_stick_x = 1;
        harness.loopFor(0.5);

        Pose pose = robot.pose();
        assertTrue(pose.heading() < -30, "should turn clockwise (negative heading), but ended at " + pose);
        assertEquals(0, pose.x(), 1.0, "should turn in place: " + pose);
        assertEquals(0, pose.y(), 1.0, "should turn in place: " + pose);
    }

    @Test
    void fullStickInEveryDirectionNeverAsksForMoreThanFullPower() {
        robot.gamepad1.left_stick_y = -1;
        robot.gamepad1.left_stick_x = 1;
        robot.gamepad1.right_stick_x = 1;
        harness.loopFor(0.1);

        for (SimMotor motor : robot.motors()) {
            assertTrue(Math.abs(motor.getRequestedPower()) <= 1.0,
                    motor.getDeviceName() + " was asked for power " + motor.getRequestedPower());
        }
    }

    @Test
    void slowModeDrivesSlower() {
        robot.gamepad1.left_stick_y = -1;
        robot.gamepad1.right_bumper = true;
        harness.loopFor(1.0);

        double slowDistance = robot.pose().x();
        double fullSpeedInchesPerSecond = SimMotor.GOBILDA_312_MAX_TICKS_PER_SECOND / TestRobot.TICKS_PER_INCH;
        assertEquals(fullSpeedInchesPerSecond * MecanumTeleOp.SLOW_MODE_SCALE, slowDistance, 2.0);
    }

    @Test
    void aOpensClawAndBClosesIt() {
        assertEquals(RobotHardware.CLAW_CLOSED, robot.servo("claw").getPosition(), 1e-9, "claw starts closed");

        robot.gamepad1.a = true;
        harness.loopFor(0.1);
        assertEquals(RobotHardware.CLAW_OPEN, robot.servo("claw").getPosition(), 1e-9, "A opens the claw");

        robot.gamepad1.a = false;
        robot.gamepad1.b = true;
        harness.loopFor(0.1);
        assertEquals(RobotHardware.CLAW_CLOSED, robot.servo("claw").getPosition(), 1e-9, "B closes the claw");
    }

    @Test
    void stopStopsAllMotors() {
        robot.gamepad1.left_stick_y = -1;
        harness.loopFor(0.5);
        harness.stop();

        for (SimMotor motor : robot.motors()) {
            assertEquals(0.0, motor.getPower(), motor.getDeviceName() + " still has power after STOP");
        }
    }
}
