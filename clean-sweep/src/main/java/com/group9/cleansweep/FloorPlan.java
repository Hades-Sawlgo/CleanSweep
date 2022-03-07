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
import com.group9.cleansweep.enums.SurfaceTypeEnum;
import com.group9.cleansweep.enums.TileTypeEnum;

public class FloorPlan {
	
	private static Logger logger = LoggerFactory.getLogger(FloorPlan.class);
	
	@Getter private String defaultDirectory = "src\\main\\resources\\outputs";
	@Getter private String defaultFilename = "SampleFloor";
	
	//this keeps track of all the tiles in a room String is the ID of the tile
	private final Map<String, Tile> roomLayout;
	
	//Dimension information on floor
	@Getter private String[] axisX;
	@Getter private int axisYMin;
	@Getter private int axisYMax;
	
	private SecureRandom random = new SecureRandom();

	public FloorPlan(){
		this.roomLayout = new HashMap<>();
		this.axisX = new String []{"a", "b", "c", "d", "e", "f", "g"};
		this.axisYMin = 1;
		this.axisYMax = 7;
		
	}
	
	// TODO: Should I have this?
	public FloorPlan(Map<String, Tile> roomLayout, String[] axisX, int axisYMin, int axisYMax){
		this.roomLayout = roomLayout;
		this.axisX = axisX;
		this.axisYMin = axisYMin;
		this.axisYMax = axisYMax;
		
	}
	
	// TODO: Decide if this method should be kept
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

		//these loops create the tiles and add them to the map
		for(int i = 0; i < axisX.length; i++){
			for(int j = axisYMin; j <= axisYMax; j++ ){
				Tile tempTile = new Tile();
				//setting tile to random floor type declared at top of class
				tempTile.setSurfaceType(SurfaceTypeEnum.getRandomEnum());
				//tile is randomly an obstacle or not
				if(random.nextBoolean()) {
					tempTile.setTileType(TileTypeEnum.OBSTACLE);
				}
					
				String tempID = axisX[i] + j;
				tempTile.setId(tempID);
				roomLayout.put(tempTile.getId(), tempTile);
			}
		}
		
		//these loops go and attempt to get all the tiles in all directions; ignores those tiles that are out of bounds
		for(int x = 0; x < axisX.length; x++){
			Tile tempTile;
			String letter = axisX[x];
			for(int y = axisYMin; y <= axisYMax; y++ ){
				String targetTile = letter + y;
				tempTile = roomLayout.get(targetTile);
				
				assignAdjacentTiles(tempTile, x, y);
			}
		}
		
		//get tile g3 in order to make it the the charging station
		Tile chargingStation = roomLayout.get("d3");
		chargingStation.setTileType(TileTypeEnum.POWERSTATION);
		logger.info("Floor plan has successfully been built");
	}
	
	public void assignAdjacentTiles(Tile tempTile, int x, int y) {
		
		// TODO: assumes x and y correctly identifies tempTile and that x and y are within the grid
		// Should this assumption become a check?
		
		//try getting the tile above target tile
		if (y+1 <= axisYMax) {
			String tileAbove = axisX[x] + (y+1);
			Tile upTile = roomLayout.get(tileAbove);
			tempTile.setTopNext(upTile);
		}
		
		//try getting the tile below the target tile
		if (y-1 >= axisYMin) {
			String tileBelow = axisX[x] + (y-1);
			Tile bottomTile = roomLayout.get(tileBelow);
			tempTile.setBottomNext(bottomTile);
		}
		
		//try getting the tile to the right of target tile
		if (x+1 < axisX.length) {
			String tileRight = axisX[x+1] + y;
			Tile rightTile = roomLayout.get(tileRight);
			tempTile.setRightNext(rightTile);
		}
		
		//try getting the tile to the left of target tile
		if (x-1 >= 0) {
			String tileLeft = axisX[x-1] + y;
			Tile leftTile = roomLayout.get(tileLeft);
			tempTile.setLeftNext(leftTile);
		}
	}

	public File writeFloorPlanToFile(){		
		return writeFloorPlanToFile(defaultDirectory, defaultFilename);
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
