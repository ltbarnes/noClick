NO CLICK

####################### RUNNING THE DEMO #########################

Download the ZIP file of the noClick project and uncompress it. Go into the master folder and make sure the NoClickDemo folder is there. 

Open the Terminal.app by finding it in the /Applications/Utilities/ folder or by searching for it using spotlight (cmd + space).

In the terminal type:

	java -version

and press enter. You should see something similar to what is shown below:

java version "1.7.0_71"
Java(TM) SE Runtime Environment (build 1.7.0_71-b14)
Java HotSpot(TM) 64-Bit Server VM (build 24.71-b01, mixed mode)

and make sure you have java version "1.7.0_..." or higher. If you don't have java or you are using an earlier version you can download the latest Java 1.7 SDK here:

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

To compile and run the program you first need to be in the NoClickDemo directory.

	1.	In the terminal type 'cd' then space then drag the NoClickFolder into the terminal window. The command should look like the example below except /your/path/to/ will be replaced by the files on your specific system.

			cd /your/path/to/NoClickDemo

		press enter.


	2.	To compile the program type the following command into the terminal:

		javac -d . -sourcepath src -cp lib/noclick.jar src/barnes/cs130dev/DemoApp.java


	3. 	The following command will start the program. To run the program again after closing just repeat step 3. If you close the terminal and reopen it make sure to repeat step 1 before attempting step 3 again.
	To run the program type:

		java -cp .:lib/noclick.jar barnes.cs130dev.DemoApp

