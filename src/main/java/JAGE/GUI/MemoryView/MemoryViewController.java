package JAGE.GUI.MemoryView;

import JAGE.processor.Memory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MemoryViewController implements Initializable {
    List<MemoryRow> memoryData;
    @FXML
    private TableView<MemoryRow> memoryTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillMemoryTable();
        } catch (Exception e) {
            System.err.println("Memory Table not initiable.");
            e.printStackTrace();
        }
    }

    private void fillMemoryTable() throws Exception {
        memoryData = new ArrayList<>();
        for (int i = 0; i < Memory.getInstance().getMemoryLength(); i = i + 8) {
            memoryData.add(new MemoryRow(
                    i,
                    Memory.getInstance().getRamValue(i) & 0x00FF,
                    Memory.getInstance().getRamValue(i + 1) & 0x00FF,
                    Memory.getInstance().getRamValue(i + 2) & 0x00FF,
                    Memory.getInstance().getRamValue(i + 3) & 0x00FF,
                    Memory.getInstance().getRamValue(i + 4) & 0x00FF,
                    Memory.getInstance().getRamValue(i + 5) & 0x00FF,
                    Memory.getInstance().getRamValue(i + 6) & 0x00FF,
                    Memory.getInstance().getRamValue(i + 7) & 0x00FF));
        }
        memoryTable.getItems().setAll(memoryData);
    }

}
