---
name: fix-robot
description: Find and fix a bug in the FTC robot code. Runs the sim tests, finds the failing one, fixes the bug in RobotHardware.java or MecanumTeleOp.java, re-runs the tests, and explains the fix the way you'd explain it to an FTC student. Use when the user says "fix the robot", "a test is failing", or types /fix-robot.
---

# Fix the robot

Follow these steps in order, every time. Keep each step short so it is easy to follow on a projector.

## 1. Run the tests

Run `./gradlew cleanTest test --console=plain` (Windows: `gradlew.bat cleanTest test --console=plain`).
If every test passes, say so and stop: there is nothing to fix.

## 2. Read the failure

Pick the failing test(s). Quote the one-line failure message. Read the test in
`TeamCode/src/test/java/org/firstinspires/ftc/teamcode/MecanumTeleOpTest.java` to see what the
driver did (which stick or button) and what should have happened.

## 3. Find the bug

The bug is in one of these two files. Read only these:

- `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotHardware.java` (motor directions, mecanum math)
- `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecanumTeleOp.java` (gamepad controls)

Never edit the tests, `TestRobot.java`, or anything in `FtcSim/`. The tests are the spec.
Use the FTC conventions in `CLAUDE.md` (stick up is negative, left motors are reversed, and so on).

## 4. Fix it

Make the smallest change that fixes the bug, usually one line. Don't refactor or tidy up other code.

## 5. Re-run the tests

Run the same command as step 1. If anything still fails, go back to step 2.

## 6. Report

Finish with exactly this format:

### What the robot did on the field
One or two sentences, in driver terms: what a driver would have seen happen on the field.

### Why
One or two sentences explaining the mistake in the code, for a high school student.

### The fix
The file name and a small before/after of the changed line(s).

### Tests
`X of X tests passed`, using the actual numbers from the run.
