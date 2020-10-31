package JAGE.GUI.Disassembler;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class DisassemblerWindow extends Stage implements EventHandler<WindowEvent> {
    Scene scene;

    public DisassemblerWindow() throws IOException {
        this.setOnCloseRequest(this);
        Parent Dissasembler = FXMLLoader.load(getClass().getClassLoader().getResource("disassemblerWindow.fxml"));
        scene = new Scene(Dissasembler);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("highlighting.css").toExternalForm());
        this.setTitle("Disassembler");
        this.setScene(scene);
        this.setResizable(true);
        this.show();
    }

    @Override
    public void handle(WindowEvent event) {
        //System.exit(0);
    }


}
