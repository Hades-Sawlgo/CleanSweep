package com.group9.sensor_simulator.enums;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum DirtAmountEnum {
	NO_DIRT(0), LOW(1), MEDIUM(2), HIGH(3);

	private int units;

	DirtAmountEnum(int units) {
		this.units = units;
	}

	public int getDirtAmount() {
		return units;
	}
	
	private static final SecureRandom RANDOM = new SecureRandom();
	
	private static final List<DirtAmountEnum> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	
	public static DirtAmountEnum getRandomEnum() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}