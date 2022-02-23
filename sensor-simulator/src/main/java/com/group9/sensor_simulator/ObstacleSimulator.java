package com.group9.sensor_simulator;

import java.security.SecureRandom;


public class ObstacleSimulator {
    private static ObstacleSimulator obstacleSimulatorInstance = null;
    private final SecureRandom random;
    private final Boolean[] randomBool = {false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, true, false, false};
    private ObstacleSimulator(){
    	random = new SecureRandom();
    }

    public static ObstacleSimulator getInstance(){
        if(obstacleSimulatorInstance == null){
        	obstacleSimulatorInstance = new ObstacleSimulator();
        }
        return  obstacleSimulatorInstance;
    }

    public Boolean getRandomObstacle(){
        //did it this way to bias towards not being an obstacle
        return randomBool[random.nextInt(randomBool.length)];
    }


}
