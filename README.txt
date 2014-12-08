NO CLICK

####################### RUNNING THE DEMO #########################

Download the ZIP file by pressing the "Download ZIP" button on the right side of this web page. Go into the master folder and make sure the NoClickDemo folder is there. 

Open the Terminal.app by finding it in the /Applications/Utilities/ folder or by searching for it using spotlight (cmd + space).

In the terminal type:

	java -version

and press enter. You should see something similar to what is shown below:

java version "1.7.0_71"
Java(TM) SE Runtime Environment (build 1.7.0_71-b14)
Java HotSpot(TM) 64-Bit Server VM (build 24.71-b01, mixed mode)

Make sure you have java version "1.7.0_..." or higher. If you don't have Java or you are using an earlier version you can download the latest Java 1.7 SDK here (1.8 should work as well if you'd rather download the latest Java version):

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

To compile and run the program you first need to be in the NoClickDemo directory.

	1.
	In the terminal type 'cd' then space then drag the NoClickDemo folder into the terminal window. The command should look like the example below except /your/path/to/ will be replaced by the files on your specific system.

		cd /your/path/to/NoClickDemo

	Press enter.


	2.
	To compile the program copy and paste the following command into the terminal:

		javac -d . -sourcepath src -cp lib/noclick.jar src/barnes/cs130dev/DemoApp.java

	Press enter.


	3.
	The command shown below will start the program. To run the program again after closing just repeat step 3. If you close the terminal and reopen it make sure to repeat step 1 before attempting step 3 again.
	Copy and paste the this command into the terminal:

		java -cp .:lib/noclick.jar barnes.cs130dev.DemoApp

	Press enter to start.



######################### USING THE DEMO ###########################

Select files and folders by moving the mouse around within the application. When the mouse hovers of an item it sticks to the cursor until another item is selected or an action is performed with the icon. To clear all items from the cursor drag the mouse to the cancel zone in the bottom left of the screen.

The current directory and page number is displayed in the upper left corner of the screen.

Actions:

	Open a folder:
		To descend into a folder drag its icon into the center of the app.

	Open/Copy/Delete a file:
		To open an options menu drag a file into the center of the app.
		A menu will appear with options to open, copy, or delete a file.
		Use the menu by dragging an option into the center or drag to the cancel area to leave.

	Go to parent folder:
		Drag the "Back" icon to the middle of the app.
		(Can only be done for the first page of a folder)

	Navigate between pages in a folder:
		Drag the "Next" or "Previous" icon to the middle of the app.

	Go to the Home Folder:
		Drag the "Home" icon to the center of the app.

