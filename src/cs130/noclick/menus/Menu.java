package cs130.noclick.menus;

import static cs130.noclick.Constants.MENU_FONT;
import static cs130.noclick.Constants.MENU_LABEL_DIM;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cs130.noclick.elements.Zone;
import cs130.noclick.menus.MenuListener.MenuEvent;

public class Menu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<JLabel> _actions;
	private Point _currentDefaultPos;
	private JLabel _currentLabel;

	private Zone _actionZone;
	private Zone _cancelZone;

	private MenuListener _actionListener;
	private MenuListener _cancelListener;

	// drawing stuff
	private List<Line2D.Double> _lines;

	public Menu(List<String> actions, Zone action, Zone cancel) {
		super();
		setLayout(null);
		setOpaque(false);

		_actions = new ArrayList<>();
		_lines = new ArrayList<>();

		_actionZone = action;
		_cancelZone = cancel;

		for (String text : actions) {
			JLabel label = new JLabel(text, JLabel.CENTER);
			label.setSize(label.getPreferredSize());
			label.setFont(MENU_FONT);
			label.setSize(MENU_LABEL_DIM);
			label.setForeground(Color.DARK_GRAY);
			_actions.add(label);
			add(label);
		}

		_currentLabel = null;
		_currentDefaultPos = null;
	}

	public void init() {

		JLabel label;
		int num_labels = _actions.size();
		_lines.clear();

		float ex = getWidth() / 2.f;
		float why = getHeight() / 2.f;
//		float w = ex * .82f;
		float h = why * .82f;

		int x, y;

		double a = 2.0 * Math.PI / (num_labels);
		double angle;

		for (int i = 0; i < num_labels; i++) {
			angle = i * a;

			x = (int) Math.round(-Math.cos(angle) * h * .75f + ex + 0.5f);
			y = (int) Math.round(Math.sin(angle) * h * .75f + why + 0.5f);

			label = _actions.get(i);
			label.setLocation(x - MENU_LABEL_DIM.width / 2, y - MENU_LABEL_DIM.height / 2);

			angle = i * a - a / 2.f;

			x = (int) Math.round(-Math.cos(angle) * h + ex + 0.5f);
			y = (int) Math.round(Math.sin(angle) * h + why + 0.5f);

			_lines.add(new Line2D.Double(ex, why, x, y));
		}
	}
	
	public boolean isDirChanged() {
		return false;
	}
	
	public File getFile() {
		return null;
	}

	public void setActionListener(MenuListener listener) {
		_actionListener = listener;
	}
	
	public void setCancelListener(MenuListener listener) {
		_cancelListener = listener;
	}
	

	public boolean update(Point p, File file) {

		Point loc;
		for (JLabel label : _actions) {
			if (label.equals(_currentLabel))
				continue;

			loc = label.getLocation();
			if (label.contains(p.x - loc.x, p.y - loc.y)) {
				if (_currentLabel != null)
					_currentLabel.setLocation(_currentDefaultPos);

				_currentLabel = label;
				_currentDefaultPos = label.getLocation();
				_actionZone.setText(label.getText());
				break;
			}
		}

		if (_currentLabel != null) {
			_currentLabel.setLocation(p.x - _currentLabel.getWidth() / 2, p.y - _currentLabel.getHeight());

			if (_actionListener != null && _actionZone.intersects(p))
				_actionListener.actionPerformed(new MenuEvent(file, _currentLabel.getText()));
		}
		
		if (_cancelZone.intersects(p)) {
			_cancelListener.actionPerformed(new MenuEvent(file, ""));
		}
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// white out under layer
		g2d.setColor(new Color(255, 255, 255, 200));
		g2d.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));

		// setup and draw circle gradient
		float dist[] = { 0f, 1f };
		Color colors[] = { new Color(255, 255, 255, 30), new Color(127, 127, 127, 240) };
		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.f, getHeight() / 2.f),
				getHeight() / 2.25f, dist, colors);
		g2d.setPaint(rgp);
		g2d.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));


		// draw lines with gradient
		if (_currentLabel == null)
			colors[0] = Color.LIGHT_GRAY;
		else
			colors[0] = Color.WHITE;

		rgp = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.f, getHeight() / 2.f), getHeight() / 2.25f,
				dist, colors);
		g2d.setPaint(rgp);
		g2d.setStroke(new BasicStroke(2));

		for (Line2D.Double line : _lines) {
			g2d.draw(line);
		}

		// draw zones
		if (_currentLabel != null) {
			_actionZone.draw(g2d);
		}
		_cancelZone.draw(g2d);

		// draw labels
		super.paintComponent(g);
	}
	
	public void resetSize(Dimension size) {
		this.setSize(size);
		init();
	}

}
