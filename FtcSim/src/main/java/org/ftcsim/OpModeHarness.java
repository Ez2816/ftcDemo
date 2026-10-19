package org.ftcsim;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Plays the role of the Driver Station: presses INIT, START and STOP.
 * <pre>
 *   OpModeHarness h = new OpModeHarness(robot, new MyTeleOp());
 *   h.init(); h.start();
 *   robot.gamepad1.left_stick_y = -1;   // push stick up
 *   h.loop();                            // one loop() call
 *   h.stop();
 * </pre>
 */
public class OpModeHarness {
    static final long MAX_CALL_MILLIS = 1000;

    private final SimRobot robot;
    private final OpMode opMode;
    private final ExecutorService robotThread = Executors.newSingleThreadExecutor(code -> {
        Thread thread = new Thread(code, "OpMode");
        thread.setDaemon(true);
        return thread;
    });

    public OpModeHarness(SimRobot robot, OpMode opMode) {
        this.robot = robot;
        this.opMode = opMode;
        opMode.hardwareMap = robot.hardwareMap;
        opMode.telemetry = robot.telemetry;
        opMode.gamepad1 = robot.gamepad1;
        opMode.gamepad2 = robot.gamepad2;
    }

    public void init() {
        run(opMode::init, "init()");
    }

    public void start() {
        run(opMode::start, "start()");
    }

    /** Calls loop() the given number of times, like the robot controller does every 20 ms. */
    public void loop(int times) {
        for (int i = 0; i < times; i++) {
            run(opMode::loop, "loop()");
        }
    }

    public void loop() {
        loop(1);
    }

    public void stop() {
        run(opMode::stop, "stop()");
        robot.stopAllMotors();
    }

    /** Runs OpMode code on its own thread so code that never returns fails the test instead of hanging it. */
    private void run(Runnable code, String name) {
        Future<?> call = robotThread.submit(() -> {
            code.run();
            robot.telemetry.update();
        });
        try {
            call.get(MAX_CALL_MILLIS, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            throw new OpModeStuckException(String.format(
                    "%s was still running after %d second and never returned. The robot is frozen: "
                            + "the Driver Station stops updating and the gamepad does nothing.",
                    name, MAX_CALL_MILLIS / 1000));
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException runtime) {
                throw runtime;
            }
            throw new RuntimeException(e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
