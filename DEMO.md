# Demo script

Presenter notes. Bugs 1 and 2: two quick prompts each. Bug 3: the `/fix-robot` skill does the same
workflow in one command. Finish with `/handoff`.

Every fix Claude makes gets a red `// ! FIX:` comment on the changed line, so the audience can see it.

## Before the demo

1. In VS Code, install the **Better Comments** extension (it's in the repo's recommended list). It turns
   `// !` comments red.
2. Restart Claude Code so `/fix-robot` and `/handoff` show up.
3. Make sure you are not in auto mode, so edit approvals show up on screen.
4. Rehearse each bug once to know the timing.

## Bug 1: `bug/wrong-name`

The robot crashes as soon as you press INIT.

1. In a terminal:
   ```
   git checkout bug/wrong-name
   ```
2. Prompt:
   ```
   Run the tests and tell me the error.
   ```
   Expect: all 8 tests fail with `Unable to find a hardware device with name "leftback" and type DcMotor`.
3. Prompt:
   ```
   Fix the bug for me and highlight the fix.
   ```
   Approve the edit. Expect: `"leftback"` becomes `"leftBack"` with a red `// ! FIX:` tag, and 8 of 8 tests pass.
4. Open `RobotHardware.java` and show the red tag.

## Bug 2: `bug/infinite-loop`

Press A and the whole robot freezes.

1. Throw away the last fix and switch:
   ```
   git checkout -- .
   git checkout bug/infinite-loop
   ```
2. Prompt:
   ```
   Run the tests and tell me the error.
   ```
   Expect: only the claw test fails with `loop() was still running after 1 second and never returned`.
3. Prompt:
   ```
   Fix the bug for me and highlight the fix.
   ```
   Expect: `while (gamepad1.a)` becomes `if (gamepad1.a)` with a red tag.
4. Open `MecanumTeleOp.java` and show the red tag.

   Teaching moment: the gamepad can't update until `loop()` returns, so `gamepad1.a` stays true forever.

## Bug 3: `bug/divide-by-zero` (skill)

Let go of the sticks and the robot doesn't stop.

1. Throw away the last fix and switch:
   ```
   git checkout -- .
   git checkout bug/divide-by-zero
   ```
2. Say: "A skill packs that whole workflow into one command, and it runs the same way every time."
3. Type:
   ```
   /fix-robot
   ```
   Expect: 2 tests fail. With the sticks let go, the code divides 0 by 0, which in Java is `NaN`
   (not a number) instead of a crash. The same missing check also makes slow mode go full speed.
   Claude puts back the `if (max > 1.0)` check with a red tag.
4. Point out the report: What the driver would see, Why, The fix, Tests.

## Wrap up: `/handoff`

1. Type:
   ```
   /handoff
   ```
2. Claude writes a short summary (goal, what got done, where things stand, next steps) in the chat and
   saves it to `HANDOFF.md`.
3. Say: "Next session, I just tell Claude 'Read HANDOFF.md and continue.'"

## After the demo

```
git checkout -- .
git checkout main
```

## Tips

- If Claude forgets the red tag, say "Add the `// ! FIX:` tag."
- Run `git checkout -- .` before switching branches, or git refuses with
  "Your local changes would be overwritten".
- Replay any bug by running `git checkout -- .` on its branch.
- `HANDOFF.md` is git-ignored, so it stays put when you switch branches.
