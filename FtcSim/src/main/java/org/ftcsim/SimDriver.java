package org.ftcsim;

/** What a LinearOpMode asks the Driver Station: has START or STOP been pressed? */
public interface SimDriver {
    void waitForStart();

    boolean opModeIsActive();

    boolean isStarted();

    boolean isStopRequested();

    void sleep(long milliseconds);

    void idle();
}
