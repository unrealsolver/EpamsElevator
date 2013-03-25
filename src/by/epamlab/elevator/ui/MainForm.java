package by.epamlab.elevator.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainForm extends JFrame {
	public MainForm() {
		setTitle("Elevator GUI");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainForm ex = new MainForm();
				ex.setVisible(true);
			}
		});
	}
}
