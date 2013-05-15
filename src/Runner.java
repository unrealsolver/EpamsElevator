import java.io.FileInputStream;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.pattern.IntegerPatternConverter;

import by.epamlab.elevator.core.ElevationTask;
import by.epamlab.elevator.core.RandomElevationTask;
import by.epamlab.elevator.ui.MainForm;

public class Runner {
	static final String PROPERTY_FILE = "elevator.config";
	
	public static int toInt(String arg) throws NumberFormatException {
		return Integer.parseInt(arg);	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties properties = new Properties();
		FileInputStream propertyStream = null;
		
		int storiesNumber = 0;
		int elevatorCapacity = 0;
		int passengerNumber = 0;
		int animationBoost = 0;
		int interactive = 0;
		
		try {
			propertyStream = new FileInputStream(PROPERTY_FILE);
			properties.load(propertyStream);

			storiesNumber = toInt(properties.getProperty("storiesNumber"));
			elevatorCapacity = toInt(properties.getProperty("elevatorCapacity"));
			passengerNumber = toInt(properties.getProperty("passengersNumber"));
			animationBoost = toInt(properties.getProperty("animationBoost"));
			interactive = toInt(properties.getProperty("interactive"));
			
		} catch (IOException | NumberFormatException e) {
			System.err.println("Cant read property file! Aborting...");
			System.err.println(e.getMessage());
			System.exit(1);
		} finally {
			if (propertyStream != null) {
				try {
					propertyStream.close();
				} catch (IOException e) {
				}
			}
		}
		
		/* Construct task */
		final ElevationTask elevationTask = 
				new RandomElevationTask(storiesNumber, passengerNumber, elevatorCapacity);
		
		
		//TODO if animationBoost...
		/* Set interactive mode */
		elevationTask.setInteractive(interactive == 1);
		elevationTask.setAnimationBoost(animationBoost);
		/* Starting GUI */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Start main form 
				JFrame form = new MainForm(elevationTask);
				form.setVisible(true);
			}
		});

		/* Start all processes */
		elevationTask.startElevation();
		
	}

}
