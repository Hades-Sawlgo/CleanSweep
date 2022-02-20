package com.group9.cleansweep.controlsystem;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PowerManagementTest {
	private static PowerManagement powerManagement;

	private static String testName;

	@BeforeClass
	public static void initPowerManagement() {
		final String className = "PowerManagementTest";
		powerManagement = new PowerManagement();

		System.out.println("************************************************************");
		System.out.println("     " + className + " class getting executed");
		System.out.println("************************************************************");
	}

	public void printTestName(String testName) {

		System.out.println(testName + " test method getting executed.....\n ");
	}

	@Test
	public void t1checkUnitOfPowerManagement() {

		testName = "t1checkUnitOfPowerManagement";
		printTestName(testName);

		String currentfloorPlanType1 = "BARE_FOOT";
		String currentfloorPlanType2 = "LOW_PILE_CARPET";
		String currentfloorPlanType3 = "HIGH_PILE_CARPET";
		String previousfloorPlanType = "BARE_FOOT";
		assertTrue(powerManagement.getUnitOfCharge(currentfloorPlanType1) == 1.0);
		assertTrue(powerManagement.getUnitOfCharge(currentfloorPlanType2) == 2.0);
		assertTrue(powerManagement.getUnitOfCharge(currentfloorPlanType3) == 3.0);
		assertTrue(powerManagement.getUnitOfCharge(previousfloorPlanType) == 1.0);

	}

	@Test
	public void t2AverageUnitOfPower() {
		testName = "t2AverageUnitOfPower";
		printTestName(testName);

		String currentfloorPlanType = "LOW_PILE_CARPET";
		String previousfloorPlanType = "BARE_FOOT";
		assertTrue(powerManagement.getAverageUnitOfCharge(currentfloorPlanType, previousfloorPlanType) == 1.5);

	}

	@Test
	public void t3checkPowerLow() {
		testName = "t3checkPowerLow";
		printTestName(testName);

		double cuurentUnitOfPower = 10.0;

		powerManagement.checkIfMinimumPowerCapacityReached(cuurentUnitOfPower);
	}

}
