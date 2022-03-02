package com.group9.cleansweep;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class FloorPlanTest {

	@Test
	void convertFileToFloorPlanTest() {
		
		String outputTestDir = (new File("src/test/resources/inputs/")).getAbsolutePath();
		
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.convertFileToFloorplan(outputTestDir + "/" + "convertFileToFloorPlanTest_7_by_7.json");
		
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
	void buildGenericFloorPlanTest() {
		fail("Not yet implemented");
	}

	@Test
	void assignAdjacentTilesTest() {
		fail("Not yet implemented");
	}

	@Test
	void writeFloorPlanToFileTest() throws FileNotFoundException {
		
		FloorPlan floorPlan = new FloorPlan();
        floorPlan.buildGenericFloorPlan();
        int numOfTiles = floorPlan.getFloorPlanMap().size();
        
        String outputTestDir = (new File("src/test/resources/outputs/")).getAbsolutePath();
        File outputFile = floorPlan.writeFloorPlanToFile(outputTestDir, "writeFloorPlanToFileTest");
    	
        //create objects and import file
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader(outputFile));
		//convert json file to tile array
		Tile[] floorTiles = gson.fromJson(br, Tile[].class);
        
		assertEquals(numOfTiles, floorTiles.length);
	}
	
	@AfterAll
	static void cleanOutputTestingFolder() {
		File dir = new File("src/test/resources/outputs/");
	    for (File file:dir.listFiles()) {
	        file.delete();
	    }
	    dir.delete();
	}

}
