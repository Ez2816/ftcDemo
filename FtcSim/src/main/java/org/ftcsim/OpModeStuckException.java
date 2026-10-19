package org.ftcsim;

/** Thrown when OpMode code blocks or ignores STOP. On a real robot this is when the app restarts itself. */
public class OpModeStuckException extends RuntimeException {
    public OpModeStuckException(String message) {
        super(message);
    }
}
