Andrew Rottier, acrottier@wpi.edu
Barret Wolfson, bwolfson@wpi.edu
Kenneth Colpritt, uhh


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