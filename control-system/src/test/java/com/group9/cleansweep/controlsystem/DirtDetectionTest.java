package com.group9.cleansweep.controlsystem;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static org.junit.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class DirtDetectionTest {

	private static DirtDetection dirtDetection;
	private static FloorPlan floorPlan;
	private static String testName;

	@BeforeClass
	public static void initDirtDetection() {
		final String className = "DirtDetectionTest";
		dirtDetection = new DirtDetection();
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
	
		dirtDetection.setRandomDirt(floorPlan);
		for (Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {
			// System.out.println(entry.getValue().getDirtAmount());
			assertTrue((entry.getValue().getDirtAmount() >= 0));

		}

	}

	@Test
	public void t2checkDirtAssignedMaxValue() {

		testName = "t2checkDirtAssignedMaxValue";
		printTestName(testName);

		Map<String, Tile> floorPlanMap = floorPlan.getFloorPlanMap();
		for (Map.Entry<String, Tile> entry : floorPlanMap.entrySet()) {

			assertTrue((entry.getValue().getDirtAmount() < 4));

		}
	}


	@Test
	public void t3checkIfDirtTankIsFull() {

		testName = "t3checkIfDirtTankIsFull";
		printTestName(testName);

		int currentDirtCollected = 100;
		boolean isDirtCapacityFull = dirtDetection.checkIfDirtCapacityFull(currentDirtCollected);
		if (isDirtCapacityFull)
			dirtDetection.emptyDirtTank();
	}
}
