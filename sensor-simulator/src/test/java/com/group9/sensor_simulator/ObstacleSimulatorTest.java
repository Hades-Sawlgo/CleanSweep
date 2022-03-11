package com.group9.sensor_simulator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ObstacleSimulatorTest {
	private static Logger logger = LoggerFactory.getLogger(ObstacleSimulatorTest.class);
	private static String testName;
	static String sysOutput = "";

	@BeforeAll
	public static void initObstacleSimulator() {

		final String className = "ObstacleSimulatorTest";
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
	void checkRandomObstacleGetInstanceIsNotNull() {
		testName = "checkRandomObstacleGetInstanceIsNotNull";
		printTestName(testName);
		ObstacleSimulator testSimulator = ObstacleSimulator.getInstance();
		assertTrue((testSimulator != null));
	}
	
	@Test
	void checkRandomObstacleAssignedIsNotNull() {
		testName = "checkRandomObstacleAssignedIsNotNull";
		printTestName(testName);
		ObstacleSimulator testSimulator = ObstacleSimulator.getInstance();
		Boolean testRandomObstacle = testSimulator.getRandomObstacle();
		assertTrue((testRandomObstacle) || (!testRandomObstacle));
	}
}
