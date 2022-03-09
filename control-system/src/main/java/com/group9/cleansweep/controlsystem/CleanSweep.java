package com.group9.cleansweep.controlsystem;

import java.io.IOException;
import java.util.Map;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;


public class CleanSweep {

	private static final int VISITED_LIST_SIZE = 200;
	private static final int TILE_VISIT_LIMIT = 1;
	private static Logger logger = LoggerFactory.getLogger(CleanSweep.class);
	
	/*
	 * floor plan to get all tiles or get start tile Navigation object move to next
	 * tile - return new tile Dirt Detection object is capacity full? >=4 if full ->
	 * emptyDirtTank clean dirt stuff
	 * 
	 * Power Management object
	 * 
	 */
	private DirtDetection dirtDetection;
	private PowerManagement powerManagement;
	
	private FloorPlan floorPlan;
	private Navigation navigation;

	@Getter @Setter private Tile firstTile;
	@Getter @Setter private Tile previousTile;
	@Getter @Setter private Tile nextTile;
	private boolean isMinPowerCapacityReached;
	@Getter @Setter private String[] visitedList = new String[VISITED_LIST_SIZE];
	@Getter @Setter private int visitedListIndex;

	public CleanSweep(){
		this(new FloorPlan());
	}

	public CleanSweep(FloorPlan floorPlan){
		dirtDetection = new DirtDetection();
		powerManagement = new PowerManagement();
		
		this.floorPlan = floorPlan;
		floorPlan.buildGenericFloorPlan();
		dirtDetection.setRandomDirt(floorPlan);
		navigation = new Navigation(floorPlan);
		
		visitedListIndex = 0;
		previousTile = new Tile();
		nextTile = null;
		
		isMinPowerCapacityReached=false;
	}

	public void doWork() throws IOException {
		boolean keepWorking = true;
		
		firstTile = navigation.currentPos;
		
		String loggerInfo = String.format("Clean Sweep is starting on tile %s", firstTile.getId());
		logger.info(loggerInfo);

		while (Boolean.TRUE.equals(keepWorking)) {

			visitedTileUpdate();

			if(isMinPowerCapacityReached){
				logger.info("Power is low.  Returning to charging station.");
				break;
			}
			nextTile = navigation.traverse(previousTile);

			if(navigation.isCycleComplete(nextTile, previousTile)){
				keepWorking = false;
			}
			else{
				dirtDetection.setTotalDirtCollected(dirtDetection.cleanDirt(nextTile,dirtDetection));
				isMinPowerCapacityReached=powerManagement.powerManagementProcess(previousTile,nextTile,nextTile.getDirtAmount());
			}
		}
		
		logger.info("\nCurrent Dirt Amount per tile:\n");
		for (Map.Entry<String, Tile> entry : floorPlan.getFloorPlanMap().entrySet()) {

			loggerInfo = String.format("Key = %s, Dirt Amount = %s", entry.getKey(), entry.getValue().getDirtAmount());
			logger.info(loggerInfo);
		}
	}

	public void visitedTileUpdate() {

		// for first time in the loop
		if(nextTile == null) {
			previousTile = firstTile;
		}
		else{
			String loggerInfo = String.format("Previous tile: %s Next Tile: %s", previousTile.getId(), nextTile.getId());
			logger.info(loggerInfo);
			visitedList[visitedListIndex] = previousTile.getId();
			previousTile.setVisited(true);
			visitedListIndex++;
			previousTile = nextTile;
			if (hasReachedTileVisitLimit(nextTile)) {
					logger.info("We've encountered multiple isVisited tiles in a row.");
					logger.info("Returning to Power Station at the end of this cycle.");
					previousTile = firstTile;
			}
		}
	}
	
	public boolean hasReachedTileVisitLimit(Tile nextTile) {
		int visitedCount = 0;
		for (int g = 0; g < visitedListIndex; g++) {
			if (nextTile.getId().equals(visitedList[g])) {
				visitedCount++;
			}
		}
		
		boolean returnVal = false;
		if(visitedCount >= TILE_VISIT_LIMIT) {
			returnVal = true;
		}
		return returnVal;
	}
}