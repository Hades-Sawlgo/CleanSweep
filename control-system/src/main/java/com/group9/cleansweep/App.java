package com.group9.cleansweep;

import com.group9.cleansweep.controlsystem.CleanSweep;
import com.group9.cleansweep.controlsystem.UnregisteredUI;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		CleanSweep cleanSweep = new CleanSweep();
	UnregisteredUI.UnregisteredUI(cleanSweep);
		//cleanSweep.doWork();

		//cleanSweep.doWorkFromFile("src/main/java/com/group9/cleansweep/controlsystem/FloorPlanFile/SampleFloor.json");

	
//		PowerManagement powerManagementService = new PowerManagement();
//		String currentfloorPlanType = "LOW_PILE_CARPET";
//		String previousfloorPlanType = "BARE_FOOT";
//		powerManagementService.getUnitOfCharge(currentfloorPlanType);
//		powerManagementService.getAverageUnitOfCharge(currentfloorPlanType, previousfloorPlanType);
//
//		powerManagementService.checkUnitOfPower(100.0);

	}
}
