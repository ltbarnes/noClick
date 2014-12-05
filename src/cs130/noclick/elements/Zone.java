package cs130.noclick.elements;

import static cs130.noclick.Constants.LABEL_FONT;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;

;

public class Zone {

	private String _text;
	private Point _location;
	private int _radius;
	private Ellipse2D.Double _ellipse;

	public Zone() {
		super();
		_location = new Point(0, 0);
		_radius = 10;
		_ellipse = new Ellipse2D.Double(_location.x - _radius, _location.y - _radius, _radius * 2, _radius * 2);
	}

	public void setLocation(int x, int y) {
		_location = new Point(x, y);
		_ellipse.x = x - _radius;
		_ellipse.y = y - _radius;
	}

	public void setRadius(int radius) {
		_radius = radius;
		_ellipse = new Ellipse2D.Double(_location.x - _radius, _location.y - _radius, _radius * 2, _radius * 2);
	}

	public void setText(String str) {
		_text = str;
	}

	public void draw(Graphics2D g) {

		g.setStroke(new BasicStroke(_radius / 10));
		g.setColor(new Color(.75f, .75f, .75f));

		g.setFont(LABEL_FONT);

		float width = g.getFontMetrics().stringWidth(_text);
		g.drawString(_text, _location.x - width / 2.f, _location.y + 5.f);

		g.setColor(Color.LIGHT_GRAY);
		g.draw(_ellipse);
	}

	public boolean intersects(JLabel label) {
		Point c = new Point(Math.max(_location.x, label.getX()), Math.max(_location.y, label.getY()));
		c.x = Math.min(c.x, label.getX() + label.getWidth());
		c.y = Math.min(c.y, label.getY() + label.getHeight());
		Point d = new Point(c.x - _location.x, c.y - _location.y);
		if (d.x * d.x + d.y * d.y <= _radius * _radius)
			return true;
		return false;
	}

	public boolean intersects(Point p) {
		Point v = new Point(p.x - _location.x, p.y - _location.y);
		return (v.x * v.x + v.y * v.y) <= (_radius * _radius);
	}

}
