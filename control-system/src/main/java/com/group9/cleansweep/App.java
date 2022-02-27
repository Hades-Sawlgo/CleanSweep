package com.group9.cleansweep;

import com.group9.cleansweep.controlsystem.CleanSweep;
import com.group9.cleansweep.controlsystem.UnregisteredUI;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		CleanSweep cleanSweep = new CleanSweep();

		UnregisteredUI.unregisteredUI(cleanSweep);

	}
}
