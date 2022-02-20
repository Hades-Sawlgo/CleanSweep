package com.group9.cleansweep.controlsystem.FloorPlanFile;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InitFloorPlan {
    List<List<String>> initFloorPlan;
    String initFloorPlanJson;
    public InitFloorPlan(){
        initFloorPlan = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            initFloorPlan.add(new ArrayList<String>());
            for (int j = 0; j < 20; j++){
                initFloorPlan.get(i).add("UNKNOWN");
            }
            initFloorPlan.get(i).set(0, "OBSTACLE");
            initFloorPlan.get(i).set(19, "OBSTACLE");
            initFloorPlan.get(0).set(i, "OBSTACLE");
        }
        for (int i = 0; i < 20; i++){
            initFloorPlan.get(19).set(i, "OBSTACLE");
        }
        initFloorPlan.get(1).set(1,"POWERSTATION");
    }
    public void createInitFloorPlanJson(){
        Gson gson = new Gson();
        initFloorPlanJson = gson.toJson(initFloorPlan);
    }

//    public static void main(String[] args) {
//        InitFloorPlan a = new InitFloorPlan();
//        a.createInitFloorPlanJson();
//        System.out.println(a.initFloorPlanJson);
//    }
}


