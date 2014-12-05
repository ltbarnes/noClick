NO CLICK DEMO

CONTINUE READING TO COMPILE AND RUN THE DEMO!!!

Open Terminal.app from the /Applications/Utilities/ folder (or by searching for it using spotlight [cmd + space]).

In the terminal type:

java -version

and make sure you have java version "1.7.0_..." and a Java SE Runtime environment. The latest Java 1.7 SDK downloads can be found here:

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

To compile and run the program you first need to be in the NoClickDemo directory. In the terminal app type 'cd' then space then drag the NoClickFolder into the terminal window. Press enter:

cd /your/path/to/NoClickDemo

To compile and run the program type the following commands into the terminal exactly as they are shown below:

javac -d . -sourcepath src -cp lib/noclick.jar src/barnes/cs130dev/DemoApp.java

java -cp .:lib/noclick.jar barnes.cs130dev.DemoApp

The DemoApp program should now be up and running on your Mac! To run the program again after closing just type the last line into the terminal again.

