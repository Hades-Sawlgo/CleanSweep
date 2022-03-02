package com.group9.cleansweep.controlsystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.cleansweep.enums.UnitConsumedEnum;

import lombok.Getter;
import lombok.Setter;

public class PowerManagement {

	@Getter
	@Setter
	private double currentUnitOfCharge = 0;

	@Getter
	@Setter
	private UnitConsumedEnum unitConsumedEnum;

	@Getter
	@Setter
	private FloorPlan floorPlanType;

	@Getter
	@Setter
	private boolean isMimumumPowerCapacityReached = false;
	private static Properties properties = new Properties();
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final String CONSTANT_FILE_PATH = "../clean-sweep/src/main/java/com/group9/cleansweep/properties/constant.properties";
	private static Logger logger = LoggerFactory.getLogger(PowerManagement.class);

	public boolean powerManagementProcess(Tile previousTile, Tile currentTile, int dirtAmount) throws IOException {
		try (InputStream input = new FileInputStream(CONSTANT_FILE_PATH)) {
			properties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		String previousSurfaceType = "";
		String currentSurfaceType = "";
		double unitOfCharge;
		currentSurfaceType = currentTile.getSurfaceType();
		if (previousTile != null && !(currentSurfaceType.equals(previousTile.getSurfaceType()))) {

			previousSurfaceType = previousTile.getSurfaceType();
			unitOfCharge = getAverageUnitOfCharge(currentSurfaceType, previousSurfaceType, properties);

		} else
			unitOfCharge = getUnitOfCharge(currentSurfaceType);

		currentUnitOfCharge = currentUnitOfCharge + unitOfCharge + dirtAmount;

		return checkIfMinimumPowerCapacityReached(currentUnitOfCharge, properties);

	}

	public float getUnitOfCharge(String floorPlanType) {

		return UnitConsumedEnum.valueOf(floorPlanType).getUnitsConsumedPerFloorType();
	}

	public double getAverageUnitOfCharge(String currentFloorPlanType, String previousFloorPlanType,
			Properties properties) {

		float previousUnitOfCharge = UnitConsumedEnum.valueOf(previousFloorPlanType).getUnitsConsumedPerFloorType();

		float currentCharge = UnitConsumedEnum.valueOf(currentFloorPlanType).getUnitsConsumedPerFloorType();

		return (previousUnitOfCharge + currentCharge) / Double.parseDouble(properties.getProperty("AVERAGE_BY_TWO"));

	}

	public boolean checkIfMinimumPowerCapacityReached(double currentPowerUnit, Properties properties) {

		double remainingBattery = Double.parseDouble(properties.getProperty("TOTAL_BATTERY_UNIT")) - currentPowerUnit;
		String batteryPercent = df
				.format((remainingBattery / Double.parseDouble(properties.getProperty("TOTAL_BATTERY_UNIT")))
						* Double.parseDouble(properties.getProperty("PERCENTAGE_VALUE")));
		String loggerInfo = String.format("%n Battery Power Remaining: %s %% %n", batteryPercent);
		logger.info(loggerInfo);

		if (remainingBattery < Double.parseDouble(properties.getProperty("POWER_UNIT_MIN_CAPACITY"))) {
			isMimumumPowerCapacityReached = true;

			loggerInfo = String.format("%n BATTERY LOW ! PLEASE RECHARGE THE VACUUM!!!!!");
			logger.info(loggerInfo);

		}
		return isMimumumPowerCapacityReached;
	}

}
