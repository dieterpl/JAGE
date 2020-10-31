/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI;

import JAGE.supervisor.Supervisor;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainThread extends Application implements Runnable {
    private String[] args;

    public void setArgs(String[] args){
        this.args = args;
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    public void run() {
        Supervisor.getInstance().startEmulator(this.args);
    }
}