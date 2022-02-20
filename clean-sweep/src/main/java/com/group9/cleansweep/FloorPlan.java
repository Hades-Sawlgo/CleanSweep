package com.group9.cleansweep;

//import netscape.javascript.JSObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group9.cleansweep.Enum.FloorPlanTypeEnum;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import com.group9.cleansweep.Tile;

public class FloorPlan {
	//this keeps track of all the tiles in a room String is the ID of the tile
	private final Map<String, Tile> roomLayout;
	private Map<String, Boolean> tileVisitedMap;
	private final Boolean[] isObstacle = {true, false};
	private final String[] floorTypes = {"BARE_FOOT", "LOW_PILE_CARPET", "HIGH_PILE_CARPET"};

	@Getter
	FloorPlanTypeEnum floorPlanType;

	public FloorPlan(){
		this.roomLayout = new HashMap<>();
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
				if(surroundingTiles[0] != null){
					tile.setRightNext(roomLayout.get(surroundingTiles[0]));
				}
				if(surroundingTiles[1] != null){
					tile.setLeftNext(roomLayout.get(surroundingTiles[1]));
				}
				if(surroundingTiles[2] != null){
					tile.setTopNext(roomLayout.get(surroundingTiles[2]));
				}
				if(surroundingTiles[3] != null){
					tile.setBottomNext(roomLayout.get(surroundingTiles[3]));
				}
			}
			System.out.println("Floor plan successfully loaded from file");
		} catch (Exception e){
			System.out.println(e);
		}
	}

	public void buildGenericFloorPlan(){
		Random random = new Random();
		String[] alpha = {"a", "b", "c", "d", "e", "f", "g"};
		//these loops create the tiles and add them to the map
		for(int i = 0; i < 7; i++){
			String letter = alpha[i];
			for(int j = 1; j <= 7; j++ ){
				Tile tempTile = new Tile();
				//setting tile to random floor type declared at top of class
				tempTile.setSurfaceType(floorTypes[random.nextInt(floorTypes.length)]);
				//tile is randomly an obstacle or not
				tempTile.setIsObstacle(isObstacle[random.nextInt(isObstacle.length)]);
				String tempID = letter + j;
				tempTile.setID(tempID);
				roomLayout.put(tempTile.getId(), tempTile);
			}
		}
		//these loops go and attempt to get all the tiles in all directions catches the out of bounds and ignores it
		for(int z = 0; z < 7; z++){
			Tile tempTile;
			String letter = alpha[z];
			for(int x = 1; x <= 7; x++ ){
				//try getting the top tile above target
				try{
					String targetTile = letter + x;
					tempTile = roomLayout.get(targetTile);
					String tileAbove = alpha[z-1] + x;
					Tile upTile = roomLayout.get(tileAbove);
					tempTile.setTopNext(upTile);
				} catch(Exception e){
					//ignore
				}
				//try getting the bottom tile above target
				try{
					String targetTile = letter + x;
					tempTile = roomLayout.get(targetTile);
					String tileBelow = alpha[z+1] + x;
					Tile bottomTile = roomLayout.get(tileBelow);
					tempTile.setBottomNext(bottomTile);
				} catch(Exception e){
					//ignore
				}
				//try getting the right tile above target
				try{
					String targetTile = letter + x;
					tempTile = roomLayout.get(targetTile);
					String tileRight = letter + (x+1);
					Tile rightTile = roomLayout.get(tileRight);
					tempTile.setRightNext(rightTile);
				} catch(Exception e){
					//ignore
				}
				//try getting the left tile above target
				try{
					String targetTile = letter + x;
					tempTile = roomLayout.get(targetTile);
					String tileLeft = letter + (x-1);
					Tile leftTile = roomLayout.get(tileLeft);
					tempTile.setLeftNext(leftTile);
				} catch(Exception e){
					//ignore
				}
			}
		}
		//get tile g3 in order to make it the the charging station
		Tile chargingStation = roomLayout.get("d3");
		chargingStation.setChargingStation(true);
		System.out.println("Floor plan has successfully been built");
	}

	public void writeFloorPlanToFile(){
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
		Tile[] floorTiles = roomLayout.values().toArray(new Tile[0]);
		for(Tile tile: floorTiles){
			tile.setSurroundingTileID(tile);
		}
		try{
			FileWriter writer = new FileWriter("src/main/java/com/group9/cleansweep/controlsystem/FloorPlanFile/SampleFloor" + UUID.randomUUID() +".json");
			gson.toJson(floorTiles, writer);
			writer.flush();
			writer.close();
			System.out.println("Floor plan saved to file");
		} catch (Exception e){
			System.out.println(e);
		}

	}
}
