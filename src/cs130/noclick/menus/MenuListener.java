package cs130.noclick.menus;

import java.io.File;

public interface MenuListener {
	
	void actionPerformed(MenuEvent e);
	
	public static class MenuEvent {
		
		public final File file;
		public final String command;
		
		public MenuEvent(File file, String command) {
			this.file = file;
			this.command = command;
		}
	}

}
