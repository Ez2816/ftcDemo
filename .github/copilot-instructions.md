# ftcDemo: instructions for AI coding agents

This is FIRST Tech Challenge (FTC) robot code that runs in a simulator on a laptop,
so OpModes can be tested without a robot. GitHub Copilot and Claude Code both read this file.

## Layout

- `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` is the team's robot code. **Fix bugs here.**
  - `RobotHardware.java`: the hardware map plus mecanum drive math
  - `MecanumTeleOp.java`: driver-controlled OpMode (iterative `OpMode`)
- `TeamCode/src/test/java/.../MecanumTeleOpTest.java` presses gamepad buttons and checks the motor powers and
  servo positions the code sets. Its `@BeforeEach` holds the robot configuration (device names). **The tests are the spec.**
- `FtcSim/` is a stand-in for the FTC SDK. It uses the real package names (`com.qualcomm.robotcore...`,
  `org.firstinspires.ftc...`) so TeamCode is identical to code that runs on a real robot.
  The `org.ftcsim` package is the test harness (pretend motors, servos and Driver Station).

## Commands

- Run all tests: `./gradlew test` (Windows: `gradlew.bat test`)
- One test class: `./gradlew test --tests '*MecanumTeleOpTest*'`

## Rules

1. Fix the robot code in `TeamCode/src/main`. Do **not** edit `FtcSim/` or the
   tests to make a failing test pass, unless the user explicitly says the test or the sim is wrong.
2. Only use FTC SDK APIs that exist in the real SDK. Never call `org.ftcsim` classes from `TeamCode/src/main`,
   because that code has to run on a real robot.
3. Run the tests after every change and report the actual result.
4. Explain fixes in terms a high school FTC student would understand: what the robot would have
   done on the field, and why the change fixes it.
5. **Tag every edit, always.** Every change in `TeamCode/src/main` gets a comment at the end of the changed line
   (for a change that spans several lines, the first one): `// ! FIX: <what it was and why it broke>`, for example
   `motor.setPower(power); // ! FIX: was -power, robot drove backward`.
   The Better Comments VS Code extension shows `// !` comments in red, so the newest fix stands out on screen.
   Only the newest fix keeps its tag: before adding yours, delete any older `// ! FIX:` comments in `TeamCode`.

## FTC conventions used here

- Gamepad sticks: pushing **up** gives a **negative** `left_stick_y`.
- Left-side drive motors are mounted mirrored, so they are set to `REVERSE`.
- `RobotHardware.drive(drive, strafe, turn)`: forward, right and clockwise are positive.
- `loop()` runs about every 20 ms and must return quickly.
