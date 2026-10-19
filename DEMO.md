# Demo script

Presenter notes. Bugs 1 and 2 are found by prompting Claude by hand. Bug 3 uses the `/fix-robot` skill
to show the same workflow packed into one command.

## Before the demo

1. Restart Claude Code so `/fix-robot` shows up.
2. Make sure you are not in auto mode, so edit approvals show up on screen.
3. Rehearse each bug once to know the timing.

## Bug 1: `bug/wrong-name` (by hand)

The robot crashes as soon as you press INIT.

1. In a terminal: `git checkout bug/wrong-name`
2. Optional: switch to **plan mode** (Shift+Tab) so Claude can investigate but not edit.
3. Prompt:
   ```
   Run the tests. What's failing? Don't fix anything yet.
   ```
   Expect: all 8 tests fail with `Unable to find a hardware device with name "leftback" and type DcMotor`.
4. Prompt:
   ```
   Why would all 8 fail from one problem? What would a driver see at a match?
   ```
5. Optional: open `RobotHardware.java` and ask the room to spot the bug.
6. Prompt:
   ```
   Find the line causing it.
   ```
   Expect: Claude compares `"leftback"` in the code with `"leftBack"` in the robot configuration.
7. Leave plan mode (Shift+Tab). Prompt:
   ```
   Fix it and rerun the tests.
   ```
   Approve the edit when asked. Expect: 8 of 8 tests pass.

## Bug 2: `bug/infinite-loop` (by hand, you steer more)

Press A and the whole robot freezes.

1. Throw away the last fix and switch:
   ```
   git checkout -- .
   git checkout bug/infinite-loop
   ```
2. Prompt:
   ```
   Run the tests and tell me what's wrong, in driver terms.
   ```
   Expect: only the claw test fails with `loop() was still running after 1 second and never returned`.
3. Prompt:
   ```
   Why does a while loop freeze the whole robot, not just the claw?
   ```
   Teaching moment: the gamepad can't update until `loop()` returns, so `gamepad1.a` stays true forever.
4. Prompt:
   ```
   Fix it. Rerun the tests.
   ```

## Bug 3: `bug/divide-by-zero` (skill)

Let go of the sticks and the robot doesn't stop.

1. Throw away the last fix and switch:
   ```
   git checkout -- .
   git checkout bug/divide-by-zero
   ```
2. Say: "The last two took 4 or 5 prompts each. A skill packs that whole workflow into one command."
3. Type:
   ```
   /fix-robot
   ```
4. Point out: same steps you did by hand, and the report has the same format every time
   (What the driver would see, Why, The fix, Tests).

   Expect: 2 tests fail. With the sticks let go, the code divides 0 by 0, which in Java is `NaN`
   (not a number) instead of a crash. The same missing check also makes slow mode go full speed.

## After the demo

```
git checkout -- .
git checkout main
```

## Tips

- If Claude starts fixing too early, say "Don't fix anything yet."
- Run `git checkout -- .` before switching branches, or git refuses with
  "Your local changes would be overwritten".
- Replay any bug by running `git checkout -- .` on its branch.
