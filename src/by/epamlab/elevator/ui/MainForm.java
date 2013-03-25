package by.epamlab.elevator.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class MainForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainForm() {
		super();
		initUI();
	}
	
	public final void initUI() {
		setTitle("Elevator GUI");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		
		JPanel canvas = new JPanel();
		canvas.setBackground(Color.gray);
		canvas.setPreferredSize(new Dimension(260, 380));
		canvas.setMinimumSize(new Dimension(260, 380)); //WUT?
		
		JTextArea text = new JTextArea("Hello texty!");
		text.setEditable(false);
		
		JButton godButton = new JButton("<Do something>");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(godButton);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(160, 380));
		mainPanel.add(text, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);
		panel.add(mainPanel, BorderLayout.EAST);
		
		this.add(panel);
		pack();
	}
}
