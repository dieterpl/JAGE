package JAGE.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIThread extends Application implements Runnable {

    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        //DebugWindow dw = new DebugWindow();
        MainWindow mw = new MainWindow();

    }

    public void run() {
        System.out.println("GUI starting...");
        launch();
    }
}