package by.epamlab.elevator.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

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
		
		JPanel canvas = new ElevatorCanvas();
		canvas.setPreferredSize(new Dimension(260, 380));
		canvas.setMinimumSize(new Dimension(260, 380)); //WUT? //Do not work, srsly.
		
		final JTextArea text = new JTextArea("Have u press the button today?\n"/*"Вы еще не проигрывали сегодня?\n"*/);
		DefaultCaret textCaret = (DefaultCaret) text.getCaret();
		text.setCaretPosition(text.getDocument().getLength()); //autoscrolling
		text.setEditable(false);
		
		JScrollPane textScroll = new JScrollPane(text,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		final JButton godButton = new JButton("Press"/*"Проиграть"*/);
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
				text.append("You press the button: "/*"Сколько раз вы проигрывали: "*/ + proigralRaz + "\n"); //Do not know how to workaround \n
				//TODO: ELEVATION_TASK -> GET_STATE -> DETERMINE -> SWITCH STATE AND CAPTION
				godButton.setText("Pressed "/*"Проиграно: "*/ + proigralRaz);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(godButton);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(260, 380));
		mainPanel.add(textScroll, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);
		panel.add(mainPanel, BorderLayout.EAST);
		
		this.add(panel);
		pack();
	}

}
