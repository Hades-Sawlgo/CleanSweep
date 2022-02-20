package com.group9.sensor_simulator.Enum;

public enum DirtAmountEnum {
	NO_DIRT(0), LOW(1), MEDIUM(2), HIGH(3);

	private int units;

	DirtAmountEnum(int units) {
		this.units = units;
	}

	public int getDirtPerFloorType() {
		return units;
	}
}