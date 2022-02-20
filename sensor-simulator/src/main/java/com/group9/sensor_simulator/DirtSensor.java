package com.group9.sensor_simulator;

import java.util.Map;
import java.util.Random;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.sensor_simulator.Enum.DirtAmountEnum;

public class DirtSensor {
	public Map<String, Tile> setRandomDirt(FloorPlan floorPlan) {
		
		Random random = new Random();
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
