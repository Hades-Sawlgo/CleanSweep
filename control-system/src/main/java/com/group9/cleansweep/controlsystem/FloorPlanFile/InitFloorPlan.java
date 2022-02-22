package com.group9.cleansweep.controlsystem.FloorPlanFile;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InitFloorPlan {
    List<List<String>> initialFloorPlanObject;
    String initFloorPlanJson;
  	 private static final String OBSTACLE = "OBSTACLE";
    public InitFloorPlan(){
 
		initialFloorPlanObject = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            initialFloorPlanObject.add(new ArrayList<String>());
            for (int j = 0; j < 20; j++){
                initialFloorPlanObject.get(i).add("UNKNOWN");
            }
            initialFloorPlanObject.get(i).set(0, OBSTACLE);
            initialFloorPlanObject.get(i).set(19, OBSTACLE);
            initialFloorPlanObject.get(0).set(i, OBSTACLE);
        }
        for (int i = 0; i < 20; i++){
            initialFloorPlanObject.get(19).set(i, OBSTACLE);
        }
        initialFloorPlanObject.get(1).set(1,"POWERSTATION");
    }
    public void createInitFloorPlanJson(){
        Gson gson = new Gson();
        initFloorPlanJson = gson.toJson(initialFloorPlanObject);
    }


}


