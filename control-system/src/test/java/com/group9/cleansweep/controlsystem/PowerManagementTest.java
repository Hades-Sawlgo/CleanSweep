package com.group9.cleansweep.controlsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class PowerManagementTest {
	private static Logger logger = LoggerFactory.getLogger(PowerManagementTest.class);
	private static PowerManagement powerManagement;

	private static String testName;
	static String sysOutput = "";

	@BeforeClass
	public static void initPowerManagement() {
		final String className = "PowerManagementTest";
		powerManagement = new PowerManagement();
		sysOutput = String.format("%s class getting executed", className);
		logger.info("-------------------------------------------------");
		logger.info(sysOutput);
		logger.info("-------------------------------------------------");
	}

	public void printTestName(String testName) {

		sysOutput = String.format("%s test method getting executed.....\n", testName);
		logger.info(sysOutput);
	}

	@Test
	public void t1checkUnitOfPowerManagement() {

		testName = "t1checkUnitOfPowerManagement";
		printTestName(testName);

		String currentfloorPlanType1 = "BARE_FOOT";
		String currentfloorPlanType2 = "LOW_PILE_CARPET";
		String currentfloorPlanType3 = "HIGH_PILE_CARPET";
		String previousfloorPlanType = "BARE_FOOT";
		assertEquals(1.0f, powerManagement.getUnitOfCharge(currentfloorPlanType1));
		assertEquals(2.0f, powerManagement.getUnitOfCharge(currentfloorPlanType2));
		assertEquals(3.0f, powerManagement.getUnitOfCharge(currentfloorPlanType3));
		assertEquals(1.0f, powerManagement.getUnitOfCharge(previousfloorPlanType));

	}

	@Test
	public void t2AverageUnitOfPower() {
		testName = "t2AverageUnitOfPower";
		printTestName(testName);

		String currentfloorPlanType = "LOW_PILE_CARPET";
		String previousfloorPlanType = "BARE_FOOT";
		assertEquals(1.5f, powerManagement.getAverageUnitOfCharge(currentfloorPlanType, previousfloorPlanType));

	}

	@Test
	public void t3checkPowerLow() {
		testName = "t3checkPowerLow";
		printTestName(testName);

		double cuurentUnitOfPower = 10.0;

		powerManagement.checkIfMinimumPowerCapacityReached(cuurentUnitOfPower);
	}

}
