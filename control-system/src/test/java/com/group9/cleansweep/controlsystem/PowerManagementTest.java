package com.group9.cleansweep.controlsystem;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
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
		assertSame(1, powerManagement.getUnitOfCharge(currentfloorPlanType1));
		assertSame(2, powerManagement.getUnitOfCharge(currentfloorPlanType2));
		assertSame(3, powerManagement.getUnitOfCharge(currentfloorPlanType3));
		assertSame(1, powerManagement.getUnitOfCharge(previousfloorPlanType));

	}

	@Test
	public void t2AverageUnitOfPower() {
		testName = "t2AverageUnitOfPower";
		printTestName(testName);

		String currentfloorPlanType = "LOW_PILE_CARPET";
		String previousfloorPlanType = "BARE_FOOT";
		assertSame(1.5, powerManagement.getAverageUnitOfCharge(currentfloorPlanType, previousfloorPlanType));

	}

	@Test
	public void t3checkPowerLow() {
		testName = "t3checkPowerLow";
		printTestName(testName);

		double cuurentUnitOfPower = 10.0;

		powerManagement.checkIfMinimumPowerCapacityReached(cuurentUnitOfPower);
	}

}
