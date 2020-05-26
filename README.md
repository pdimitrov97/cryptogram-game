# Cryptogram Game
This is a simple cryptogram game using substitution cipher written in Java.

## Description
A cryptogram is a word puzzle where players are presented with an encrypted phrase. The aim of the game is to identify the original message.

The game has the following features:
- Given a phrase, it generates a unique key and encrypts the prase. The encrypte phrase is presented on the screen and next each letter there is an empty box that the player has to fill in order to get the original phrase.
- Allow players to create accounts and keep track of their statistics.
- Scoreboard with showing the stats of the top 10 players.
- Players can get hints, skip a particular game or give up and reveal the answer of a particular game.
- Players can save and load games in case they have to stop playing.
- When filling or deleting a letter, the same action is applied to every hole with the same encrypted letter. Just clicking on a hole makes all holes with the same letter in different colour so it is easier for the player to identify them.

## How to use it
The program has a GUI. When started, the player has to type in their name and either register or login. This is done so the game can keep track of the player's performance.

After this, the player can go in the <b>Game</b> tab and start a new game or load a saved one. While playing the player can receive hints using the <b>Hint</b> button and view the frequencies of all letters by enabling it with the <b>View Frequencies</b> checkbox.

The player can also <b>Skip</b> a particular game and start a new one, <b>Give up</b> and reveal the answer for the current game and <b>Save</b> the progress of the current game and continue later.

There is also a <b>Scoreboard</b> tab where the player can view statistics of the top 10 about completed cryptograms, average time to complete a cryptogram, best time and accuracy.

## Requirements:
- JDK 8
- Maven

## Build:
````
mvn clean install
````

<img src="https://user-images.githubusercontent.com/15669909/82898801-8b3f1500-9f62-11ea-9c2a-22b16e201ec4.jpg">
<img src="https://user-images.githubusercontent.com/15669909/82898796-8aa67e80-9f62-11ea-862c-f4db874346ad.jpg">
<img src="https://user-images.githubusercontent.com/15669909/82898800-8b3f1500-9f62-11ea-8d29-600bfe73be39.jpg">
