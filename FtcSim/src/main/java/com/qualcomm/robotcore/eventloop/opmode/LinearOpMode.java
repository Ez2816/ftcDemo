package com.qualcomm.robotcore.eventloop.opmode;

import org.ftcsim.SimDriver;

/**
 * Linear OpMode: everything happens inside runOpMode(), top to bottom.
 * Call waitForStart() before moving, and check opModeIsActive() in every loop
 * so the robot stops when the driver presses STOP.
 */
public abstract class LinearOpMode extends OpMode {
    /** Set by the simulator's OpModeHarness. Not part of the real FTC SDK. */
    public SimDriver simDriver;

    public abstract void runOpMode() throws InterruptedException;

    public final void waitForStart() {
        driver().waitForStart();
    }

    public final boolean opModeIsActive() {
        return driver().opModeIsActive();
    }

    public final boolean isStarted() {
        return driver().isStarted();
    }

    public final boolean isStopRequested() {
        return driver().isStopRequested();
    }

    public final void sleep(long milliseconds) {
        driver().sleep(milliseconds);
    }

    public final void idle() {
        driver().idle();
    }

    @Override
    public final void init() {}

    @Override
    public final void loop() {}

    private SimDriver driver() {
        if (simDriver == null) {
            throw new IllegalStateException("LinearOpModes must be run with OpModeHarness.runLinear()");
        }
        return simDriver;
    }
}
