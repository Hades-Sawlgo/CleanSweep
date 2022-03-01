package com.group9.cleansweep;

import java.io.IOException;

import com.group9.cleansweep.controlsystem.CleanSweep;
import com.group9.cleansweep.controlsystem.UnregisteredUI;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		CleanSweep cleanSweep = new CleanSweep();

		UnregisteredUI.unregisteredUI(cleanSweep);

	}
}
