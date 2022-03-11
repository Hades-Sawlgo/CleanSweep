package com.group9.cleansweep.controlsystem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class DirtDetectionTest {

	private static DirtDetection dirtDetection;
	private static FloorPlan floorPlan;
	private static String testName;
	private static Properties properties = new Properties();
	private static final String CONSTANT_FILE_PATH = "../clean-sweep/src/main/java/com/group9/cleansweep/properties/constant.properties";

	@BeforeAll
	public static void initDirtDetection() {
		final String className = "DirtDetectionTest";

		try (InputStream input = new FileInputStream(CONSTANT_FILE_PATH)) {
			properties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
	void t1checkRandomDirtAssignedIsNotNull() {

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
	void t2checkDirtAssignedMaxValue() {

		testName = "t2checkDirtAssignedMaxValue";
		printTestName(testName);

		Map<String, Tile> floorPlanMap = floorPlan.getFloorPlanMap();
		for (Map.Entry<String, Tile> entry : floorPlanMap.entrySet()) {

			assertTrue((entry.getValue().getDirtAmount() < 4));

		}
	}

	@Test
	void t3checkDirtIsCleaned() {

		testName = "t3checkDirtIsCleaned";
		printTestName(testName);

		Map<String, Tile> floorPlanMap = floorPlan.getFloorPlanMap();

		Tile firstTile = floorPlanMap.get(floorPlanMap.keySet().toArray()[0]);
		dirtDetection.setDirtCapacityFull(false);
		firstTile.setDirtAmount(0);
		dirtDetection.cleanDirt(firstTile, dirtDetection);
		assertEquals(firstTile.getDirtAmount(), Integer.parseInt(properties.getProperty("IDEAL_DIRT_AMOUNT_PER_TILE")));
		firstTile.setDirtAmount(3);
		dirtDetection.setDirtCapacityFull(true);
		dirtDetection.cleanDirt(firstTile, dirtDetection);
		assertEquals(firstTile.getDirtAmount(), Integer.parseInt(properties.getProperty("IDEAL_DIRT_AMOUNT_PER_TILE")));
	}

	@Test
	void t4checkIfDirtTankIsFull() {

		testName = "t3checkIfDirtTankIsFull";
		printTestName(testName);

		int currentDirtCollected = 60;
		assertTrue(dirtDetection.checkIfDirtCapacityFull(currentDirtCollected));
		currentDirtCollected = 40;
		assertFalse(dirtDetection.checkIfDirtCapacityFull(currentDirtCollected));
	}

	@Test
	void t5CheckDirtTankEmptied() {
		testName = "t4checkDirtTankEmptied";
		printTestName(testName);
		dirtDetection.emptyDirtTank();
		assertEquals(dirtDetection.getTotalDirtCollected(), dirtDetection.getTotalDirtCollected());
	}
}
