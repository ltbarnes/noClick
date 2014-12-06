package cs130.noclick.menus;

import java.awt.Desktop;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import cs130.noclick.elements.Zone;

public class FileMenu extends Menu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static List<String> fileMenuActions = Arrays.asList("Open", "Copy", "Delete");

	private boolean _active;
	private File _file;

	public FileMenu(Zone action, Zone cancel) {
		super(fileMenuActions, action, cancel);
		_active = true;

		this.setActionListener(new MenuListener() {

			@Override
			public void actionPerformed(MenuEvent e) {
				switch (e.command) {
				case "Open":
					try {
						Desktop.getDesktop().open(e.file);
					} catch (IOException e1) {
						System.err.println("Can't open file: " + e.file.getName());
					}
					break;
				case "Copy":
					try {
						Files.copy(e.file.toPath(), Paths.get(e.file.getAbsolutePath() + " copy"),
								StandardCopyOption.COPY_ATTRIBUTES);
					} catch (IOException e1) {
						System.err.println(e.file.getName() + " already exists.");
					}
					_file = e.file;
					break;
				case "Delete":
					try {
						String trash = System.getProperty("user.home") + "/.Trash";
						Files.move(e.file.toPath(), Paths.get(trash + "/" + e.file.getName()), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e1) {
						System.err.println(e.file.getName() + " cannot be deleted.");
					}
					_file = e.file;
					break;
				default:
					break;
				}
				_active = false;
			}

		});

		this.setCancelListener(new MenuListener() {

			@Override
			public void actionPerformed(MenuEvent e) {
				_active = false;
			}

		});
	}

	@Override
	public boolean update(Point p, File file) {
		super.update(p, file);

		return _active;

	}

	@Override
	public boolean isDirChanged() {
		return _file != null;
	}

	@Override
	public File getFile() {
		return _file;
	}

}
