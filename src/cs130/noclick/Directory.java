package cs130.noclick;

import static cs130.noclick.Constants.FILE_LABEL_DIM;
import static cs130.noclick.Constants.MAX_FILES_DISPLAYED;
import static cs130.noclick.NoClick.BACK_IMAGE;
import static cs130.noclick.NoClick.BACK_IMAGE_S;
import static cs130.noclick.NoClick.HOME_IMAGE;
import static cs130.noclick.NoClick.HOME_IMAGE_S;
import static cs130.noclick.NoClick.NEXT_IMAGE;
import static cs130.noclick.NoClick.NEXT_IMAGE_S;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import cs130.noclick.elements.FileLabel;

public class Directory extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static FileLabel home_label;

	private File _dir;
	private List<FileLabel> _files;
	private List<FileLabel> _more;
	private FileLabel _moreLabel;
	private Directory _parent;
	private int _page;
	private int _totalPages;

	public Directory(File dir) {
		setLayout(null);
		setLocation(0, 0);

		setOpaque(false);

		_dir = dir;
		_files = new ArrayList<FileLabel>();
		_more = new ArrayList<FileLabel>();
		_page = 1;

		_parent = null;

		_moreLabel = null;
		home_label = new FileLabel(new File(System.getProperty("user.home")), HOME_IMAGE, HOME_IMAGE_S);

		addFilePanels();
	}

	public Directory(List<FileLabel> files, Directory parent, int page, int totalPages) {
		setLayout(null);
		setLocation(0, 0);

		setOpaque(false);

		_dir = null;
		_files = new ArrayList<>(files);

		_parent = parent;
		_page = page;
		_totalPages = totalPages;

		_more = null;
		_moreLabel = null;
		home_label = new FileLabel(new File(System.getProperty("user.home")), HOME_IMAGE, HOME_IMAGE_S);

		checkFilesSize();
	}

	private void addFilePanels() {
		File[] fileArray = _dir.listFiles();
		BufferedImage image;
		Icon icon, iconS;

		FileLabel fl;
		for (int i = 0; i < fileArray.length; i++) {
			if (!fileArray[i].isHidden()) {
				icon = FileSystemView.getFileSystemView().getSystemIcon(fileArray[i]);

				image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				if (icon != null)
					icon.paintIcon(null, g, 0, 0);

				icon = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
				iconS = new ImageIcon(image.getScaledInstance(72, 72, Image.SCALE_SMOOTH));

				fl = new FileLabel(fileArray[i], icon, iconS);
				_files.add(fl);
			}
		}
		_totalPages = _files.size() / MAX_FILES_DISPLAYED + 1;
		checkFilesSize();
	}

	private void checkFilesSize() {

		if (_files.size() > MAX_FILES_DISPLAYED) {
			_more = new ArrayList<>(_files.subList(MAX_FILES_DISPLAYED - 1, _files.size()));
			_files = new ArrayList<>(_files.subList(0, MAX_FILES_DISPLAYED - 1));
			_moreLabel = new FileLabel("NEXT", NEXT_IMAGE, NEXT_IMAGE_S);
			_files.add(0, _moreLabel);
		}
		if (_page > 1) {
			_files.add(new FileLabel("PREVIOUS", BACK_IMAGE, BACK_IMAGE_S));
		} else {
			_files.add(new FileLabel("BACK", BACK_IMAGE, BACK_IMAGE_S));
		}
		for (FileLabel fl : _files) {
			add(fl);
		}
		add(home_label);

	}

	public Directory reset() {
		if (_dir != null)
			return new Directory(_dir);

		Directory dir = new Directory(_parent.getFile());
		dir.resetSize(getSize());

		for (int page = 1; page < _page; page++) {
			dir = dir.getMore();
		}

		return dir;
	}

	public File getFile() {
		if (_dir == null)
			return _parent.getFile();
		return _dir;
	}

	public int getNextPage() {
		return _page + 1;
	}

	public int getPage() {
		return _page;
	}

	public String getPageString() {
		return "Page " + _page + " of " + _totalPages;
	}

	public String getPath() {
		if (_dir == null)
			return _parent.getPath();
		return _dir.getAbsolutePath();
	}

	public Directory getParentDirectory() {
		if (_parent == null) {
			File parent;
			if ((parent = _dir.getParentFile()) == null)
				return this;
			_parent = new Directory(parent);
		}
		_parent.resetSize(getSize());
		return _parent;
	}

	public Directory getMore() {
		Directory result = new Directory(_more, this, _page + 1, _totalPages);
		result.resetSize(getSize());
		return result;
	}

	public FileLabel checkIntersections(Point p) {
		Point loc;
		for (FileLabel fl : _files) {
			loc = fl.getLocation();
			if (fl.contains(p.x - loc.x, p.y - loc.y)) {
				return fl;
			}
		}
		if (_moreLabel != null && _moreLabel.contains(p.x - _moreLabel.getX(), p.y - _moreLabel.getY())) {
			return _moreLabel;
		}
		if (home_label.contains(p.x - home_label.getX(), p.y - home_label.getY())) {
			return home_label;
		}
		return null;
	}

	public void resetSize(Dimension dim) {
		setSize(dim);
		setFilePanels(dim);
	}

	private void setFilePanels(Dimension dim) {
		int x, y;
		double angle;
		int numIcons = _files.size();
		FileLabel fp;

		if (numIcons == 1) {// avoid division by zero.
			x = (int) Math.round((-dim.width * .375f) + dim.width / 2f + 0.5f);
			y = (int) Math.round(dim.height / 2f + 0.5f);
			_files.get(0).setLocation(x - FILE_LABEL_DIM.width / 2, y - FILE_LABEL_DIM.height / 2);

		} else {
			for (int i = 0; i < numIcons; i++) {
				angle = i * 1.5f * Math.PI / (numIcons - 1);

				x = (int) Math.round((Math.sin(angle) * dim.width * .375f) + dim.width / 2f + 0.5f);
				y = (int) Math.round((Math.cos(angle) * dim.height * .375f) + dim.height / 2f + 0.5f);

				fp = _files.get(i);
				fp.setLocation(x - FILE_LABEL_DIM.width / 2, y - FILE_LABEL_DIM.height / 2);
			}
		}
		angle = -Math.PI / 4;

		x = (int) Math.round((Math.sin(angle) * dim.width * .375f) + dim.width / 2f + 0.5f);
		y = (int) Math.round((Math.cos(angle) * dim.height * .375f) + dim.height / 2f + 0.5f);

		home_label.setLocation(x - FILE_LABEL_DIM.width / 2, y - FILE_LABEL_DIM.height / 2);
		home_label.setSize(FILE_LABEL_DIM);

	}

	public String getFileName() {
		if (_dir == null)
			return _parent.getFileName();
		return _dir.getName();
	}

}
