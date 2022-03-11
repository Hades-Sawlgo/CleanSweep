package com.group9.cleansweep.controlsystem;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.google.gson.Gson;
import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.cleansweep.enums.TileTypeEnum;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class NavigationTest {

	private static Navigation navigation;
	private static FloorPlan floorPlan;
	private static Tile tile;
	private static String testName;
	private static Map<String, Tile> floorPlanMap;
	private static Entry<String, Tile> floorPlanFirstTile;

	@BeforeAll
	public static void initNavigationTest() {
		final String className = "NavigationTest";

		floorPlan = new FloorPlan();
		floorPlan.buildGenericFloorPlan();
		floorPlanMap = floorPlan.getFloorPlanMap();
		navigation = new Navigation(floorPlan);
		floorPlanFirstTile = floorPlanMap.entrySet().iterator().next();
		tile = floorPlanFirstTile.getValue();

		System.out.println("************************************************************");
		System.out.println("     " + className + " class getting executed");
		System.out.println("************************************************************");
	}

	public void printTestName(String testName) {

		System.out.println(testName + " test method getting executed.....\n ");

	}

	public Tile temporartTileObject() {
		Gson gson = new Gson();

		JSONObject jsonObject = new JSONObject("{" + "id: a2," + "surfaceType: BARE_FOOT," + "isObstable: true,"
				+ "dirtAmount: 0," + "isChargingStation: false," + "visited: false," + "rightID: a3," + "leftID: a1,"
				+ "topID: b1," + "bottomID: b2" + "}");
		return gson.fromJson(jsonObject.toString(), Tile.class);
	}

	@Test
	void t1checkIsObstacleRight() {

		testName = "t1checkIsObstacleRight";
		printTestName(testName);

		if (tile.getRightNext() == null)
			tile.setRightNext(temporartTileObject());
		tile.getRightNext().setTileType(TileTypeEnum.OBSTACLE);

		assertEquals(true, navigation.isObstacleRight(tile));

	}

	@Test
	void t2checkIsObstacleLeft() throws FileNotFoundException, IOException, ParseException {

		testName = "t2checkIsObstacleLeft";
		printTestName(testName);

		if (tile.getLeftNext() == null)
			tile.setLeftNext(temporartTileObject());
		tile.getLeftNext().setTileType(TileTypeEnum.OBSTACLE);

		assertEquals(true, navigation.isObstacleLeft(tile));
	}

	@Test
	void t3checkIsObstacleTop() {

		testName = "t3checkIsObstacleTop";
		printTestName(testName);

		if (tile.getTopNext() == null)
			tile.setTopNext(temporartTileObject());
		tile.getTopNext().setTileType(TileTypeEnum.OBSTACLE);

		assertEquals(true, navigation.isObstacleTop(tile));

	}

	@Test
	void t4checkIsObstacleBottom() {

		testName = "t4checkIsObstacleBottom";
		printTestName(testName);

		if (tile.getBottomNext() == null)
			tile.setBottomNext(temporartTileObject());
		tile.getBottomNext().setTileType(TileTypeEnum.OBSTACLE);

		assertEquals(true, navigation.isObstacleBottom(tile));

	}
}
