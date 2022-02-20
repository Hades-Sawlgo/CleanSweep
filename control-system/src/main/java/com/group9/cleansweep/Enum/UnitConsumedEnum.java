package com.group9.cleansweep.Enum;

public enum UnitConsumedEnum {
	BARE_FOOT(1), LOW_PILE_CARPET(2), HIGH_PILE_CARPET(3);

	private int units;

	UnitConsumedEnum(int units) {
		this.units = units;
	}

	public int getUnitsConsumedPerFloorType() {
		return units;
	}
}
