import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Runner {
	static final String PROPERTY_FILE = "elevator.config";
	static final Logger log = Logger.getLogger(Runner.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties properties = new Properties();
		FileInputStream propertyStream = null;
		
		log.info("Hello loggy!");
		
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
				} catch (IOException e) {}
			}
		}
	}

}
