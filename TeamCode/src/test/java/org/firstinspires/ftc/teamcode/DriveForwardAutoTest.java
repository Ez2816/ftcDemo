package org.firstinspires.ftc.teamcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.ftcsim.OpModeHarness;
import org.ftcsim.Pose;
import org.ftcsim.SimRobot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DriveForwardAutoTest {
    private SimRobot robot;

    @BeforeEach
    void runAuto() throws InterruptedException {
        robot = TestRobot.create();
        OpModeHarness.runLinear(robot, new DriveForwardAuto());
        System.out.println("Auto finished at " + robot.pose());
    }

    @Test
    void drivesForward24Inches() {
        Pose pose = robot.pose();
        assertEquals(24.0, pose.x(), 1.0, "should end 24 inches forward: " + pose);
        assertEquals(0.0, pose.y(), 1.0, "should not drift sideways: " + pose);
        assertEquals(0.0, pose.heading(), 2.0, "should not turn: " + pose);
    }

    @Test
    void dropsTheSampleAtTheEnd() {
        assertEquals(RobotHardware.CLAW_OPEN, robot.servo("claw").getPosition(), 1e-9);
    }

    @Test
    void showsDoneOnDriverStation() {
        assertEquals("Done", robot.telemetry.lines().get(0));
    }
}
