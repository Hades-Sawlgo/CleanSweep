package com.group9.cleansweep;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.group9.cleansweep.enums.FloorPlanTypeEnum;

public class FloorPlan {
	
	private static Logger logger = LoggerFactory.getLogger(FloorPlan.class);
	
	//this keeps track of all the tiles in a room String is the ID of the tile
	private final Map<String, Tile> roomLayout;
	private final boolean[] isObstacle = {true, false};
	private final String[] floorTypes = {"BARE_FOOT", "LOW_PILE_CARPET", "HIGH_PILE_CARPET"};
	
	//Dimension information on floor
	private String[] axisX = {"a", "b", "c", "d", "e", "f", "g"};
	private int axisYMin = 1;
	private int axisYMax = 7;

	@Getter
	FloorPlanTypeEnum floorPlanType;

	public FloorPlan(){
		this.roomLayout = new HashMap<>();
	}
	
	public int[] getDimInfo() {
		return new int[] {axisX.length, (axisYMax-axisYMin)+1};
	}

	public Map<String, Tile> getFloorPlanMap(){
		return roomLayout;
	}

	public void convertFileToFloorplan(String fileLocation){
		try {
			//create objects and import file
			Gson gson = new Gson();
			File file = new File(fileLocation);
			BufferedReader br = new BufferedReader(new FileReader(file));
			//convert json file to tile array
			Tile[] floorTiles = gson.fromJson(br, Tile[].class);
			//add tiles in array to roomLayout
			for(Tile tile: floorTiles){
				roomLayout.put(tile.getId(), tile);
			}

			//get ids from surrounding tiles, pull them from the room layout map and add the tile object to each one
			for(Tile tile: floorTiles){
				String[] surroundingTiles = tile.getSurroundingTileID();
				if(surroundingTiles[Tile.RIGHT_ID_INDEX] != null){
					tile.setRightNext(roomLayout.get(surroundingTiles[Tile.RIGHT_ID_INDEX]));
				}
				if(surroundingTiles[Tile.LEFT_ID_INDEX] != null){
					tile.setLeftNext(roomLayout.get(surroundingTiles[Tile.LEFT_ID_INDEX]));
				}
				if(surroundingTiles[Tile.TOP_ID_INDEX] != null){
					tile.setTopNext(roomLayout.get(surroundingTiles[Tile.TOP_ID_INDEX]));
				}
				if(surroundingTiles[Tile.BOTTOM_ID_INDEX] != null){
					tile.setBottomNext(roomLayout.get(surroundingTiles[Tile.BOTTOM_ID_INDEX]));
				}
			}
			logger.info("Floor plan successfully loaded from file");
		} catch (Exception e){
			logger.error("An error occured!", e);
		}
	}

	public void buildGenericFloorPlan(){
		SecureRandom random = new SecureRandom();

		//these loops create the tiles and add them to the map
		for(int i = 0; i < axisX.length; i++){
			for(int j = axisYMin; j <= axisYMax; j++ ){
				Tile tempTile = new Tile();
				//setting tile to random floor type declared at top of class
				tempTile.setSurfaceType(floorTypes[random.nextInt(floorTypes.length)]);
				//tile is randomly an obstacle or not
				tempTile.setIsObstacle(isObstacle[random.nextInt(isObstacle.length)]);
				String tempID = axisX[i] + j;
				tempTile.setId(tempID);
				roomLayout.put(tempTile.getId(), tempTile);
			}
		}
		
		//these loops go and attempt to get all the tiles in all directions; ignores those tiles that are out of bounds
		for(int z = 0; z < axisX.length; z++){
			Tile tempTile;
			String letter = axisX[z];
			for(int x = axisYMin; x <= axisYMax; x++ ){
				String targetTile = letter + x;
				tempTile = roomLayout.get(targetTile);
				
				assignAdjacentTiles(tempTile, x, z);
			}
		}
		
		//get tile g3 in order to make it the the charging station
		Tile chargingStation = roomLayout.get("d3");
		chargingStation.setChargingStation(true);
		logger.info("Floor plan has successfully been built");
	}
	
	public void assignAdjacentTiles(Tile tempTile, int x, int z) {
		
		//try getting the tile above target tile
		if (z-1 >= 0) {
			String tileAbove = axisX[z-1] + x;
			Tile upTile = roomLayout.get(tileAbove);
			tempTile.setTopNext(upTile);
		}
		
		//try getting the tile below the target tile
		if (z+1 < axisX.length) {
			String tileBelow = axisX[z+1] + x;
			Tile bottomTile = roomLayout.get(tileBelow);
			tempTile.setBottomNext(bottomTile);
		}
		
		//try getting the tile to the right of target tile
		if (x+1 <= axisYMax) {
			String tileRight = axisX[z] + (x+1);
			Tile rightTile = roomLayout.get(tileRight);
			tempTile.setRightNext(rightTile);
		}
		
		//try getting the tile to the left of target tile
		if (x-1 >= axisYMin) {
			String tileLeft = axisX[z] + (x-1);
			Tile leftTile = roomLayout.get(tileLeft);
			tempTile.setLeftNext(leftTile);
		}
	}

	public File writeFloorPlanToFile(){
		String directory = "src/main/resources";
		String filename = "SampleFloor" + UUID.randomUUID() +".json";
		
		return writeFloorPlanToFile(directory, filename);
	}
	
	public File writeFloorPlanToFile(String directory, String fileName){
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
		Tile[] floorTiles = roomLayout.values().toArray(new Tile[0]);
		File outputFile = null;
		
		for(Tile tile: floorTiles){
			tile.setSurroundingTileID(tile);
		}
		try{
			outputFile = new File(directory, fileName + UUID.randomUUID() +".json");
			FileWriter writer = new FileWriter(outputFile);
			gson.toJson(floorTiles, writer);
			writer.flush();
			writer.close();
			logger.info("Floor plan saved to file");
		} catch (Exception e){
			logger.error("An erro occured!", e);
		}
		return outputFile;
	}
}
