# ftcDemo

FTC robot code you can run and test **on a laptop, with no robot**. It was built for a live
demo of AI coding agents (GitHub Copilot and Claude Code) finding and fixing robot bugs.

```
TeamCode/   our robot code: OpModes and RobotHardware, the same as in a real FtcRobotController project
FtcSim/     a stand-in for the FTC SDK, plus a physics sim that moves the robot on the field
```

The OpModes import the real FTC classes (`com.qualcomm.robotcore.hardware.DcMotor`, `@TeleOp`,
`LinearOpMode`, ...), so the code in `TeamCode` can be copied straight into a real robot project.

## Setup

1. Install [VS Code](https://code.visualstudio.com/) and **JDK 17** (for example [Temurin 17](https://adoptium.net/temurin/releases/?version=17)).
2. Clone this repo and open the folder in VS Code.
3. Install the recommended extensions when VS Code asks (Java, Gradle, GitHub Copilot).
4. Run the tests:
   - **Terminal:** `./gradlew test` (Windows: `gradlew.bat test`)
   - **VS Code:** `Terminal → Run Task → FTC: Run all tests`, or use the Testing panel (the beaker icon)

The first run downloads Gradle and takes a minute. After that it takes a few seconds.

## What the tests check

| OpMode | Test | Checks |
|---|---|---|
| `Mecanum TeleOp` | `MecanumTeleOpTest` | the sticks drive, strafe and turn the right way; motor power never goes over 100%; slow mode; claw buttons; STOP stops the motors |
| `Drive Forward 24in` | `DriveForwardAutoTest` | ends 24" forward without drifting or turning; drops the sample; shows "Done" |

The sim also catches classic FTC mistakes. For example, a `while` loop in autonomous that never
checks `opModeIsActive()` fails with:

```
OpModeStuckException: OpMode was still running 1 s after STOP was pressed. Does every loop check opModeIsActive()?
```

## Using an AI agent on this repo

Agents read `.github/copilot-instructions.md` (Copilot) and `CLAUDE.md` (Claude Code). Those files explain
the layout and the ground rules: fix the robot code, not the tests or the sim.

Try a prompt like:

> Run the tests. One is failing. Find the bug in TeamCode, explain what the robot would do on the field, and fix it.
