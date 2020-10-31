package JAGE.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class DebugWindow extends Stage implements EventHandler<WindowEvent> {
    Scene scene;

    DebugWindow() throws IOException {
        this.setOnCloseRequest(this);
        Parent debugWindow = FXMLLoader.load(getClass().getClassLoader().getResource("debugWindow.fxml"));
        scene = new Scene(debugWindow);
        this.setTitle("Debug");
        this.setScene(scene);
        this.setResizable(true);
        this.show();
    }

    @Override
    public void handle(WindowEvent event) {
        //System.exit(0);
    }


}
