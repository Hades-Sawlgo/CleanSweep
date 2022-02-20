package com.group9.cleansweep.controlsystem.FloorPlanFile;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: asayu
 * @Creat:10/12/21 3:08 PM
 **/
public class FloorPlanExample {
    List<List<String>> floorPlanExample;
    String FloorPlanExampleJson;
    public void createFloorPlanExample(){
        floorPlanExample = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            floorPlanExample.add(new ArrayList<String>());
            for (int j = 0; j < 20; j++){
                floorPlanExample.get(i).add("UNKNOWN");
            }
            floorPlanExample.get(i).set(0, "OBSTACLE");
            floorPlanExample.get(i).set(19, "OBSTACLE");
            floorPlanExample.get(0).set(i, "OBSTACLE");
        }
        for (int i = 0; i < 20; i++){
            floorPlanExample.get(19).set(i, "OBSTACLE");
            floorPlanExample.get(i).set(8,"OBSTACLE");
            //col walls
            if (i > 10){
                floorPlanExample.get(i).set(12,"OBSTACLE");
            }
            if (i < 7){
                floorPlanExample.get(i).set(16,"OBSTACLE");
            }
            if (i >= 6 && i <= 8){
                floorPlanExample.get(i).set(4,"OBSTACLE");
            }
            //row walls
            if (i >= 8 && i <= 16){
                floorPlanExample.get(2).set(i,"OBSTACLE");
            }
            if (i >= 8 && i <= 12){
                floorPlanExample.get(4).set(i,"OBSTACLE");
            }
            if (i <= 8) {
                floorPlanExample.get(6).set(i, "OBSTACLE");
                floorPlanExample.get(8).set(i, "OBSTACLE");
                floorPlanExample.get(10).set(i, "OBSTACLE");
            }
            if (i >= 16) {
                floorPlanExample.get(6).set(i, "OBSTACLE");
            }
        }
        //set row doors
        floorPlanExample.get(2).set(14,"BARE_FOOT");
        floorPlanExample.get(4).set(10,"BARE_FOOT");
        floorPlanExample.get(6).set(14,"BARE_FOOT");
        floorPlanExample.get(10).set(4,"BARE_FOOT");
        //set col doors
        floorPlanExample.get(5).set(8,"BARE_FOOT");
        floorPlanExample.get(6).set(8,"BARE_FOOT");
        floorPlanExample.get(13).set(8,"BARE_FOOT");
        floorPlanExample.get(5).set(12,"BARE_FOOT");
        floorPlanExample.get(3).set(16,"BARE_FOOT");
        //set stairs
        for(int i = 8; i < 10; i++)
            floorPlanExample.get(19).set(i,"STAIRS");
        //set tiles
        for (int i = 1; i < 8; i++){
            for(int j = 1; j < 6; j++)
                floorPlanExample.get(i).set(j,"LOW_PILE_CARPET");
            for (int j = 18; j > 10; j--){
                floorPlanExample.get(i).set(j,"LOW_PILE_CARPET");
            }
        }

        for (int i = 8; i < 18;i++){
            for (int j = 14; j < 18; j++)
                floorPlanExample.get(i).set(j,"HIGH_PILE_CARPET");
        }
        for (int i = 1; i < 19; i++){
            for (int j = 1; j < 19; j++){
                if (floorPlanExample.get(i).get(j) == "UNKNOWN")
                    floorPlanExample.get(i).set(j,"BARE_FOOT");
            }
        }
        //set power station
        floorPlanExample.get(18).set(1, "CHARGINGSTATION");
    }
    public void createInitFloorPlanJson(){
        Gson gson = new Gson();
        FloorPlanExampleJson = gson.toJson(floorPlanExample);
    }

//    public static void main(String[] args) {
//        FloorPlanExample a = new FloorPlanExample();
//        a.createFloorPlanExample();
//        a.createInitFloorPlanJson();
//
//        InitFloorPlan init = new InitFloorPlan();
//        init.createInitFloorPlanJson();
//
//        inputFile(a.FloorPlanExampleJson, "src/main/java/com/group9/cleansweep/Model/FloorPlanFile/FloorPlanFileExample.json");
//        inputFile(init.initFloorPlanJson, "src/main/java/com/group9/cleansweep/Model/FloorPlanFile/InitFloorPlanFile.json");
//    }
    public static void inputFile(final String str, final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeJsonToFile(str, path);
            }
        }).start();
    }
    private static void writeJsonToFile(String context, String path){
        File file = new File(path);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        try{
            file.delete();
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            FileWriter fw = new FileWriter(file, true);
            fw.write(context);
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
