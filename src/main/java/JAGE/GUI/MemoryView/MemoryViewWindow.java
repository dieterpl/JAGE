/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.MemoryView;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


/**
 * Memory View Window to see the current state of the emulator memory
 */
public class MemoryViewWindow extends Stage implements EventHandler<WindowEvent> {
    public static MemoryViewWindow.EncodingType encoding = EncodingType.HEX;
    Scene scene;


    public MemoryViewWindow() throws IOException {
        this.setOnCloseRequest(this);
        Parent debugWindow = FXMLLoader.load(getClass().getClassLoader().getResource("memoryView.fxml"));

        scene = new Scene(debugWindow);
        this.setTitle("Memory");
        this.setScene(scene);
        this.setResizable(true);
        this.show();
    }

    @Override
    public void handle(WindowEvent event) {
        //System.exit(0);
    }

    public enum EncodingType {
        BINARY, HEX, INT
    }


}
