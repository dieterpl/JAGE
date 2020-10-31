/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.MainView;

import JAGE.display.FrameBuffer;
import JAGE.display.GPU;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Main Window containing the display rendering the game
 */
public class MainView extends Stage implements EventHandler<WindowEvent> {
    Scene scene;


    public MainView() throws IOException {
        this.setOnCloseRequest(this);
        Parent mainWindow = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        scene = new Scene(mainWindow);
        this.setTitle("Main");
        this.setScene(scene);
        this.createCanvas();
        this.setResizable(false);
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

        new AnimationTimer() {
            long lastTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                try {
                    GPU.getInstance().updateBackgroundTiles();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                for (int x = 0; x < FrameBuffer.width; x++) {
                    for (int y = 0; y < FrameBuffer.height; y++) {
                        try {
                            gc.getPixelWriter().setColor(x, y, FrameBuffer.getInstance().getPixel(x, y));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                String fps = String.valueOf(Math.round(1000000000.0 / (currentNanoTime - lastTime)));
                gc.fillText(fps, 0, 10);
                gc.strokeText(fps, 0, 10);
                lastTime = currentNanoTime;
            }
        }.start();


        return canvas;
    }
}

