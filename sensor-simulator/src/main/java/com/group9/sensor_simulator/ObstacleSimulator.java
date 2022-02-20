package com.group9.sensor_simulator;

import java.util.Random;


public class ObstacleSimulator {
    private static ObstacleSimulator obstacleSimulator_instance = null;
    private final Random random;
    private final Boolean[] randomBool = {false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, true, false, false};
    private ObstacleSimulator(){random = new Random();
    }

    public static ObstacleSimulator getInstance(){
        if(obstacleSimulator_instance == null){
            obstacleSimulator_instance = new ObstacleSimulator();
        }
        return  obstacleSimulator_instance;
    }

    public Boolean getRandomObstacle(){
        //did it this way to bias towards not being an obstacle
        return randomBool[random.nextInt(randomBool.length)];
    }


}
