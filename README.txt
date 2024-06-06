About Graphing Calculator:
For my final Java Swing project I made a rudimentary graphing calculator!
Basically, it functions as a normal calculator that can process mathematical expressions,
and it can also graph linear functions/equations. For the evaluating math part of the code,
I converted math text that was typed as a String into Reverse Polish Notation, and then
wrote another method that can easily evaluate those. For the graphing portion of the code,
I used the paintLab we did in class as a basic framework for DrawingPanel, and then
added a method that plugs in two points into the equation that needs to be graphed
and graphs a line between those two points on the DrawingPanel.

Instructions:
Use the buttons on the screen to operate the program as you would a normal calculator.
Type out a mathematical expression and then press the equals button to evaluate it.
Some buttons that might seem different are cut and paste. Cut clears the text
and stores the values on screen and then paste allows the stored text to be pasted
at the end of existing text. Note that multiplication cannot be notated by adjacent
parenthesis; a multiply sign, or asterisk, must be used. In order to graph a linear function
the text entered must start with y= which can be entered with its respective button.
Following the y= must be entered one and only one x and then any other amount of operations.
When the function is typed, press graph and the line will be plotted on the graph on the right.

Files:
Calculator.java is the java file that contains all the code for the Calculator object.
When an instance of calculator is instantiated, in this case, Main.java, the Calculator class'
constructor is called and creates a calculator Swing window.
ElementButton is a class that extends JPanel and comes with a button that is preset
with the function to be clicked and append the button's corresponding symbol to the calculator's
JTextArea. It takes in the symbol as a parameter. The majority of buttons on the calculator
utilize this.

References:
https://en.wikipedia.org/wiki/Reverse_Polish_notation
I used this to figure out how calculators evaluate mathematical expressions through RPN
https://www.geeksforgeeks.org/deque-interface-java-example/#
I used this to learn what deque does to use instead of Stack.