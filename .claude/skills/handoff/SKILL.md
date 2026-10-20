---
name: handoff
description: Write a short handoff of the current session (goal, what got done, where things stand, next steps) to the chat and to HANDOFF.md, so the next session can pick up where this one left off. Use when the user types /handoff, says "wrap up", or asks for a session summary.
---

# Handoff

Write a quick summary someone could read in 30 seconds and know where to start next time.

## 1. Check where things stand

Run these and use the real output:

- `git branch --show-current`
- `git status --short`
- `./gradlew test --console=plain` (Windows: `gradlew.bat test --console=plain`), and count passed and failed tests

## 2. Write the handoff

Use this format, plain language, a high school student should follow it. Keep each section to a few bullets.

```markdown
# Handoff: <date>

## Goal
What we were trying to do this session, in one or two sentences.

## Done
- What got finished (files changed, bugs fixed).

## Where things stand
- Branch: <branch>
- Uncommitted changes: <files, or "none">
- Tests: <X of Y passed>

## Next steps
- What to do next time, most important first.

## Open questions
- Anything undecided or waiting on someone. Leave out this section if there are none.
```

Base it on this conversation and the commands above. Don't guess: if something is unclear, say so under Open questions.

## 3. Save and show it

Save it to `HANDOFF.md` at the repo root, replacing any older one (it is git-ignored, so it survives branch
switches). Then print the same handoff in the chat.

To pick up next session, the user says: "Read HANDOFF.md and continue."
