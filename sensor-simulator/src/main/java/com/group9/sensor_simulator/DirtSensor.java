package com.group9.sensor_simulator;

import java.util.Map;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.sensor_simulator.enums.DirtAmountEnum;

public class DirtSensor {
	public Map<String, Tile> setRandomDirt(FloorPlan floorPlan) {
		
		Map<String, Tile> floorPlanMap = floorPlan.getFloorPlanMap();
		
		for (Map.Entry<String, Tile> entry : floorPlanMap.entrySet()) {
			entry.getValue().setDirtAmount(DirtAmountEnum.getRandomEnum().getDirtAmount());
		}
		return floorPlanMap;
	}
}
