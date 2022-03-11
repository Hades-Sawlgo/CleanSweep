package com.group9.sensor_simulator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ObstacleSimulatorTest {
	private static String testName;

	@BeforeAll
	public static void initObstacleSimulator() {
		final String className = "ObstacleSimulatorTest";
		System.out.println("************************************************************");
		System.out.println("     " + className + " class getting executed");
		System.out.println("************************************************************");
	}

	public void printTestName(String testName) {

		System.out.println(testName + " test method getting executed.....\n ");

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
