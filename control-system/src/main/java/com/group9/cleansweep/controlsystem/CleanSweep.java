package com.group9.cleansweep.controlsystem;

import java.util.Map;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import com.group9.sensor_simulator.FloorTypeSimulator;


public class CleanSweep {

	/*
	 * floor plan to get all tiles or get start tile Navigation object move to next
	 * tile - return new tile Dirt Detection object is capacity full? >=4 if full ->
	 * emptyDirtTank clean dirt stuff
	 * 
	 * Power Management object
	 * 
	 */
	DirtDetection dirtDetection = new DirtDetection();
	PowerManagement powerManagement = new PowerManagement();
	int totalDirtCollected=0;
	public CleanSweep(){


	}

	public void doWork() {
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.buildGenericFloorPlan();
		Navigation navigation = new Navigation(floorPlan);
	
		Tile firstTile = new Tile();
		Tile previousTile = new Tile();
		Tile nextTile = new Tile();
		Tile tempTile = new Tile();
		Boolean keepWorking = true;
		boolean isMinimumPowerCapacityReached=false;
		String[] list = new String[200];
		int i = 0;

		firstTile = navigation.currentPos;
		nextTile = null;
		Map<String, Tile> floorPlanDirtMap = dirtDetection.setRandomDirt(floorPlan);
		System.out.println("Clean Sweep is starting on tile " + firstTile.getId());
		while (keepWorking) {


			//for first time in the loop
			if(nextTile == null){
				previousTile = firstTile;
			}
//			else if (nextTile.getId() == previousTile.getId()) {
//				System.out.println("We ran into a wall.  Stopping shortly...");
//				keepWorking = false;
//			}
			else{
				System.out.println("Previous tile: " + previousTile.getId() + " Next Tile: " + nextTile.getId());
				list[i] = previousTile.getId();
				i++;
				previousTile = nextTile;
				for (int g = 0; g < list.length ;g++) {
					if (nextTile.getId() == list[g]) {
						System.out.println("We've encountered multiple isVisited tiles in a row.  Returning to Power Station at the end of this cycle.");
						previousTile = firstTile;
						break;
					}
				}
			}

			if(isMinimumPowerCapacityReached){
				System.out.println("Power is low.  Returning to charging station.");
				break;
			}
			nextTile = navigation.traverse(previousTile);



			if(navigation.isCycleComplete()){
				keepWorking = false;
			}
			else
			{
				totalDirtCollected=dirtDetection.cleanDirt(nextTile,dirtDetection);
				dirtDetection.setTotalDirtCollected(totalDirtCollected);
				isMinimumPowerCapacityReached=powerManagement.powerManagementProcess(previousTile,nextTile,nextTile.getDirtAmount());
		
			}
		
		
			
	
			//Insert battery check logic here:

			//This will check if all 4 directions become blocked somehow.
//			if (currentTile.equals(null))
//				return;
//
//			//If you navigate to a new tile and it is visited, check to see if ALL tiles are visited.
//			if (currentTile.isVisited()) {
//				navigation.setIgnoreIsVisited(true);
//				//If all tiles visited, check cycle completion first
//				if (navigation.isCycleComplete()) {
//					floorPlan.writeFloorPlanToFile();
//					return;
//				}
//				//If not all tiles visited, try checking all other tiles for unvisited
//				else {
//					System.out.println("Cycle is not yet complete.  Checking adjacent tiles.");
//					currentTile = navigation.traverseTop(currentTile);
//					//If all adjacent tiles are still visited, stop the loop and go back to the charging station to try again later.
//					if(currentTile.isVisited()) {
//						System.out.println("All nearby tiles have been visited.  Starting over from charging station");
//						//Insert navigation back to charging station here:
//						return;
//					}
//				}
//			}
		
		}
		
		System.out.println("\nCurrent Dirt Amount per tile:\n");
		for (Map.Entry<String, Tile> entry : floorPlanDirtMap.entrySet()) {

			System.out.println("Key = " + entry.getKey() + ", Dirt Amount = " + entry.getValue().getDirtAmount());
		}
	}
	
	public void dirtDetectionProcess() {
		
	}
//	public void doWorkFromFile(String fileLocation){
//		FloorPlan floorPlan = new FloorPlan();
//		floorPlan.convertFileToFloorplan("src/main/java/com/group9/cleansweep/controlsystem/FloorPlanFile/SampleFloor.json");
//		DirtDetection dirtDetection = new DirtDetection();
//		dirtDetectionProcess(floorPlan);
//
//	}
//

}