package com.group9.cleansweep.controlsystem;

import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.cleansweep.enums.UnitConsumedEnum;
import com.group9.sensor_simulator.FloorTypeSimulator;
public class PowerManagement {

	@Getter

	private static final int TOTAL_BATTERY_UNIT = 250;
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
	private boolean isMimumumPowerCapacityReached=false;


	private static final double POWER_UNIT_MIN_CAPACITY = 50.0;
	  private static final DecimalFormat df = new DecimalFormat("0.00");
	  
	private static Logger logger = LoggerFactory.getLogger(PowerManagement.class);
	
	public boolean powerManagementProcess(Tile previousTile, Tile currentTile, int dirtAmount) {

	String previousSurfaceType = "";
		String currentSurfaceType = "";
		double unitOfCharge;
			currentTile.setSurfaceType(FloorTypeSimulator.getInstance().getRandomFloorType());
			currentSurfaceType = currentTile.getSurfaceType();
			if (previousTile != null && !(currentSurfaceType.equals(previousTile.getSurfaceType()))) {

				previousSurfaceType = previousTile.getSurfaceType();
				unitOfCharge = getAverageUnitOfCharge(currentSurfaceType, previousSurfaceType);
				
			} else 
				unitOfCharge = getUnitOfCharge(currentSurfaceType);

	
			currentUnitOfCharge = currentUnitOfCharge + unitOfCharge+dirtAmount;

			return checkIfMinimumPowerCapacityReached(currentUnitOfCharge);

		}


	public float getUnitOfCharge(String floorPlanType) {

		return UnitConsumedEnum.valueOf(floorPlanType).getUnitsConsumedPerFloorType();
	}

	public float getAverageUnitOfCharge(String currentFloorPlanType, String previousFloorPlanType) {

		float previousUnitOfCharge = UnitConsumedEnum.valueOf(previousFloorPlanType).getUnitsConsumedPerFloorType();

		float currentCharge = UnitConsumedEnum.valueOf(currentFloorPlanType).getUnitsConsumedPerFloorType();

		return (previousUnitOfCharge + currentCharge) / 2;		

	}

	public boolean checkIfMinimumPowerCapacityReached(double currentPowerUnit) {
		
		double remainingBattery=TOTAL_BATTERY_UNIT-currentPowerUnit;
		String batteryPercent= df.format((remainingBattery/TOTAL_BATTERY_UNIT)*100);
		String loggerInfo = String.format("%n Battery Power Remaining: %s %% %n", batteryPercent);
		logger.info(loggerInfo);
		
		if (remainingBattery < POWER_UNIT_MIN_CAPACITY) {
			isMimumumPowerCapacityReached=true;
			StatusCheck statusCheck = new StatusCheck();
			statusCheck.setStatus("\n BATTERY LOW ! PLEASE RECHARGE THE VACUUM!!!!!");

		}
		return isMimumumPowerCapacityReached;
	}

}
