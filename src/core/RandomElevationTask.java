package core;

import java.util.Random;

public class RandomElevationTask extends ElevationTask {
	private static Random rnd = new Random();
	
	public RandomElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		super(totalStoreys, totalPassengers, elevatorCapacity);

		Passenger passenger;
		int currentStorey = 0;
		int destinationStorey;
		int[] storeyDistribution = createStoreyDistribution(totalStoreys, totalPassengers);
		
		for (Storey storey : storeys) {
			
			for (int i = 0; i < storeyDistribution[currentStorey]; i++) {
				//Find new destination storey for the passneger. 
				//Current storey number must not be equal to current storey number
				while ((destinationStorey = rnd.nextInt(totalStoreys)) == currentStorey) {
					//pass
				}
				
				//Creating new passenger
				passenger = new Passenger(destinationStorey);
				//Pushing passenger to story
				storey.takePassenger(passenger);
			}
			
			currentStorey++;
		}
	}
	
	private int[] createStoreyDistribution(int totalStoreys, int totalPassengers) {
		int[] storeyDistribution = new int[totalStoreys];
		int undistributedPassengers = totalPassengers;
		
		//While we have undistributed passengers
		while (undistributedPassengers-- > 0) {
			//Increase randomly selected storey's passengers count
			storeyDistribution[rnd.nextInt(totalStoreys)]++;
		}
		
		return storeyDistribution;
	}
	
}
