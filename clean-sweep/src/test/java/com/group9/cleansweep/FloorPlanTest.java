package com.group9.cleansweep;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.group9.cleansweep.enums.TileTypeEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FloorPlanTest {

	static ArrayList<File> filesToDelete;
	static Path outputTestDir = Paths.get("src", "test", "resources", "outputs");
    String fileName = "writeFloorPlanToFileTest";
	
	@BeforeAll
	 static void setup() throws IOException {
		filesToDelete = new ArrayList<File>();
		Files.createDirectory(outputTestDir);
	}
	
	@Test
	void convertFileToFloorPlanTest() {
		
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.convertFileToFloorplan(new File("src/test/resources/inputs", "convertFileToFloorPlanTest_7_by_7.json"));
		
		int[] floorPlanDim = new int[] {7, 7};
		int numNullTileExpected = (2*floorPlanDim[0]) + (2*floorPlanDim[1]);
		int numNullTileCounted = 0;
		
		for(Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {
			if(entry.getValue().getTopNext() == null) {
				numNullTileCounted++;
			}
			if(entry.getValue().getBottomNext() == null) {
				numNullTileCounted++;
			}
			if(entry.getValue().getLeftNext() == null) {
				numNullTileCounted++;
			}
			if(entry.getValue().getRightNext() == null) {
				numNullTileCounted++;
			}
		}
		assertEquals(numNullTileExpected, numNullTileCounted);
	}

	@Test
	void buildGenericFloorPlan_checkThatOnePowerStationWasAdded_Test() {
		int numberOfPowerStations = 0;
		
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.buildGenericFloorPlan();
		
		for(Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {
			if(entry.getValue().getTileType() == TileTypeEnum.POWERSTATION) {
				numberOfPowerStations++;
			}
		}
		assertEquals(1, numberOfPowerStations);
	}
	
	@Test
	void buildGenericFloorPlan_allTilesWereAssignedAdjacentIds_Test() {
		int numberOfTilesWithAllNullAdjacentTiles = 0;
				
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.buildGenericFloorPlan();
		
		Tile tempTile;
		
		for(Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {
			tempTile = entry.getValue();
			if(
					tempTile.getBottomNext() == null
					&& tempTile.getTopNext() == null
					&& tempTile.getLeftNext() == null
					&& tempTile.getRightNext() == null) {
				numberOfTilesWithAllNullAdjacentTiles++;
			}
		}
		assertEquals(0, numberOfTilesWithAllNullAdjacentTiles);
	}
	
	@Test
	void buildGenericFloorPlan_correctNumberOfTilesWereAddedToFloorPlan_Test() {
		int numberOfTilesWithoutASurfaceType = 0;
		
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.buildGenericFloorPlan();
		
		for(Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {
			if(entry.getValue().getSurfaceType() == null){
						numberOfTilesWithoutASurfaceType++;
			}
		}
		assertEquals(0, numberOfTilesWithoutASurfaceType);
	}
	
	@Test
	void buildGenericFloorPlan_allTilesHaveASurfaceType_Test() {	
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.buildGenericFloorPlan();
		
		int expectedTileCount = 
				((floorPlan.getAxisYMax()-floorPlan.getAxisYMin())+1)
				*floorPlan.getAxisX().length;
		
		int acutalTileCount = floorPlan.getFloorPlanMap().entrySet().size();
		
		assertEquals(expectedTileCount, acutalTileCount);
	}

	@Test
	void assignAdjacentTiles_noNulls_Test() {
		String[] axisX = {"b", "c", "d"};
		int axisYMin = 4;
		int axisYMax = axisYMin+2;
		
		FloorPlan floorPlan = createSimpleFloorPlan_withoutAssignments(axisX, axisYMin, axisYMax);
		
		int middleTileXidx = 1;
		int middleTileYval = axisYMin+1;
		
		Map<String, Tile> roomLayout = floorPlan.getFloorPlanMap();
		Tile middleTile = roomLayout.get(axisX[middleTileXidx] + middleTileYval);
		
		// the middle element is the second index in the x and y
		floorPlan.assignAdjacentTiles(middleTile, middleTileXidx, middleTileYval);
		
		String topTileId = axisX[middleTileXidx] + (middleTileYval+1);
		assertSame(
				roomLayout.get(topTileId),
				middleTile.getTopNext());
		
		String bottomTileId = axisX[middleTileXidx] + (middleTileYval-1);
		assertSame(
				roomLayout.get(bottomTileId),
				middleTile.getBottomNext());
		
		String leftTileId = axisX[middleTileXidx-1] + middleTileYval;
		assertSame(
				roomLayout.get(leftTileId),
				middleTile.getLeftNext());
		
		String rightTileId = axisX[middleTileXidx+1] + middleTileYval;
		assertSame(
				roomLayout.get(rightTileId),
				middleTile.getRightNext());
	}
	
	@Test
	void assignAdjacentTiles_withNulls_Test() {
		String[] axisX = {"b", "c", "d"};
		int axisYMin = 4;
		int axisYMax = axisYMin+2;
		
		FloorPlan floorPlan = createSimpleFloorPlan_withoutAssignments(axisX, axisYMin, axisYMax);
		
		Map<String, Tile> roomLayout = floorPlan.getFloorPlanMap();
		
		int tgtTileXidx = 0;
		int tgtTileYval = axisYMin;
		
		Tile tgtTile = roomLayout.get(axisX[tgtTileXidx] + axisYMin);
		
		// the middle element is the second index in the x and y
		floorPlan.assignAdjacentTiles(tgtTile, tgtTileXidx, tgtTileYval);
		
		String topTileId = axisX[tgtTileXidx] + (tgtTileYval+1);
		assertSame(
				roomLayout.get(topTileId),
				tgtTile.getTopNext());
		
		assertNull(tgtTile.getBottomNext());
		
		assertNull(tgtTile.getLeftNext());
		
		String rightTileId = axisX[tgtTileXidx+1] + tgtTileYval;
		assertSame(
				roomLayout.get(rightTileId),
				tgtTile.getRightNext());
	}

	@Test
	void writeFloorPlanToFile_withInputs_Test() throws FileNotFoundException {
		
		// write FloorPlan to file
		FloorPlan floorPlan = new FloorPlan();
        floorPlan.buildGenericFloorPlan();
        int numOfTiles = floorPlan.getFloorPlanMap().size();
        File outputFile = floorPlan.writeFloorPlanToFile(outputTestDir, fileName);
    	
        // Read floorPlan from file
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader(outputFile.getAbsoluteFile()));
		//convert json file to tile array
		Tile[] floorTiles = gson.fromJson(br, Tile[].class);
        
		assertEquals(numOfTiles, floorTiles.length);
	}

	@Test
	void writeFloorPlanToFile_noInput_Test() throws FileNotFoundException {
		
		// write FloorPlan to file
		FloorPlan floorPlan = new FloorPlan();
        floorPlan.buildGenericFloorPlan();
        int numOfTiles = floorPlan.getFloorPlanMap().size();
        File outputFile = floorPlan.writeFloorPlanToFile();
        filesToDelete.add(outputFile);
    	
        // Same test from writeFloorPlanToFileTest()
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader(outputFile.getAbsoluteFile()));
		Tile[] floorTiles = gson.fromJson(br, Tile[].class);
		assertEquals(numOfTiles, floorTiles.length);
		
		// Test the file location is correct
		String outputTestDir = outputFile.getAbsolutePath();

		assertTrue(outputTestDir.contains((new File(floorPlan.getDefaultDirectory().toString(),floorPlan.getDefaultFilename())).toString()));
	}
	
	@Test
	void constructor_withArgs_Test() {
		Map<String, Tile> roomLayout = new HashMap<>();
		String[] axisX = new String []{"a", "b", "c", "d"};
		int axisYMin = 4;
		int axisYMax = 7;
		FloorPlan floorPlan = new FloorPlan(roomLayout, axisX, axisYMin, axisYMax);
		
		assertSame(roomLayout, floorPlan.getFloorPlanMap());
		assertEquals(axisX, floorPlan.getAxisX());
		assertEquals(axisYMin, floorPlan.getAxisYMin());
		assertEquals(axisYMax, floorPlan.getAxisYMax());
		
	}
	
	public static FloorPlan createSimpleFloorPlan_withoutAssignments(String[] axisX, int axisYMin, int axisYMax) {
		Map<String, Tile> roomLayout = new HashMap<>();
		
		Tile tile;
		
		for (int y=axisYMin; y<=axisYMax; y++) {
			for(int x=0; x<axisX.length; x++) {
				tile = new Tile();
				tile.setId(axisX[x] + y);
				roomLayout.put(tile.getId(), tile);
			}
		}
		
		return new FloorPlan(roomLayout, axisX, axisYMin, axisYMax);
	}
	
	@AfterAll
	static void cleanOutputTestingFolder() throws IOException {
		File dir = new File(outputTestDir.toString());
	    for (File file : dir.getAbsoluteFile().listFiles()) {
	    	System.out.println(file.getAbsolutePath().toString());
	    	if(file.getAbsolutePath().contains(".json")) {
	    		file.getAbsoluteFile().delete();
	    	}
	    }
	    for (File file : filesToDelete) {
	    	System.out.println(file.getAbsolutePath().toString());
	    	file.getAbsoluteFile().delete();
	    }
	}

}
