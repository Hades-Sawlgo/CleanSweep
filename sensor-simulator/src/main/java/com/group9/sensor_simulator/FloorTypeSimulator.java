package com.group9.sensor_simulator;

import java.util.Random;

public class FloorTypeSimulator {
	private static FloorTypeSimulator instance = null;
	private final String[] floorTypes = {"BARE_FOOT", "LOW_PILE_CARPET", "HIGH_PILE_CARPET"};
	private final Random random;

	private FloorTypeSimulator(){
		random = new Random();
	}

	public static FloorTypeSimulator getInstance(){
		if(instance == null){
			instance = new FloorTypeSimulator();
		}
		return instance;
	}

	public String getRandomFloorType(){
		return floorTypes[random.nextInt(floorTypes.length)];
	}


}
