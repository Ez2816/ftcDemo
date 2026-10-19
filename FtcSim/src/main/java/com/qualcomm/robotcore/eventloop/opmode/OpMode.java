package com.qualcomm.robotcore.eventloop.opmode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Iterative OpMode: init() runs once when INIT is pressed, then loop() runs
 * over and over (about every 20 ms) after START until STOP is pressed.
 */
public abstract class OpMode {
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public Gamepad gamepad1;
    public Gamepad gamepad2;

    public abstract void init();

    public void init_loop() {}

    public void start() {}

    public abstract void loop();

    public void stop() {}
}
