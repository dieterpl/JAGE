package JAGE.GUI.MemoryView;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MemoryView extends Stage implements EventHandler<WindowEvent> {
    public static MemoryView.EncodingType encoding = EncodingType.HEX;
    Scene scene;


    public MemoryView() throws IOException {
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
