package com.group9.cleansweep.controlsystem;

import java.util.Scanner;

/**
 * @Description:
 * @Author: asayu
 * @Creat:11/11/21 4:17 PM
 **/
public class UnregisteredUI {
    public static void UnregisteredUI(CleanSweep cleanSweep){
        System.out.println("You're not logged in.");
        System.out.println("Press Y to run by default floor plan, Press N to break:");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!input.equals("Y") && !input.equals("N") && !input.equals("y") && !input.equals("n")){
            System.out.println("Input value wrong!");
            System.out.println("Press Y to run by default floor plan, Press N to break:");
            input = sc.nextLine();
        }
        if(input.equals("Y") || input.equals("y")) cleanSweep.doWork();
    }
}

