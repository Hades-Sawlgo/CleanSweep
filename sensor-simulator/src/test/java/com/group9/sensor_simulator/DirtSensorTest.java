package com.group9.sensor_simulator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class DirtSensorTest {
	private static Logger logger = LoggerFactory.getLogger(DirtSensorTest.class);
	static String sysOutput = "";
	private static DirtSensor dirtSensor;
	private static FloorPlan floorPlan;
	private static String testName;

	@BeforeAll
	public static void initDirtSensor() {

		final String className = "DirtSensorTest";
		dirtSensor = new DirtSensor();
		floorPlan = new FloorPlan();
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
	void t1checkRandomDirtAssignedIsNotNull() {

		testName = "t1checkRandomDirtAssignedIsNotNull";
		printTestName(testName);
		floorPlan.buildGenericFloorPlan();

		dirtSensor.setRandomDirt(floorPlan);
		for (Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {
			assertTrue((entry.getValue().getDirtAmount() >= 0));

		}

	}
}
