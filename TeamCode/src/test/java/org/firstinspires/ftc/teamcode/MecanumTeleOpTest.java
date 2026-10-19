package org.firstinspires.ftc.teamcode;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.ftcsim.OpModeHarness;
import org.ftcsim.SimMotor;
import org.ftcsim.SimRobot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MecanumTeleOpTest {
    private SimRobot robot;
    private OpModeHarness driverStation;

    @BeforeEach
    void pressInitAndStart() {
        // The robot configuration on the Driver Station.
        robot = new SimRobot();
        robot.addMotor("leftFront");
        robot.addMotor("rightFront");
        robot.addMotor("leftBack");
        robot.addMotor("rightBack");
        robot.addServo("claw");

        driverStation = new OpModeHarness(robot, new MecanumTeleOp());
        driverStation.init();
        driverStation.start();
    }

    @Test
    void leftStickUpDrivesForward() {
        robot.gamepad1.left_stick_y = -1; // stick up
        driverStation.loop();

        assertWheelPowers(1, 1, 1, 1);
    }

    @Test
    void leftStickRightStrafesRight() {
        robot.gamepad1.left_stick_x = 1;
        driverStation.loop();

        assertWheelPowers(1, -1, -1, 1);
    }

    @Test
    void rightStickRightTurnsClockwise() {
        robot.gamepad1.right_stick_x = 1;
        driverStation.loop();

        assertWheelPowers(1, -1, 1, -1);
    }

    @Test
    void lettingGoOfTheSticksStopsTheRobot() {
        robot.gamepad1.left_stick_y = -1;
        driverStation.loop();
        robot.gamepad1.left_stick_y = 0;
        driverStation.loop();

        assertWheelPowers(0, 0, 0, 0);
    }

    @Test
    void fullStickInEveryDirectionNeverAsksForMoreThanFullPower() {
        robot.gamepad1.left_stick_y = -1;
        robot.gamepad1.left_stick_x = 1;
        robot.gamepad1.right_stick_x = 1;
        driverStation.loop();

        for (SimMotor motor : robot.motors()) {
            assertTrue(Math.abs(motor.getRequestedPower()) <= 1.0,
                    motor.getDeviceName() + " was asked for power " + motor.getRequestedPower());
        }
    }

    @Test
    void slowModeDrivesSlower() {
        robot.gamepad1.left_stick_y = -1;
        robot.gamepad1.right_bumper = true;
        driverStation.loop();

        double slow = MecanumTeleOp.SLOW_MODE_SCALE;
        assertWheelPowers(slow, slow, slow, slow);
    }

    @Test
    void aOpensClawAndBClosesIt() {
        assertEquals(RobotHardware.CLAW_CLOSED, robot.servo("claw").getPosition(), 1e-9, "claw starts closed");

        robot.gamepad1.a = true;
        driverStation.loop();
        assertEquals(RobotHardware.CLAW_OPEN, robot.servo("claw").getPosition(), 1e-9, "A opens the claw");

        robot.gamepad1.a = false;
        robot.gamepad1.b = true;
        driverStation.loop();
        assertEquals(RobotHardware.CLAW_CLOSED, robot.servo("claw").getPosition(), 1e-9, "B closes the claw");
    }

    @Test
    void stopStopsAllMotors() {
        robot.gamepad1.left_stick_y = -1;
        driverStation.loop();
        driverStation.stop();

        assertWheelPowers(0, 0, 0, 0);
    }

    /** Checks the four wheel powers, in the order leftFront, rightFront, leftBack, rightBack. */
    private void assertWheelPowers(double leftFront, double rightFront, double leftBack, double rightBack) {
        double[] expected = {leftFront, rightFront, leftBack, rightBack};
        double[] actual = {
            robot.motor("leftFront").getPower(), robot.motor("rightFront").getPower(),
            robot.motor("leftBack").getPower(), robot.motor("rightBack").getPower(),
        };
        assertArrayEquals(expected, actual, 1e-9, "wheel powers (leftFront, rightFront, leftBack, rightBack)");
    }
}
