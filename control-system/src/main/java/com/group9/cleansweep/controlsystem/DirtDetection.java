package com.group9.cleansweep.controlsystem;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.sensor_simulator.DirtSensor;

public class DirtDetection {

	@Getter
	private final int totalDirtCapacity = 50;
	@Getter
	@Setter
	private int dirtCount;
	@Getter
	@Setter
	private int unitOfDirt;
	@Getter
	@Setter
	private int totalDirtCollected = 0;
	@Getter
	@Setter
	private boolean isDirtCapacityFull = false;
	@Getter
	@Setter
	private boolean isMinimumPowerCapacityReached = false;
	private static DirtDetection dirtDetecting = new DirtDetection();
	


	public Map<String, Tile> setRandomDirt(FloorPlan floorPlan) {
		
		DirtSensor dirtSensor=new DirtSensor();
		return dirtSensor.setRandomDirt(floorPlan);
	}

	public int cleanDirt(Tile tile, DirtDetection dirtDetection) {
		int dirtAmount = tile.getDirtAmount();
		int totalDirtCollected=dirtDetection.getTotalDirtCollected();
		dirtCount = tile.getDirtAmount();
		System.out.println("Total Dirt Amount of tile " + tile.getId() + ": " + tile.getDirtAmount());
		
		for (int i = dirtAmount; i >= 0; i--) {
			if (tile.getDirtAmount() == 0) {
				System.out.println("Tile " + tile.getId() + " is completely clean ");
				break;
			} else {
				System.out.println("Cleaning tile: " + tile.getId());
				dirtCount--;
				totalDirtCollected++;
				isDirtCapacityFull = checkIfDirtCapacityFull(totalDirtCollected);

				if (isDirtCapacityFull) {
					StatusCheck statusCheck = new StatusCheck();
					System.out.println("-------------------------------------------------");
					System.out.println(" DIRT TANK FULL !!!Please empty the dirt tank !!!");
					System.out.println("-------------------------------------------------");
					emptyDirtTank();
			
				}
				
				tile.setDirtAmount(dirtCount);
				System.out.println("Current Dirt Amount of " + tile.getId() + " : " + dirtCount);
			}

		}
		return totalDirtCollected;

	}

	public void emptyDirtTank() {
		totalDirtCollected = 0;

		System.out.println("Dirt tank emptied!! Clean sweep is ready to vacuum again..");
	}

	public boolean checkIfDirtCapacityFull(int totalDirtCollected) {

		return totalDirtCollected >= totalDirtCapacity ? true : false;
	}

}