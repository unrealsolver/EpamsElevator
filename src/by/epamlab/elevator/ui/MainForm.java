package by.epamlab.elevator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import core.ElevationTask;
import core.SwingAppender;

public class MainForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ElevationTask elevationTask;
	private final JButton godButton = new JButton("press me");
	
	public MainForm(ElevationTask elevationTask) {
		super();
		this.elevationTask = elevationTask; 
		initUI();
	}
	
	public final void initUI() {
		setTitle("Elevator GUI");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel canvas = new ElevatorCanvas(elevationTask, godButton);
		canvas.setPreferredSize(new Dimension(360, 480));
		canvas.setMinimumSize(new Dimension(360, 480)); //WUT? //Do not work, srsly.
		
		final JTextArea textArea = new JTextArea("");
		textArea.setCaretPosition(textArea.getDocument().getLength()); //autoscrolling
		textArea.setEditable(false);
		
		SwingAppender appender = new SwingAppender(textArea);
		elevationTask.getLogger().addAppender(appender);
		
		JScrollPane textScroll = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		final JButton stepButton = new JButton("Step");
		
		final class Cell {
			private int value; 
			public Cell(int t) { value = t; }
			public void inc() { value += 1; };
			public String toString() { return String.valueOf(value); };
		}
		
		final Cell counter = new Cell(0);
		stepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				counter.inc();
				textArea.append("__button_pressed__" + counter + "\n");
				//TODO: ELEVATION_TASK -> GET_STATE -> DETERMINE -> SWITCH STATE AND CAPTION
				stepButton.setText("Pressed " + counter);
				synchronized (elevationTask.getElevatorLock()) {
					elevationTask.getElevatorLock().notify();
				}
			}
		});
		
		godButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				elevationTask.interrupt();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(stepButton);
		buttonPanel.add(godButton);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(360, 380));
		mainPanel.add(textScroll, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);
		panel.add(mainPanel, BorderLayout.EAST);
		
		this.add(panel);
		pack();
	}

}
