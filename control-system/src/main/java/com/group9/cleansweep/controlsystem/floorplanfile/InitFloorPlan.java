package com.group9.cleansweep.controlsystem.floorplanfile;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.group9.cleansweep.enums.TileTypeEnum;

public class InitFloorPlan {
	List<List<String>> initialFloorPlanObject;
	String initFloorPlanJson;
	static final String OBSTACLE = TileTypeEnum.OBSTACLE.toString();

	public InitFloorPlan() {

		initialFloorPlanObject = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			initialFloorPlanObject.add(new ArrayList<>());
			for (int j = 0; j < 20; j++) {
				initialFloorPlanObject.get(i).add(TileTypeEnum.UNKNOWN.toString());
			}
			initialFloorPlanObject.get(i).set(0, OBSTACLE);
			initialFloorPlanObject.get(i).set(19, OBSTACLE);
			initialFloorPlanObject.get(0).set(i, OBSTACLE);
		}
		for (int i = 0; i < 20; i++) {
			initialFloorPlanObject.get(19).set(i, OBSTACLE);
		}
		initialFloorPlanObject.get(1).set(1, TileTypeEnum.POWERSTATION.toString());
	}

	public void createInitFloorPlanJson() {
		Gson gson = new Gson();
		initFloorPlanJson = gson.toJson(initialFloorPlanObject);
	}

}
