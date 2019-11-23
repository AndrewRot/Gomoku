Andrew Rottier, acrottier@wpi.edu
Barrett Wolfson, bwolfson@wpi.edu
Kenneth Colpritt, kcolpritt@wpi.edu


~EXTRA CREDIT: Upgrade the AI to beat our original AI~

Our initial AI had some weaknesses to it. It was only capable at looking for defensive moves and had no understanding of offense. It also was not capable of calculating streaks for diaganol directions. To upgrade out AI, we incorperated streak checking to the diaganol directions. Additionally, we added offensive moves to our AI's gameplay. Instead of just checking the board for opponent streaks, we determined that it was equally important to check a board state for the AI's streaks as well. For example, we weighted the following two scenarios equally:

_OOOO = _XXXX
O_OOO = X_XXX
OO_O = XX_X
etc.

It is just as good as a move to increase our AI's streak than it is to block an opponent's streak - depending on the length of the opponent streak. 


This is a project completed for CS4341 (Intro to Artifical Intelligence)
See the github repository for this project: https://github.com/AndrewRot/Gomoku


This project was part of an AI design competition for Gomoku (a game variation from the popular board game Go). This project uses concepts such as minimax, alpha beta pruning, and custom heuristics to generate moves for Gomoku.

********************************************************************
There are two runnable versions of this game Gomoku and GomokuAI. 
-Gomoku is used for humans to play against our AI.
-GomokuAI was used for our in-class competition and ran against an adversarial AI and a referee program used to control gameplay. The source code for the referee can be found at: https://github.com/samogden/WPI.CS4341


********************************************************************
INSTRUCTIONS to compile:
Open terminal and navigate to src folder in the project and use command:

	javac GomokuAI.java 

NOTE, if there is a warning, recompile with this command

	javac -Xlint:unchecked  GomokuAI.java 

Once compiled, run the program with:

	java GomokuAI
