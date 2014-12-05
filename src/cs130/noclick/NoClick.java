package cs130.noclick;

import static cs130.noclick.Constants.BACK;
import static cs130.noclick.Constants.CURR_DEPTH;
import static cs130.noclick.Constants.FILE_LABEL_DIM;
import static cs130.noclick.Constants.HOME;
import static cs130.noclick.Constants.ICON_DEPTH;
import static cs130.noclick.Constants.MENU_DEPTH;
import static cs130.noclick.Constants.NEXT;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cs130.noclick.elements.FileLabel;
import cs130.noclick.elements.Zone;
import cs130.noclick.menus.FileMenu;
import cs130.noclick.menus.Menu;

public class NoClick extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static ImageIcon HOME_IMAGE;
	public static ImageIcon HOME_IMAGE_S;
	public static ImageIcon BACK_IMAGE;
	public static ImageIcon BACK_IMAGE_S;
	public static ImageIcon NEXT_IMAGE;
	public static ImageIcon NEXT_IMAGE_S;

	private String _currName;
	private Directory _currentDir;
	private FileLabel _selectedFile;
	private JLabel _selectedLabel;

	private Zone _actionZone;
	private Zone _cancelZone;

	private Menu _menu;

	public NoClick() {

		loadImages();

		this.setBackground(Color.PINK);
		this.setLayout(null);

		this.addMouseMotionListener(new MouseHandler());
		this.addMouseListener(new MouseHandler());
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				init();
			}
		});

		_currName = System.getProperty("user.home");
		_currentDir = new Directory((new File(_currName)));
		this.add(_currentDir, CURR_DEPTH, 0);

		_selectedLabel = new JLabel("", null, JLabel.CENTER);
		_selectedLabel.setSize(FILE_LABEL_DIM);
		_selectedLabel.setVerticalTextPosition(JLabel.BOTTOM);
		_selectedLabel.setHorizontalTextPosition(JLabel.CENTER);
		this.add(_selectedLabel, ICON_DEPTH, 0);

		_actionZone = new Zone();
		_cancelZone = new Zone();
		_cancelZone.setText("CANCEL");

		_menu = null;
	}

	private void loadImages() {
		HOME_IMAGE = new ImageIcon(NoClick.class.getResource(HOME));
		HOME_IMAGE_S = new ImageIcon(HOME_IMAGE.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		HOME_IMAGE = new ImageIcon(HOME_IMAGE.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

		BACK_IMAGE = new ImageIcon(NoClick.class.getResource(BACK));
		BACK_IMAGE_S = new ImageIcon(BACK_IMAGE.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		BACK_IMAGE = new ImageIcon(BACK_IMAGE.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

		NEXT_IMAGE = new ImageIcon(NoClick.class.getResource(NEXT));
		NEXT_IMAGE_S = new ImageIcon(NEXT_IMAGE.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		NEXT_IMAGE = new ImageIcon(NEXT_IMAGE.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
	}

	public void init() {
		_currentDir.resetSize(getSize());

		int width = getWidth();
		int height = getHeight();

		_actionZone.setLocation(width / 2, height / 2);
		_actionZone.setRadius(width / 20);

		_cancelZone.setLocation(width / 16, height - width / 16);
		_cancelZone.setRadius(width / 10);

		if (_menu != null) {
			_menu.resetSize(getSize());
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.GRAY);
		String path = _currentDir.getPath().replace("/", " > ");
		g2d.drawString(path, 15, 20);

		int index = path.indexOf(_currentDir.getFileName());
		int x = g2d.getFontMetrics().stringWidth(path.substring(0, index));
		g2d.drawString(_currentDir.getPageString(), x + 25, 40);

		if (_menu != null) {
			_menu.paint(g);
		}

		if (_selectedFile != null && _menu == null) {
			_actionZone.draw(g2d);
			_cancelZone.draw(g2d);
		}
	}

	private void unstickIcon() {
		_selectedFile.deselect();
		_selectedFile = null;
		_selectedLabel.setIcon(null);
		_selectedLabel.setText("");
	}

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			Point p = e.getPoint();

			_selectedLabel.setLocation(p.x - FILE_LABEL_DIM.width / 2, p.y - FILE_LABEL_DIM.height / 2);

			if (_menu == null) {
				checkDirectory(p);
				setActionText();
			} else {
				if (!_menu.update(p, _selectedFile.getFile())) {
					if (_menu.isDirChanged()) {
						remove(_currentDir);
						_currentDir = _currentDir.reset();
						_currentDir.resetSize(getSize());
						add(_currentDir, CURR_DEPTH, 0);
					}
					remove(_menu);
					_menu = null;
					unstickIcon();
				}

			}

			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Point p = e.getPoint();

			if (p.x < 0)
				p.x = 0;
			else if (p.x > getWidth())
				p.x = getWidth();
			if (p.y < 0)
				p.y = 0;
			if (p.y > getHeight())
				p.y = getHeight();

		}

		private void checkDirectory(Point p) {
			FileLabel label = _currentDir.checkIntersections(p);

			if (label != null) {
				if (_selectedFile != null) {
					_selectedFile.deselect();
				}
				_selectedFile = label;
				_selectedLabel.setIcon(label.select());
				_selectedLabel.setText("<html><div style=\"text-align: center;\">" + label.getText() + "</html>");
			}

			if (_selectedFile != null) {
				if (_cancelZone.intersects(p)) {
					unstickIcon();

				} else if (_actionZone.intersects(p)) {

					if (!_selectedFile.hasFile()) {
						remove(_currentDir);

						if (_selectedFile.getText().startsWith("NEXT"))
							_currentDir = _currentDir.getMore();
						else {
							_currentDir = _currentDir.getParentDirectory();
						}

						add(_currentDir, CURR_DEPTH, 0);

						unstickIcon();
					} else if (_selectedFile.isDirectory()) {
						remove(_currentDir);
						_currentDir = new Directory(_selectedFile.getFile());
						_currentDir.resetSize(getSize());
						add(_currentDir, CURR_DEPTH, 0);

						unstickIcon();
					} else {
						_menu = new FileMenu(_actionZone, _cancelZone);
						_menu.setSize(getWidth(), getHeight());
						_menu.setLocation(getX(), getY());
						_menu.init();
						add(_menu, MENU_DEPTH, 0);
					}

				}
			}
		}

		private void setActionText() {
			if (_selectedFile != null) {
				if (!_selectedFile.hasFile()) {
					if (_selectedFile.getText().startsWith("NEXT")) {
						_actionZone.setText("NEXT");
					} else {
						_actionZone.setText("BACK");
					}
				} else if (_selectedFile.isDirectory()) {
					_actionZone.setText("OPEN");
				} else {
					_actionZone.setText("OPTIONS");
				}
			}
		}

	}
}
