NO CLICK

####################### RUNNING THE DEMO #########################

Open the Terminal.app by finding it in the /Applications/Utilities/ folder or by searching for it using spotlight (cmd + space).

In the terminal type:

java -version

and make sure you have java version "1.7.0_..." and a Java SE Runtime environment. The latest Java 1.7 SDK downloads can be found here:

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

To compile and run the program you first need to be in the NoClickDemo directory.

	1.	In the terminal type 'cd' then space then drag the NoClickFolder into the terminal window. Press enter.

		cd /your/path/to/NoClickDemo


	2.	To compile the program type the following command into the terminal:

		javac -d . -sourcepath src -cp lib/noclick.jar src/barnes/cs130dev/DemoApp.java

	3. 	To run the program type:

		java -cp .:lib/noclick.jar barnes.cs130dev.DemoApp

	The DemoApp program should now be up and running on your Mac! To run the program again after closing just repeat step 3. If you close the terminal and reopen it make sure to repeat step 1 before attempting step 3 again.

