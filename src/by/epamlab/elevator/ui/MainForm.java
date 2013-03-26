package by.epamlab.elevator.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		//setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		
		JPanel canvas = new JPanel();
		canvas.setBackground(Color.gray);
		canvas.setPreferredSize(new Dimension(260, 380));
		canvas.setMinimumSize(new Dimension(260, 380)); //WUT? //Do not work, srsly.
		
		final JTextArea text = new JTextArea("Вы еще не проигрывали сегодня?\n");
		text.setEditable(false);
		
		final JButton godButton = new JButton("Проиграть");
		final class Cell {
			private int value; 
			public Cell(int t) { value = t; }
			public void set(int t) { value = t; }
			public int get() { return value; }
			public void inc() { value += 1; };
			public String toString() { return String.valueOf(value); };
		}
		final Cell proigralRaz = new Cell(0);
		godButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				proigralRaz.inc();
				text.append("Сколько раз вы проигрывали: " + proigralRaz + "\n"); //Do not know how to workaround \n
				//TODO: ELEVATION_TASK -> GET_STATE -> DETERMINE -> SWITCH STATE AND CAPTION
				godButton.setText("Проиграно: " + proigralRaz);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(godButton);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(260, 380));
		mainPanel.add(text, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);
		panel.add(mainPanel, BorderLayout.EAST);
		
		this.add(panel);
		pack();
	}

}
