package com.group9.cleansweep.controlsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.sensor_simulator.DirtSensor;

import lombok.Getter;
import lombok.Setter;

public class DirtDetection {
	private static Logger logger = LoggerFactory.getLogger(DirtDetection.class);
	@Getter
	@Setter
	private static int totalDirtCapacity = 50;
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

	public void setRandomDirt(FloorPlan floorPlan) {

		DirtSensor dirtSensor = new DirtSensor();

		dirtSensor.setRandomDirt(floorPlan);
	}

	public int cleanDirt(Tile tile, DirtDetection dirtDetection) {
		int dirtAmount = tile.getDirtAmount();
		totalDirtCollected = dirtDetection.getTotalDirtCollected();
		dirtCount = tile.getDirtAmount();
		String sysOutput = String.format("Total Dirt Amount of tile %s : %s", tile.getId(), tile.getDirtAmount());
		logger.info(sysOutput);
		for (int i = dirtAmount; i >= 0; i--) {
			if (tile.getDirtAmount() == 0) {
				sysOutput = String.format("Tile %s : is completely clean", tile.getId());
				logger.info(sysOutput);
				break;
			} else {
				sysOutput = String.format("Cleaning tile :%s", tile.getId());
				logger.info(sysOutput);
				dirtCount--;
				totalDirtCollected++;
				isDirtCapacityFull = checkIfDirtCapacityFull(totalDirtCollected);

				if (isDirtCapacityFull) {
					logger.info("-------------------------------------------------");
					logger.info("DIRT TANK FULL !!!Please empty the dirt tank !!");
					logger.info("-------------------------------------------------");
					emptyDirtTank();

				}

				tile.setDirtAmount(dirtCount);
				String stringOutput = String.format("Current Dirt Amount of %s : %s", tile.getId(), dirtCount);
				logger.info(stringOutput);

			}

		}
		return totalDirtCollected;

	}

	public void emptyDirtTank() {
		totalDirtCollected = 0;
		logger.info("Dirt tank emptied!! Clean sweep is ready to vacuum again..");
	}

	public boolean checkIfDirtCapacityFull(int totalDirtCollected) {

		return totalDirtCollected >= totalDirtCapacity;
	}

}