package com.group9.cleansweep.enums;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SurfaceTypeEnum {
	BARE_FOOT(1), LOW_PILE_CARPET(2), HIGH_PILE_CARPET(3);
	
	// units of power consumed
	private int units;

	SurfaceTypeEnum(int units) {
		this.units = units;
	}

	public int getUnitsConsumed() {
		return units;
	}
	
	private static final SecureRandom RANDOM = new SecureRandom();
	
	private static final List<SurfaceTypeEnum> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	
	public static SurfaceTypeEnum getRandomEnum() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	
}
