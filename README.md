# Modloader Fix for Beta 1.7.3 #

A fixed version of Risugami's ModLoader for Minecraft Beta 1.7.3 that fixes the "URI is not hierarchical" exception.


## Download Links ###
If you just want to use the fixed version of Modloader you only have to download the 'Modloader Fix'. **It's a complete version of Modloader, you don't need to install the broken Modloader first, only the fixed one.**

_Please let me know should any link be broken!_
* [ModLoader Fixed Version b1.7.3 v1.0.0](https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases) ([Direct](https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases/download/v1.0.0/ModLoader.Fix.b1.7.3-1.0.0.jar))

Other links:
* [MCP 4.3](http://minecraft.gamepedia.com/Programs_and_editors/Mod_Coder_Pack) ([Direct](http://www.mediafire.com/file/03d94f13c9ulj5a/mcp43.zip))
* [Risugami's ModLoader b1.7.3](https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases) ([Direct](https://github.com/coffeenotfound/ModloaderFix-b1.7.3/raw/master/jars/ModLoader%20b1.7.3%20Original.zip))


## Installing (Using the 'New'/Second-Oldest Launcher, Not the _Very_ New One!) ##

1. Open the Launcher and create a new Profile with the Beta 1.7.3 version
2. Start the game with the new profile and close it again (just so it downloads the jars)
3. Close the Launcher
4. Go into `.minecraft/versions`, copy `b1.7.3` and name it `b1.7.3-modded` (or something similar)
5. Go into `b1.7.3-modded` and replace all file's name with the new folder name
6. Make a backup of the jar
7. Open `b1.7.3-modded.json`, replace the value of `id` right at the top with the folder name (`b1.7.3-modded`)
8. Still in the file, scroll down to the bottom and remove the last `"downloads": { ... }` (only the one that contains `"client": {...}`!)
9. Save and close the file
10. Now you can mod the jar
11. Reopen the launcher and create a new profile
12. Select the new `old_beta b1.7.3-modded` version
13. Your done!

## Setting Up MCP 4.3 ##

1. Extract the MCP zip
2. Copy `.minecraft/bin` folder into `/jars` (if old launcher)
    * For new launcher:
    1. Copy the (modded with the original ModLoader) `b1.7.3-modded.jar` into `/jars/bin` and rename to `minecraft.jar`
    2. Find and copy the `lwjgl-2.9.0` jar from `/libraries` of the new launcher to `/jars/bin` and rename to `lwjgl.jar`
    3. Find and copy the `lwjgl_util-2.9.0` jar from `/libraries` of the new launcher to `/jars/bin` and rename to `lwjgl_util.jar`
    4. Find and copy the `jinput-2.0.5` jar from `/libraries` of the new launcher to `/jars/bin` and rename to `jinput.jar`
    5. Extract the natives out of `lwjgl-platform-2.9.0-natives-*.jar` to `/jars/bin/natives`
    6. Extract the natives out of `jinput-platform-2.0.5-natives-*.jar` to `/jars/bin/natives`

3. Run decompile.bat
    * How to fix 'Couldnt convert string to float':
    1. Go into `/runtime/commands.py`
    2. Comment out ('#') lines 502 and 503 (`for bla md5srvlist bla`)

4. Copy the ModLoader Fix source into `/src/...`

## How to Recompile and Build ModLoader Fix ##

1. Run `recompile.bat`
2. Run `reobfuscate.bat`
3. Retrieve modded classes from `/reobf` and put into a jar

_Shoutout to Risugami!_
