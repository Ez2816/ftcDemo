package org.ftcsim;

/**
 * Moves the robot around the field based on its four wheel speeds.
 *
 * The left-side motors are mounted mirrored, like on a real robot, so they
 * must be set to REVERSE in code for the robot to drive straight.
 */
class MecanumDriveSim {
    private final SimMotor leftFront, rightFront, leftBack, rightBack;
    private final double ticksPerInch;
    private final double turnRadiusInches;
    private double x, y, headingRadians;

    MecanumDriveSim(SimMotor leftFront, SimMotor rightFront, SimMotor leftBack, SimMotor rightBack,
                    double ticksPerInch, double turnRadiusInches) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftBack = leftBack;
        this.rightBack = rightBack;
        this.ticksPerInch = ticksPerInch;
        this.turnRadiusInches = turnRadiusInches;
    }

    void update(double dt) {
        // Wheel surface speeds in inches/second, positive = rolling forward.
        double fl = -leftFront.shaftVelocity() / ticksPerInch;
        double bl = -leftBack.shaftVelocity() / ticksPerInch;
        double fr = rightFront.shaftVelocity() / ticksPerInch;
        double br = rightBack.shaftVelocity() / ticksPerInch;

        double forward = (fl + fr + bl + br) / 4;
        double right = (fl - fr - bl + br) / 4;
        double clockwise = (fl - fr + bl - br) / (4 * turnRadiusInches);

        x += (forward * Math.cos(headingRadians) + right * Math.sin(headingRadians)) * dt;
        y += (forward * Math.sin(headingRadians) - right * Math.cos(headingRadians)) * dt;
        headingRadians -= clockwise * dt;
    }

    Pose pose() {
        return new Pose(x, y, Math.toDegrees(headingRadians));
    }
}
