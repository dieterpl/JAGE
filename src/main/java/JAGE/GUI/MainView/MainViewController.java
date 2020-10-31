/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.MainView;

import JAGE.GUI.Disassembler.DisassemblerWindow;
import JAGE.GUI.MemoryView.MemoryViewWindow;
import JAGE.GUI.SpriteView.SpriteView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBar.useSystemMenuBarProperty().set(true);
    }

    public void openDisassembler(ActionEvent actionEvent) throws IOException {
        DisassemblerWindow dw = new DisassemblerWindow();

    }

    public void openMemoryView(ActionEvent actionEvent) throws IOException {
        MemoryViewWindow dw = new MemoryViewWindow();

    }

    public void openSpriteView(ActionEvent actionEvent) throws IOException {
        SpriteView spriteView = new SpriteView();
    }
}