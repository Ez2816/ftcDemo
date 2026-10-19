package com.qualcomm.robotcore.hardware;

public interface DcMotor extends DcMotorSimple {
    enum RunMode {
        RUN_WITHOUT_ENCODER,
        RUN_USING_ENCODER,
        RUN_TO_POSITION,
        STOP_AND_RESET_ENCODER
    }

    enum ZeroPowerBehavior {
        UNKNOWN, BRAKE, FLOAT
    }

    void setMode(RunMode mode);

    RunMode getMode();

    void setTargetPosition(int position);

    int getTargetPosition();

    /** True while in RUN_TO_POSITION and the motor has not reached its target yet. */
    boolean isBusy();

    int getCurrentPosition();

    void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior);

    ZeroPowerBehavior getZeroPowerBehavior();
}
