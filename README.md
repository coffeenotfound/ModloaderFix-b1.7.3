# Modloader Fix for Beta 1.7.3 #

A fixed version of Risugami's ModLoader for Minecraft Beta 1.7.3 that fixes the "URI is not hierarchical" exception.


## Installing (Using the 'New'/Second-Oldest Launcher) ##

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
13. Your done! Keep on modding and enjoy the game!

_Shoutout to Risugami!_
