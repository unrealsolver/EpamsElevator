package by.epamlab.elevator.core;

import java.util.Arrays;
import java.util.Random;

public class RandomElevationTask extends ElevationTask {
	private static Random rnd = new Random();
	/**
	 * Creates elevation task with randomly distributed passengers
	 * @param totalStoreys
	 * @param totalPassengers
	 * @param elevatorCapacity
	 */
	public RandomElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		super(totalStoreys, totalPassengers, elevatorCapacity);
		
		if (totalStoreys < 2 || totalStoreys < 1 || elevatorCapacity < 1) {
			//FIXME throws new SomeException
			System.err.println("Do not want");
			System.exit(1);
		}
		
		Passenger passenger;
		int currentStorey = 0;
		int destinationStorey;
		int[] storeyDistribution = createStoreyDistribution(totalStoreys, totalPassengers);
		
		for (Storey storey : storeys) {
			
			for (int i = 0; i < storeyDistribution[currentStorey]; i++) {
				//Find new destination storey for the passenger. 
				//Current storey number must not be equal to current storey number
				while ((destinationStorey = rnd.nextInt(totalStoreys)) == currentStorey) {
					//pass
				}

				//Creating new passenger
				passenger = new Passenger(destinationStorey);
				
				//Pushing passenger to storey's dispatch container
				storey.addPassenger(passenger);
				
				passengersThreads.add(new Thread(passengersThreadGroup,
						new PassengerThread(this, passenger),
						new String("PASSENGER_" + passenger.getId())));
				
			}
			
			currentStorey++;
		}
		
		log.info("Passenger distribution: " + Arrays.toString(storeyDistribution));
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
