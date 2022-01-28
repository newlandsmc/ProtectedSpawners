# ProtectedSpawners

___
Plugin designed for [SemiVanilla-MC](https://github.com/SemiVanilla-MC/SemiVanilla-MC).
Simple plugin to help protect blocks, to break these blocks the player must break the block they want to break, confirm it by command and break it again.
It's only after this second break that the block will end up being broken. Any block can be added to the protected blocks by adding it in the config.

## **Downloads**
Downloads can be obtained on the [github actions page.](https://github.com/SemiVanilla-MC/ProtectedSpawners/actions)

## **Building**

#### Initial setup
Clone the repo using `git clone https://github.com/SemiVanilla-MC/ProtectedSpawners.git`.

#### Compiling
Use the command `./gradlew build --stacktrace` in the project root directory.
The compiled jar will be placed in directory `/build/libs/`.

## **Commands**

| Command  | Description                                                          | Permission                |
|----------|----------------------------------------------------------------------|---------------------------|
| confirm  | Allows the player to break a block that's protected by this plugin.  | protectedspawners.confirm |

## **Permissions**

| Permission                 | Description                         |
|----------------------------|-------------------------------------|
| protectedspawners.confirm  | Permission for the confirm command. |

## **Configuration**

```yaml
# Magic value used to determine auto configuration updates, do not change this value
config-version: 1

# How much time in ticks does the player get to break a block.
command-time-out: 300

messages:
  # Message send when a player can break a protected block.
  player-command-can-break: <red>You can now break protected blocks.
  # Message send when the time runs out.
  player-command-time-out: <red>Timed out, rerun /<command> to break protected blocks
  # Message send when a player can't break this block.
  break-block-failed: <red>You can't break this block, to break it do /<command> and
    break this block
  
# List of materials/blocks that must be confirmed to be broken.
# Containers that are added to this list are only protected if they have an active loottable.
materials-enabled:
  - CHEST
  - SPAWNER
  - BARREL
```

## **Support**

## **License**
[LICENSE](LICENSE)