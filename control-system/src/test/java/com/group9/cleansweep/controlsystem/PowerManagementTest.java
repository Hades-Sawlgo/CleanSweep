package com.group9.cleansweep.controlsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group9.cleansweep.enums.SurfaceTypeEnum;
import com.group9.cleansweep.enums.UnitConsumedEnum;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class PowerManagementTest {
	private static Logger logger = LoggerFactory.getLogger(PowerManagementTest.class);
	private static PowerManagement powerManagement;
	private static Properties prop;

	private static String testName;
	static String sysOutput = "";

	@BeforeClass
	public static void initPowerManagement() {
		final String className = "PowerManagementTest";
		final String CONSTANT_FILE_PATH = "../clean-sweep/src/main/java/com/group9/cleansweep/properties/constant.properties";

		powerManagement = new PowerManagement();
		prop = new Properties();

		try (InputStream input = new FileInputStream(CONSTANT_FILE_PATH)) {
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
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

		assertEquals(Double.parseDouble(prop.getProperty("BARE_FOOT_UNIT_OF_CHARGE")),
				powerManagement.getUnitOfCharge(SurfaceTypeEnum.BARE_FOOT));
		assertEquals(Double.parseDouble(prop.getProperty("LOW_PILE_CARPET_UNIT_OF_CHARGE")),
				powerManagement.getUnitOfCharge(SurfaceTypeEnum.LOW_PILE_CARPET));
		assertEquals(Double.parseDouble(prop.getProperty("HIGH_PILE_CARPET_UNIT_OF_CHARGE")),
				powerManagement.getUnitOfCharge(SurfaceTypeEnum.HIGH_PILE_CARPET));

	}

	@Test
	public void t2AverageUnitOfPower() {
		testName = "t2AverageUnitOfPower";
		printTestName(testName);
		SurfaceTypeEnum firstVal = SurfaceTypeEnum.LOW_PILE_CARPET;
		SurfaceTypeEnum secondVal = SurfaceTypeEnum.BARE_FOOT;
		
		double expectedVal = 
				(UnitConsumedEnum.valueOf(firstVal.toString()).getUnitsConsumedPerSurfaceType() 
						+ UnitConsumedEnum.valueOf(secondVal.toString()).getUnitsConsumedPerSurfaceType()) 
				/2;

		assertEquals(expectedVal, powerManagement.getAverageUnitOfCharge(firstVal, secondVal));
	}

	@Test
	public void t3checkPowerLow() {
		testName = "t3checkPowerLow";
		printTestName(testName);

		powerManagement.checkIfMinimumPowerCapacityReached(
				Double.parseDouble(prop.getProperty("MINIMUM_POWER_CAPACITY")), prop);
	}

}
