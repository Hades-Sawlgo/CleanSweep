package com.group9.cleansweep.controlsystem.floorplanfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.group9.cleansweep.enums.TileTypeEnum;

public class InitFloorPlan {
	List<List<String>> initialFloorPlanObject;
	String initFloorPlanJson;
	static final String OBSTACLE = TileTypeEnum.OBSTACLE.toString();
	private static Properties properties = new Properties();
	private static final String CONSTANT_FILE_PATH = "../clean-sweep/src/main/java/com/group9/cleansweep/properties/constant.properties";

	public InitFloorPlan() {
		try (InputStream input = new FileInputStream(CONSTANT_FILE_PATH)) {
			properties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		
		initialFloorPlanObject = new ArrayList<>();
		double outerLoop = Double.parseDouble((properties.getProperty("INIT_FLOOR_PLAN_OUTTER_LOOP")));
		double innerLoop = Double.parseDouble((properties.getProperty("INIT_FLOOR_PLAN_INNER_LOOP")));
		int obstacle = Integer.parseInt((properties.getProperty("INIT_FLOOR_PLAN_OBSTACLE")));				
		
		for (int i = 0; i < outerLoop; i++) {
			initialFloorPlanObject.add(new ArrayList<>());
			for (int j = 0; j < innerLoop; j++) {
				initialFloorPlanObject.get(i).add(TileTypeEnum.UNKNOWN.toString());
			}
			initialFloorPlanObject.get(i).set(0, OBSTACLE);
			initialFloorPlanObject.get(i).set(obstacle, OBSTACLE);
			initialFloorPlanObject.get(0).set(i, OBSTACLE);
		}
		for (int i = 0; i < outerLoop; i++) {
			initialFloorPlanObject.get(obstacle).set(i, OBSTACLE);
		}
		initialFloorPlanObject.get(1).set(1, TileTypeEnum.POWERSTATION.toString());
	}

	public void createInitFloorPlanJson() {
		Gson gson = new Gson();
		initFloorPlanJson = gson.toJson(initialFloorPlanObject);
	}

}
