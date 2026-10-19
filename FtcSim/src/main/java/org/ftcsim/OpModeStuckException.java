package org.ftcsim;

/** Thrown when OpMode code never returns. On a real robot the Driver Station freezes and the app restarts. */
public class OpModeStuckException extends RuntimeException {
    public OpModeStuckException(String message) {
        super(message);
    }
}
