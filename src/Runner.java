import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
				} catch (IOException e) {}
			}
		}
	}

}
