package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.jsfml.system.Clock;

public class ElevatorCanvas extends JPanel {
	private DynamicWidget box;
	private Clock clock;
	private float frameTime;
	
	public ElevatorCanvas() {
		super();
		init();
		startAnimation();
	}
	
	public void init() {
		this.setBackground(Color.gray);
		box = new BaseWidget();
		clock = new Clock();
	}
	
	public void startAnimation() {
        SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                	repaint();
                    Thread.sleep(20);
                }
            }
        };

        sw.execute();
    }
	//TODO Or paint component?
	public void paint(Graphics g) {
		frameTime = clock.getElapsedTime().asMicroseconds();
		clock.restart();
		super.paint(g);
		//repaint();
		Graphics g2d = (Graphics2D) g;
		box.update(frameTime);
		box.draw(g2d);
	}
}
