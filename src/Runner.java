import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import core.ElevationTask;
import core.RandomElevationTask;

import by.epamlab.elevator.ui.MainForm;

public class Runner {
	static final String PROPERTY_FILE = "elevator.config";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties properties = new Properties();
		FileInputStream propertyStream = null;

		try {
			propertyStream = new FileInputStream(PROPERTY_FILE);
			properties.load(propertyStream);

			System.out.println(properties.getProperty("storiesNumber"));
			System.out.println(properties.getProperty("elevatorCapacity"));
			System.out.println(properties.getProperty("passengersNumber"));
			System.out.println(properties.getProperty("animationBoost"));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (propertyStream != null) {
				try {
					propertyStream.close();
				} catch (IOException e) {
				}
			}
		}
		
		/* Construct task */
		final ElevationTask elevationTask = new RandomElevationTask(10, 100, 6);
		
		
		//TODO if animationBoost...
		/* Set interactive mode */
		//elevationTask.setInteractive(true);
		/* Starting GUI */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/* Start main form */
				JFrame form = new MainForm(elevationTask);
				form.setVisible(true);
			}
		});
		
		/* Start all processes */
		elevationTask.startElevation();
		
	}

}
