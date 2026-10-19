package com.qualcomm.robotcore.hardware;

public interface DcMotor extends DcMotorSimple {
    enum ZeroPowerBehavior {
        UNKNOWN, BRAKE, FLOAT
    }

    void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior);

    ZeroPowerBehavior getZeroPowerBehavior();
}
