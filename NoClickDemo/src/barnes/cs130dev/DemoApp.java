package barnes.cs130dev;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;

import cs130.noclick.NoClick;

public class DemoApp {

	public DemoApp() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				JFrame frame = new JFrame("DemoApp");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new NoClick());

				frame.setExtendedState(Frame.MAXIMIZED_BOTH);
				frame.setMinimumSize(new Dimension(700, 600));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public static void main(String args[]) {
		new DemoApp();
	}
}