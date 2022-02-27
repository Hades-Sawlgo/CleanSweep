package com.group9.cleansweep.controlsystem;

import java.io.IOException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description:
 * @Author: asayu
 * @Creat:11/11/21 4:17 PM
 **/
public class UnregisteredUI {
	private static Logger logger = LoggerFactory.getLogger(UnregisteredUI.class);

	private UnregisteredUI() {
	    throw new IllegalStateException();
	}
	
    public static void unregisteredUI(CleanSweep cleanSweep) throws IOException{
    	logger.info("You're not logged in.");
    	logger.info("Press Y to run by default floor plan, Press N to break:");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!input.equals("Y") && !input.equals("N") && !input.equals("y") && !input.equals("n")){
        	logger.info("Input value wrong!");
        	logger.info("Press Y to run by default floor plan, Press N to break:");
            input = sc.nextLine();
        }
        if(input.equals("Y") || input.equals("y")) cleanSweep.doWork();
    }
}

