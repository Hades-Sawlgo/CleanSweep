package com.group9.sensor_simulator;

import java.security.SecureRandom;
import java.util.Map;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.sensor_simulator.Enum.DirtAmountEnum;

public class DirtSensor {
	public Map<String, Tile> setRandomDirt(FloorPlan floorPlan) {
		
		SecureRandom random = new SecureRandom();
		DirtAmountEnum randomDirtCapacityEnum;
		DirtAmountEnum[] dirtCapacityEnum = DirtAmountEnum.values();
		Map<String, Tile> floorPlanMap = floorPlan.getFloorPlanMap();
		
		for (Map.Entry<String, Tile> entry : floorPlanMap.entrySet()) {
			randomDirtCapacityEnum = dirtCapacityEnum[random.nextInt(dirtCapacityEnum.length)];
			entry.getValue().setDirtAmount(randomDirtCapacityEnum.getDirtPerFloorType());

		}
		return floorPlanMap;
	}
}
