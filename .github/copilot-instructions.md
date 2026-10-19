# ftcDemo: instructions for AI coding agents

This is FIRST Tech Challenge (FTC) robot code that runs in a simulator on a laptop,
so OpModes can be tested without a robot. GitHub Copilot and Claude Code both read this file.

## Layout

- `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` is the team's robot code. **Fix bugs here.**
  - `RobotHardware.java`: the hardware map plus mecanum drive math, shared by every OpMode
  - `MecanumTeleOp.java`: driver-controlled OpMode (iterative `OpMode`)
  - `DriveForwardAuto.java`: autonomous OpMode (`LinearOpMode`)
- `TeamCode/src/test/java/...` holds JUnit tests that run the OpModes in the sim. **The tests are the spec.**
  - `TestRobot.java` describes the *physical* robot (wheel size, motor mounting). It is deliberately
    independent of `RobotHardware`.
- `FtcSim/` is a stand-in for the FTC SDK. It uses the real package names (`com.qualcomm.robotcore...`,
  `org.firstinspires.ftc...`) so TeamCode is identical to code that runs on a real robot.
  The `org.ftcsim` package is the physics and test harness.

## Commands

- Run all tests: `./gradlew test` (Windows: `gradlew.bat test`)
- One test class: `./gradlew test --tests '*MecanumTeleOpTest*'`

## Rules

1. Fix the robot code in `TeamCode/src/main`. Do **not** edit `FtcSim/`, `TestRobot.java`, or the
   tests to make a failing test pass, unless the user explicitly says the test or the sim is wrong.
2. Only use FTC SDK APIs that exist in the real SDK. Never call `org.ftcsim` classes from `TeamCode/src/main`,
   because that code has to run on a real robot.
3. Run the tests after every change and report the actual result.
4. Explain fixes in terms a high school FTC student would understand: what the robot would have
   done on the field, and why the change fixes it.

## FTC conventions used here

- Gamepad sticks: pushing **up** gives a **negative** `left_stick_y`.
- Left-side drive motors are mounted mirrored, so they are set to `REVERSE`.
- `RobotHardware.drive(drive, strafe, turn)`: forward, right and clockwise are positive.
- Sim field pose: `x` = inches forward, `y` = inches left, `heading` = degrees counter-clockwise.
- In a `LinearOpMode`, every loop must check `opModeIsActive()` so STOP works. Iterative
  `loop()` methods must never block.
