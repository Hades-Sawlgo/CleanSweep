package com.group9.cleansweep.enums;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SurfaceTypeEnum {
	// TODO: fix this, its is too similar to UnitConsumedEnum and the constant.properties file
	BARE_FOOT, LOW_PILE_CARPET, HIGH_PILE_CARPET;
	
	private static final SecureRandom RANDOM = new SecureRandom();
	
	private static final List<SurfaceTypeEnum> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	
	public static SurfaceTypeEnum getRandomEnum() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	
}
