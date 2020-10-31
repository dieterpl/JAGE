/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.SpriteView;

import JAGE.processor.Memory;
import JAGE.utils.Utilities;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Sprite View to look at a graphical representation of the sprite memory
 */
public class SpriteView extends Stage implements EventHandler<WindowEvent> {
    Scene scene;


    public SpriteView() throws IOException {
        this.setOnCloseRequest(this);
        Parent mainWindow = FXMLLoader.load(getClass().getClassLoader().getResource("spriteView.fxml"));
        scene = new Scene(mainWindow);
        this.setTitle("SpriteView");
        this.setScene(scene);
        this.createCanvas();
        this.setResizable(true);
        this.show();
    }

    @Override
    public void handle(WindowEvent event) {
        System.exit(0);
    }

    private Canvas createCanvas() {
        Canvas canvas = (Canvas) scene.lookup("#canvas");
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int spirtePosX = 0;
        int spirtePosY = 0;
        try {
            for (int address = 0x8000; address < 0x97FF; address += 16) {
                for (int offset = 0; offset < 16; offset += 2) {
                    int value1 = Memory.getInstance().getRamValue(address + offset);
                    int value2 = Memory.getInstance().getRamValue(address + offset + 1);
                    String bit1 = Utilities.intToBitString(value1);
                    String bit2 = Utilities.intToBitString(value2);
                    for (int bit = 0; bit < 8; bit++) {
                        Color color = new Color(0, 0, 0, 1);
                        if (bit1.charAt(bit) == '0' && bit2.charAt(bit) == '0') {
                            color = new Color(1, 1, 0, 1);
                        }
                        if (bit1.charAt(bit) == '1' && bit2.charAt(bit) == '0') {
                            color = new Color(1, 0, 0, 1);
                        }
                        if (bit1.charAt(bit) == '0' && bit2.charAt(bit) == '1') {
                            color = new Color(0, 1, 0, 1);
                        }
                        if (bit1.charAt(bit) == '1' && bit2.charAt(bit) == '1') {
                            color = new Color(0, 0, 1, 1);
                        }
                        gc.getPixelWriter().setColor(bit + spirtePosX * 8, spirtePosY * 8 + offset / 2, color);
                    }
                }
                spirtePosX += 1;
                if (spirtePosX > 15) {
                    spirtePosY += 1;
                    spirtePosX = 0;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return canvas;
    }
}
