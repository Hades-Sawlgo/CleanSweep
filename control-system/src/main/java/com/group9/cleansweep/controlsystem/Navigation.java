package com.group9.cleansweep.controlsystem;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Stack;

public class Navigation {

	private static Logger logger = LoggerFactory.getLogger(Navigation.class);

	// This is where the stack/queue would be for tiles that have been visited.
	// Need a method that returns boolean for is cleaning done.
	Stack<Tile> visited;
	Tile currentPos = new Tile();
	FloorPlan floorPlan;
	Map<String, Tile> floorPlanMap;

	public Navigation(FloorPlan floorPlan) {
		this.visited = new Stack<>();
		this.floorPlan = floorPlan;
		this.floorPlanMap = floorPlan.getFloorPlanMap();

		// Assuming charging station is start.
		// Set current position to the charging station.

		for(Map.Entry<String, Tile> entry : floorPlanMap.entrySet()){
			if(entry.getValue().isChargingStation()) {
				currentPos = entry.getValue();
				currentPos.setRightNext(currentPos.getRightNext());
				currentPos.setLeftNext(currentPos.getLeftNext());
				currentPos.setTopNext(currentPos.getTopNext());
				currentPos.setBottomNext((currentPos.getBottomNext()));
			}
		}
	}

	// Always start with top and move through the other methods.
	public Tile traverse(Tile target){
		return traverseTop(target);
	}

	private Tile traverseTop(Tile target) {
		try {
			if (Boolean.TRUE.equals(isObstacleTop(target))) {
				return traverseRight(target);
			}
			else if (target.getTopNext().isVisited()){
				logger.info("top tile already visited. Trying Right.");
				return traverseRight(target);
			}
			else {
				logger.info("Top direction is clear. Proceeding.");
				target.setVisited(true);
				String stringOutput = String.format("Traversed up from tile %s to tile %s.",
						target.getId(), target.getTopNext().getId());
				logger.info(stringOutput);
				return target.getTopNext();
			}
		}
		catch(NullPointerException e) {
			logger.error("Encountered a wall. We'll try traversing to the right.");
			return traverseRight(target);
		}
	}

	private Tile traverseRight(Tile target) {
		try {
			if (Boolean.TRUE.equals(isObstacleRight(target))) {
				return traverseBottom(target);
			}
			else if (target.getRightNext().isVisited()){
				logger.info("Right tile already visited. Trying bottom.");
				return traverseBottom(target);
			}
			else {
				logger.info("Right direction is clear. Proceeding.");
				target.setVisited(true);
				String stringOutput = String.format("Traversed right from tile %s to tile %s.",
						target.getId(), target.getRightNext().getId());
				logger.info(stringOutput);
				return target.getRightNext();
			}
		}
		catch(NullPointerException e) {
			logger.error("Encountered a wall. We'll try traversing Bottom.");
			return traverseBottom(target);
		}
	}

	private Tile traverseBottom(Tile target){
		try {
			if (Boolean.TRUE.equals(isObstacleBottom(target))) {
				return traverseLeft(target);
			}
			else if (target.getBottomNext().isVisited()){
				logger.info("Bottom tile already visited. Trying Left.");
				return traverseLeft(target);
			}
			else {
				logger.info("Bottom direction is clear. Proceeding.");
				target.setVisited(true);
				String stringOutput = String.format("Traversed down from tile %s to tile %s.",
						target.getId(), target.getBottomNext().getId());
				logger.info(stringOutput);
				return target.getBottomNext();
			}
		}
		catch(NullPointerException e) {
			logger.error("Encountered a wall. We'll try traversing Left.");
			return traverseLeft(target);
		}
	}

	private Tile traverseLeft(Tile target) {
		try {
			if (Boolean.TRUE.equals(isObstacleLeft(target))) {
				logger.info("Clean Sweep encountered an obstacle on all sides. Stopping.");
				return target;
			}
			else if (target.getLeftNext().isVisited()){
				logger.info("Left tile is already visited. Returning to Charging station since all surrounding tiles are visited.");
				return target;
			}
			else {
				logger.info("Left direction is clear. Proceeding.");
				target.setVisited(true);
				String stringOutput = String.format("Traversed left from tile %s to tile %s.",
						target.getId(), target.getLeftNext().getId());
				logger.info(stringOutput);
				return target.getLeftNext();
			}
		}
		catch(NullPointerException e) {
			logger.error("Encountered a wall. Since all directions are checked, we'll stop here.");
			return target;
		}
	}



	private Boolean isObstacleRight(Tile currentPos) {
		if(Boolean.TRUE.equals(currentPos.getRightNext().getObstacle())) {
			String stringOutput = String.format("Detected tile %s as obstacle to the right. Checking Bottom Sensor.", currentPos.getRightNext().getId());
			logger.info(stringOutput);
			return true;
		} else return false;
	}

	private Boolean isObstacleLeft(Tile currentPos) {
		if(Boolean.TRUE.equals(currentPos.getLeftNext().getObstacle())) {
			String stringOutput = String.format("Detected tile %s as obstacle to the left. Checking Left Sensor.", currentPos.getLeftNext().getId());
			logger.info(stringOutput);
			return true;
		} else return false;
	}

	private Boolean isObstacleTop(Tile currentPos) {
		if(Boolean.TRUE.equals(currentPos.getTopNext().getObstacle())) {
			String stringOutput = String.format("Detected tile %s as obstacle above. Checking Right Sensor.", currentPos.getTopNext().getId());
			logger.info(stringOutput);
			return true;
		} else {
			return false;
		}
	}

	private Boolean isObstacleBottom(Tile currentPos) {
		if(Boolean.TRUE.equals(currentPos.getBottomNext().getObstacle())) {
			String stringOutput = String.format("Detected tile %s as obstacle below. Checking Left Sensor.", currentPos.getBottomNext().getId());
			logger.info(stringOutput);
			return true;
		} else return false;
	}

	public boolean isCycleComplete() {
		Tile checkTile;
		Tile[] allTiles = floorPlan.getFloorPlanMap().values().toArray(new Tile[floorPlan.getFloorPlanMap().values().size()]);
		for (int i = 0; i < allTiles.length; ) {
			checkTile = allTiles[i];
			if (!checkTile.isVisited())
				return false;
		}
		return true;
	}
}
