- The Game is created by Group 14: Darrick Gunawan, Mahyar Sharafi Laleh, Hanxi Chen, Boyu Zhang

- [](Prerequisites)
Since the game is built using Apache Maven, the user need to have Maven installed on their local machine. Follow this link to install Maven:
[](https://maven.apache.org/download.cgi)

- [](Build the game)
Follow these steps to build the game using maven:
1. Clone the repository to local machine
2. Open the shell of your choice (eg. Command Prompt(Windows users) or Terminal(Mac or Linux users))
3. Navigate to the project directory
4. Give the command to build and test the project: "mvn package"

- [](Runing the game)
How the game runs:
1. once you run the "SimpleGame" class you will see a title screen opens up.
2. you will have 2 options which is to start the game or exitting.You can choose the options by left clicking the mouse and pressing "start game" after.
3. "exit game" will close the window however, choosing "start game" will take you to the game.
4. You can play the game by using the arrow keys on keyboard to move the character and collect the rewards.
5. if your score or lives fall below zero you will see the gameover screen.
6. in the gameover screen you can choose to retry (select and press "restart game") or you can slect "exit game" which close the game.
7. if you can reach the end of the game where the door is, you will win and the win screen will apear.
8. you will have the option to retry by pressing "restart game" or quit by pressing "exit game" after winning the game.

- [](Testing the game)
1. Open the shell of your choice (eg. Command Prompt(Windows users) or Terminal(macOS or Linux users))
2. Navigate to the project directory
3. Give the command to run the tests:"mvn test"

