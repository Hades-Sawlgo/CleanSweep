package com.group9.sensor_simulator;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class DirtSensorTest {

	private static DirtSensor dirtSensor;
	private static FloorPlan floorPlan;
	private static String testName;

	@BeforeClass
	public static void initDirtSensor() {
		final String className = "DirtSensorTest";
		dirtSensor = new DirtSensor();
		floorPlan = new FloorPlan();
		System.out.println("************************************************************");
		System.out.println("     " + className + " class getting executed");
		System.out.println("************************************************************");
	}

	public void printTestName(String testName) {

		System.out.println(testName + " test method getting executed.....\n ");

	}

	@Test
	public void t1checkRandomDirtAssignedIsNotNull() {

		testName = "t1checkRandomDirtAssignedIsNotNull";
		printTestName(testName);
		floorPlan.buildGenericFloorPlan();
	
		dirtSensor.setRandomDirt(floorPlan);
		for (Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {
			assertTrue((entry.getValue().getDirtAmount() >= 0));

		}

	}
}
