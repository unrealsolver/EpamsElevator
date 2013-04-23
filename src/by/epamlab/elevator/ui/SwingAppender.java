package by.epamlab.elevator.ui;

import javax.swing.JTextArea;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class SwingAppender extends AppenderSkeleton {
	private static final PatternLayout pattern = new PatternLayout("%d{ABSOLUTE} %5p %t %c{1}:%M:%L - %m%n");
	private final JTextArea textArea;
	
	public SwingAppender(JTextArea textArea) {
		this.textArea = textArea;
	}
	
    @Override
    protected void append(LoggingEvent event) {
        textArea.append(pattern.format(event));
    }

    public void close() {
    }

    public boolean requiresLayout() {
        return false;
    }

}