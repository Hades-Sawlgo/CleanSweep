package com.group9.cleansweep.controlsystem;

import lombok.Getter;
import com.group9.cleansweep.Tile;
import com.group9.sensor_simulator.ObstacleSimulator;

import lombok.Setter;

import java.util.Map;
import java.util.Stack;
import com.group9.cleansweep.FloorPlan;
public class Navigation {
	// this is where the stack/queue would be for tiles that have been visited
	// need a method that returns boolean for is cleaning done
	Stack<Tile> visited;
	Tile currentPos = new Tile();
	FloorPlan floorPlan;
	private Boolean ignoreIsVisited = false;

	Map<String, Tile> floorPlanMap;

	public Navigation(FloorPlan floorPlan) {
		this.visited = new Stack<>();
		this.floorPlan = floorPlan;
		this.floorPlanMap = floorPlan.getFloorPlanMap();

		// Assuming charging station is start
		// set current position to the charging station

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

	public Tile traverse(Tile target){
		// always start with top and move through the other methods
		return traverseTop(target);
	}

	private Tile traverseTop(Tile target) {
		try {
			if (isObstacleTop(target)) {
				return traverseRight(target);
			}
			else if (target.getTopNext().isVisited()){
				System.out.println("top tile already visited.  Trying Right.");
				return traverseRight(target);
			}
			else {
				{
					System.out.println("Top direction is clear.  Proceeding.");
					target.setVisited(true);
					System.out.println("Traversed up from tile " + target.getId() + " to tile " + target.getTopNext().getId() + ".");
					return target.getTopNext();
				}
			}
		}
		catch(NullPointerException e) {
			System.out.println("Encountered a wall.  We'll try traversing to the right.");
			return traverseRight(target);
		}
	}

	private Tile traverseRight(Tile target) {
		try {
			if (isObstacleRight(target)) {
				return traverseBottom(target);
			}
			else if (target.getRightNext().isVisited()){
				System.out.println("Right tile already visited.  Trying bottom.");
				return traverseBottom(target);
			}
			else {
				System.out.println("Right direction is clear.  Proceeding.");
				target.setVisited(true);
				System.out.println("Traversed right from tile " + target.getId() + " to tile " + target.getRightNext().getId() + ".");
				return target.getRightNext();
			}
		}
		catch(NullPointerException e) {
			System.out.println("Encountered a wall.  We'll try traversing Bottom.");
			return traverseBottom(target);
		}
	}

	private Tile traverseBottom(Tile target){
		try {
			if (isObstacleBottom(target)) {
				return traverseLeft(target);
			}
			else if (target.getBottomNext().isVisited()){
				System.out.println("Bottom tile already visited.  Trying Left.");
				return traverseLeft(target);
			}
			else {
				System.out.println("Bottom direction is clear.  Proceeding.");
				target.setVisited(true);
				System.out.println("Traversed down from tile " + target.getId() + " to tile " + target.getBottomNext().getId() + ".");
				return target.getBottomNext();
			}
		}
		catch(NullPointerException e) {
			System.out.println("Encountered a wall.  We'll try traversing Left.");
			return traverseLeft(target);
		}
	}

	private Tile traverseLeft(Tile target) {
		try {
			if (isObstacleLeft(target)) {
				System.out.println("Clean Sweep encountered an obstacle on all sides.  Stopping.");
				return target;
			}
			else if (target.getLeftNext().isVisited()){
				System.out.println("Left tile is already visited.  Returning to Charging station since all surrounding tiles are visited.");
				return target;
			}
			else {
				System.out.println("Left direction is clear.  Proceeding.");
				target.setVisited(true);
				System.out.println("Traversed down from tile " + target.getId() + " to tile " + target.getLeftNext().getId() + ".");
				return target.getLeftNext();
			}
		}
		catch(NullPointerException e) {
			System.out.println("Encountered a wall.  Since all directions are checked, we'll stop here.");
			return target;
		}
	}



	private Boolean isObstacleRight(Tile currentPos) {
//		currentPos.getRightNext().setIsObstacle(ObstacleSimulator.getInstance().getRandomObstacle());
		if(currentPos.getRightNext().getObstacle()) {
			System.out.println("Detected tile " + currentPos.getRightNext().getId() + " as obstacle to the right. Checking Bottom Sensor");
			return true;
		} else return false;
	}

	private Boolean isObstacleLeft(Tile currentPos) {
//		currentPos.getLeftNext().setIsObstacle(ObstacleSimulator.getInstance().getRandomObstacle());
		if(currentPos.getLeftNext().getObstacle()) {
			System.out.println("Detected tile " + currentPos.getLeftNext().getId() + " as obstacle to the left.");
			return true;
		} else return false;
	}

	private Boolean isObstacleTop(Tile currentPos) {
//		currentPos.getTopNext().setIsObstacle(ObstacleSimulator.getInstance().getRandomObstacle());
		if(currentPos.getTopNext().getObstacle()) {
			System.out.println("Detected tile " + currentPos.getTopNext().getId() + " as obstacle above. Checking Right Sensor.");
			return true;
		} else {
			return false;
		}
	}

	private Boolean isObstacleBottom(Tile currentPos) {
//		currentPos.getBottomNext().setIsObstacle(ObstacleSimulator.getInstance().getRandomObstacle());
		if(currentPos.getBottomNext().getObstacle()) {
			System.out.println("Detected tile " + currentPos.getBottomNext().getId() + " as obstacle below. Checking Left Sensor.");
			return true;
		} else return false;
	}



    // TODO: go back to power station
	//  keep iterating back through stack, traversing back to charging station
	// Still need to figure out algorithm for it
	private void backToChargingStation() {

	}




	public boolean isCycleComplete() {
		Tile checkTile = new Tile();
		Tile[] allTiles = floorPlan.getFloorPlanMap().values().toArray(new Tile[floorPlan.getFloorPlanMap().values().size()]);
		for (int i = 0; i < allTiles.length; ) {
			checkTile = allTiles[i];
			if (checkTile.isVisited())
				continue;
			else
				return false;
		}
		return true;
	}

	public void resetIgnoreIsVisited(){
		ignoreIsVisited = false;
	}

	public void setIgnoreIsVisited(boolean b) {
		ignoreIsVisited = b;
	}

	private boolean isIgnoreIsVisited() {
		// TODO Auto-generated method stub
		return ignoreIsVisited;
	}

	// vv Done vv

	//print moved up one cell
	//print moved left one cell
	//print attempting to move up one cell
	//print obstacle detected one cell up
	//print attempting to move left one cell
	//print moved left one cell
	//return left Tile object
}
