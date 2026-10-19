package com.qualcomm.robotcore.hardware;

/**
 * Controller state. Sticks range from -1.0 to 1.0. Like a real gamepad,
 * pushing a stick UP gives a NEGATIVE y value.
 */
public class Gamepad {
    public float left_stick_x, left_stick_y;
    public float right_stick_x, right_stick_y;
    public float left_trigger, right_trigger;

    public boolean a, b, x, y;
    public boolean left_bumper, right_bumper;
    public boolean dpad_up, dpad_down, dpad_left, dpad_right;
    public boolean start, back, guide;
}
