/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI;

import org.apache.log4j.xml.DOMConfigurator;

import java.util.concurrent.TimeUnit;

/**
 * Main entry point for the emulator
 */
public class Main {


    public static void main(String[] args) throws Exception {
        DOMConfigurator.configureAndWatch("log4j.xml", 60 * 1000);
        // Core thread for calculating the Gameboy
        Thread mainThread = new Thread(new MainThread());
        mainThread.start();

        // Thread for the interface
        Thread guiThread = new Thread(new GUIThread());
        guiThread.start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
