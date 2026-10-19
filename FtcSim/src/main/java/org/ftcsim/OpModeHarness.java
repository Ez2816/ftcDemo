package org.ftcsim;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Plays the role of the Driver Station: presses INIT, START and STOP.
 *
 * Iterative OpMode:
 * <pre>
 *   OpModeHarness h = new OpModeHarness(robot, new MyTeleOp());
 *   h.init(); h.start();
 *   robot.gamepad1.left_stick_y = -1;   // push stick up
 *   h.loopFor(1.0);                      // 1 second of loop() calls
 *   h.stop();
 * </pre>
 *
 * LinearOpMode:
 * <pre>
 *   OpModeHarness.runLinear(robot, new MyAuto());   // full 30 s autonomous period
 * </pre>
 */
public class OpModeHarness {
    public static final double LOOP_SECONDS = 0.020;
    public static final double AUTO_SECONDS = 30.0;
    static final double MAX_LOOP_SECONDS = 1.0;
    static final double STOP_GRACE_SECONDS = 1.0;
    static final double IDLE_SECONDS = 0.005;

    private final SimRobot robot;
    private final OpMode opMode;

    public OpModeHarness(SimRobot robot, OpMode opMode) {
        this.robot = robot;
        this.opMode = opMode;
        wire(robot, opMode);
    }

    public void init() {
        runGuarded(opMode::init, "init()");
        robot.telemetry.update();
    }

    public void start() {
        opMode.resetRuntime();
        runGuarded(opMode::start, "start()");
        robot.telemetry.update();
    }

    /** Calls loop() every 20 ms of simulated time, like the real robot controller. */
    public void loopFor(double seconds) {
        long loops = Math.round(seconds / LOOP_SECONDS);
        for (long i = 0; i < loops; i++) {
            runGuarded(opMode::loop, "loop()");
            robot.telemetry.update();
            SimClock.advance(LOOP_SECONDS);
        }
    }

    public void stop() {
        runGuarded(opMode::stop, "stop()");
        robot.stopAllMotors();
    }

    /** Runs a LinearOpMode through INIT and START, then presses STOP after the given time. */
    public static void runLinear(SimRobot robot, LinearOpMode opMode, double seconds) throws InterruptedException {
        wire(robot, opMode);
        opMode.simDriver = new LinearDriver(robot, opMode, seconds);
        try {
            opMode.runOpMode();
        } finally {
            robot.clearWatchdog();
            robot.stopAllMotors();
        }
    }

    public static void runLinear(SimRobot robot, LinearOpMode opMode) throws InterruptedException {
        runLinear(robot, opMode, AUTO_SECONDS);
    }

    private static void wire(SimRobot robot, OpMode opMode) {
        opMode.hardwareMap = robot.hardwareMap;
        opMode.telemetry = robot.telemetry;
        opMode.gamepad1 = robot.gamepad1;
        opMode.gamepad2 = robot.gamepad2;
    }

    private void runGuarded(Runnable code, String name) {
        robot.setWatchdog(SimClock.seconds() + MAX_LOOP_SECONDS, String.format(
                "%s ran for over %.0f s without returning. Iterative OpModes must not block: "
                        + "no long while loops or sleeps inside %s.", name, MAX_LOOP_SECONDS, name));
        try {
            code.run();
        } finally {
            robot.clearWatchdog();
        }
    }

    private static final class LinearDriver implements SimDriver {
        private final SimRobot robot;
        private final OpMode opMode;
        private final double durationSeconds;
        private boolean started;
        private double stopAtSeconds = Double.POSITIVE_INFINITY;

        LinearDriver(SimRobot robot, OpMode opMode, double durationSeconds) {
            this.robot = robot;
            this.opMode = opMode;
            this.durationSeconds = durationSeconds;
        }

        @Override
        public void waitForStart() {
            if (started) {
                return;
            }
            started = true;
            opMode.resetRuntime();
            stopAtSeconds = SimClock.seconds() + durationSeconds;
            robot.setWatchdog(stopAtSeconds + STOP_GRACE_SECONDS, String.format(
                    "OpMode was still running %.0f s after STOP was pressed. "
                            + "Does every loop check opModeIsActive()?", STOP_GRACE_SECONDS));
        }

        @Override
        public boolean opModeIsActive() {
            boolean active = started && !isStopRequested();
            if (active) {
                idle();
            }
            return active;
        }

        @Override
        public boolean isStarted() {
            return started;
        }

        @Override
        public boolean isStopRequested() {
            return SimClock.seconds() >= stopAtSeconds;
        }

        @Override
        public void sleep(long milliseconds) {
            long endNanos = SimClock.nanoTime() + milliseconds * 1_000_000L;
            while (SimClock.nanoTime() < endNanos && !isStopRequested()) {
                SimClock.advance(Math.min(LOOP_SECONDS, (endNanos - SimClock.nanoTime()) / 1e9));
            }
        }

        @Override
        public void idle() {
            SimClock.advance(IDLE_SECONDS);
        }
    }
}
