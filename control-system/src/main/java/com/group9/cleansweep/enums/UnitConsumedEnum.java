package com.group9.cleansweep.enums;

public enum UnitConsumedEnum {
	// TODO: fix this, its is too similar to UnitConsumedEnum and the constant.properties file
	BARE_FOOT(1), LOW_PILE_CARPET(2), HIGH_PILE_CARPET(3);

	private int units;

	UnitConsumedEnum(int units) {
		this.units = units;
	}

	public int getUnitsConsumedPerSurfaceType() {
		return units;
	}
}
