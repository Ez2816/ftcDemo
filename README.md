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

The robot code is just two files: `RobotHardware.java` (motors and mecanum math) and
`MecanumTeleOp.java` (the driver controls).

## Demo: planted bugs

Each `bug/*` branch is `main` with one small mistake in one of those two files:

| Branch | File | What the robot does |
|---|---|---|
| `bug/backwards` | `MecanumTeleOp.java` | stick up drives backward |
| `bug/claw` | `MecanumTeleOp.java` | A closes the claw and B opens it |
| `bug/spins` | `RobotHardware.java` | curves and twists instead of driving straight |
| `bug/strafe` | `RobotHardware.java` | turns instead of strafing |
| `bug/overpower` | `RobotHardware.java` | full stick asks a motor for more than 100% power |

```
git checkout bug/backwards
```

then in Claude Code type `/fix-robot`. Switch back with `git checkout main`.

## Using an AI agent on this repo

Agents read `.github/copilot-instructions.md` (Copilot) and `CLAUDE.md` (Claude Code). Those files explain
the layout and the ground rules: fix the robot code, not the tests or the sim.

With Claude Code, run the `/fix-robot` skill (`.claude/skills/fix-robot/SKILL.md`). With other agents, try a prompt like:

> Run the tests. One is failing. Find the bug in TeamCode, explain what the robot would do on the field, and fix it.
