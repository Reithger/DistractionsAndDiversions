# DistractionsAndDiversions
Project for voice activated visual effects via the StylizedCaptions project I have also made. Primarily meant for novel visual effects based on spoken text during streams cause it's fun. Initially has a simple effect that makes all words spoken and picked up by the captioning software to appear on screen and bounce with simple physics rules. 

This includes the .jar runnable for StylizedCaptions within it, but that does have a dependency on the vosk Python library so make sure to run a 'pip install vosk' before you use this program. Captions will take a few seconds to set itself up before it starts processing text.

If you just want some captions, check out StylizedCaptions (though I still need to add some config menu options to it, time for another support library to make that less of a pain...)

Currently only has one Effect implemented but a system is present for adding more if you wanted to download this code yourself and modify it; I plan to add more to this over time now that I've got the baseline established.

Word Bounce Example (still-frame of them moving)
![image](https://github.com/user-attachments/assets/6e8f9984-a665-4939-bf2a-50ac3624b4a9)


Todo:
 - A config interface for manual activation of a visual effect
 - Conditional opening of the captions/voice recognition at all (would be more versatile for this to be visual effect first and cool audio attachment second, despite my focus on making this being that vocal control is cool to do)
 - Config menu for general editing of visual effects
