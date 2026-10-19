package org.firstinspires.ftc.teamcode;

import org.ftcsim.SimMotor;
import org.ftcsim.SimRobot;

/**
 * The simulated version of our robot and its Driver Station configuration.
 * These numbers describe the physical robot, so they are written out here
 * instead of copied from RobotHardware. If the code's math is wrong, the sim
 * should not be wrong with it.
 */
final class TestRobot {
    static final double TICKS_PER_INCH = 537.7 / (3.78 * Math.PI);
    static final double TURN_RADIUS_INCHES = 14.0;

    private TestRobot() {}

    static SimRobot create() {
        SimRobot robot = new SimRobot();
        for (String name : new String[] {"leftFront", "rightFront", "leftBack", "rightBack"}) {
            robot.addMotor(name, SimMotor.GOBILDA_312_MAX_TICKS_PER_SECOND);
        }
        robot.addServo("claw");
        robot.addMecanumDrive("leftFront", "rightFront", "leftBack", "rightBack",
                TICKS_PER_INCH, TURN_RADIUS_INCHES);
        return robot;
    }
}
