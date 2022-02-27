package com.group9.cleansweep.controlsystem;

import java.io.IOException;
import java.util.Map;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CleanSweep {

	private static Logger logger = LoggerFactory.getLogger(CleanSweep.class);
	
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

	Tile firstTile;
	Tile previousTile;
	Tile nextTile;
	Boolean keepWorking;
	boolean isMinimumPowerCapacityReached;
	String[] list;
	int i;

	public CleanSweep(){
		// Empty constructor
	}

	public void doWork() throws IOException {
		FloorPlan floorPlan = new FloorPlan();
		floorPlan.buildGenericFloorPlan();
		Navigation navigation = new Navigation(floorPlan);

		previousTile = new Tile();
		keepWorking = true;
		isMinimumPowerCapacityReached=false;
		list = new String[200];
		i = 0;

		firstTile = navigation.currentPos;
		nextTile = null;
		Map<String, Tile> floorPlanDirtMap = dirtDetection.setRandomDirt(floorPlan);
		String loggerInfo = String.format("Clean Sweep is starting on tile %s", firstTile.getId());
		logger.info(loggerInfo);

		while (Boolean.TRUE.equals(keepWorking)) {

			visitedTileUpdate();

			if(isMinimumPowerCapacityReached){
				logger.info("Power is low.  Returning to charging station.");
				break;
			}
			nextTile = navigation.traverse(previousTile);


			if(navigation.isCycleComplete()){
				keepWorking = false;
			}
			else{
				totalDirtCollected=dirtDetection.cleanDirt(nextTile,dirtDetection);
				dirtDetection.setTotalDirtCollected(totalDirtCollected);
				isMinimumPowerCapacityReached=powerManagement.powerManagementProcess(previousTile,nextTile,nextTile.getDirtAmount());
		
			}
		}
		
		logger.info("\nCurrent Dirt Amount per tile:\n");
		for (Map.Entry<String, Tile> entry : floorPlanDirtMap.entrySet()) {

			loggerInfo = String.format("Key = %s, Dirt Amount = %s", entry.getKey(), entry.getValue().getDirtAmount());
			logger.info(loggerInfo);
		}
	}

	private void visitedTileUpdate() {

		// for first time in the loop
		if(nextTile == null) {
			previousTile = firstTile;
		}
		else{
			String loggerInfo = String.format("Previous tile: %s Next Tile: %s", previousTile.getId(), nextTile.getId());
			logger.info(loggerInfo);
			list[i] = previousTile.getId();
			i++;
			previousTile = nextTile;
			for (int g = 0; g < list.length; g++) {
				if (nextTile.getId().equals(list[g])) {
					logger.info("We've encountered multiple isVisited tiles in a row.  Returning to Power Station at the end of this cycle.");
					previousTile = firstTile;
					break;
				}
			}
		}
	}
}