/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI;

import JAGE.GUI.MainView.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Independent thread to run the GUI
 */
public class GUIThread extends Application implements Runnable {

    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        MainView mw = new MainView();

    }

    public void run() {
        System.out.println("GUI starting...");
        launch();
    }
}