package com.group9.cleansweep.controlsystem.FloorPlanFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReadFloorPlanFile {
    public static String[][] readFile(String path) {
        String[][] res = new String[20][20];
        String jsonString;
        File file = new File(path);
        try{
            FileInputStream inputStream = new FileInputStream(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
            String[] lines = jsonString.split("\\[|]");
            int i = 0, j = 0;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
//        try{
//            //JSONParser jsonParser = new JSONParser();
//            JsonParser jsonParser = new JsonParser();
//            FileReader fileReader = new FileReader(path);
//            Object obj = jsonParser.parse(fileReader);
//            //JsonObject jsonObject = (JsonObject) obj;
//            System.out.println(obj);
//            //JsonArray jsonArray = (JsonArray) jsonParser.parse(fileReader);
//            int i = 0, j = 0;
////            for(JsonElement jsonElement : jsonArray){
//////                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
//////                    res[i][j] = entry.getValue().toString();
//////                    j++;
//////                }
////                JsonArray jsonArray2 = (JsonArray) jsonElement;
////                for (JsonElement s: jsonArray2)
////                    //System.out.println(s);
////                    res[i][j] = s.toString();
////                   j++;
////                i++;
////                j = 0;
//            } catch (FileNotFoundException fileNotFoundException) {
//            fileNotFoundException.printStackTrace();
//        }

//    } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return res;
//    }

//    public static void main(String[] args) throws FileNotFoundException {
//        String[][] a = readFile("src/main/java/com/group9/cleansweep/Model/FloorPlanFile/FloorPlanFileExample.json");
//        System.out.println(a[19][19]);
//    }
//}
