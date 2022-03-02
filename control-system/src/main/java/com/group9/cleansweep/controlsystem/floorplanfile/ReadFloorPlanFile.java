package com.group9.cleansweep.controlsystem.floorplanfile;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReadFloorPlanFile {
	
	private ReadFloorPlanFile() {
	    throw new IllegalStateException();
	}
	
    public static String[][] readFile(String path) {
        String[][] res = new String[20][20];
        String jsonString;
        File file = new File(path);
        try(FileInputStream inputStream = new FileInputStream(file)){
            int size = inputStream.available();
            byte[] buffer = new byte[size];  
            inputStream.read(buffer);
            jsonString = new String(buffer, StandardCharsets.UTF_8);
            String[] lines = jsonString.split("\\[|]");
            int i = 0;
            int j = 0;
            for (String l: lines){
                if(l.length() > 1) {
                    for (String word : l.split(",")) {
                        res[i][j] = word;
                        j++;
                    }
                    i++;
                    j = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return res;
    }
}

