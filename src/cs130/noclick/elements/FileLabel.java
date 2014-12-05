package cs130.noclick.elements;

import static cs130.noclick.Constants.FILE_LABEL_DIM;
import static cs130.noclick.Constants.LABEL_FONT;
import static cs130.noclick.Constants.MENU_FONT;

import java.awt.Color;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;

public class FileLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File _file;
	private Icon _default;
	private Icon _selected;

	public FileLabel(File file, Icon norm, Icon selected) {
		super("", norm, JLabel.CENTER);

		if (file != null) {
			this.setText(file.getName());
		}

		_file = file;
		_default = norm;
		_selected = selected;

		init();
	}

	public FileLabel(String fakeName, Icon norm, Icon selected) {
		super(fakeName, norm, JLabel.CENTER);

		_file = null;
		_default = norm;
		_selected = selected;

		init();
	}

	public FileLabel(String fakeName) {
		super(fakeName, null, JLabel.CENTER);

		_file = null;
		_default = null;
		_selected = null;

		init();
	}

	private void init() {
		this.setBackground(Color.GRAY);
		this.setSize(FILE_LABEL_DIM);
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setHorizontalTextPosition(JLabel.CENTER);

		this.setFont(LABEL_FONT);

	}

	public boolean hasFile() {
		return _file != null;
	}

	public File getFile() {
		return _file;
	}

	public boolean isDirectory() {
		return _file.isDirectory();
	}

	public Icon select() {
		setIcon(_selected);
		setFont(MENU_FONT);
		return _default;
	}

	public void deselect() {
		setIcon(_default);
		setFont(LABEL_FONT);
	}

	public Icon getSelectedIcon() {
		return _selected;
	}

}
