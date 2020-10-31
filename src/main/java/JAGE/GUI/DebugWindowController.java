package JAGE.GUI;

import JAGE.GUI.MemoryView.MemoryGUIInterface;
import JAGE.processor.Registers.*;
import JAGE.supervisor.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DebugWindowController implements Initializable {

    @FXML
    public VBox vbox;
    @FXML
    public SplitPane pane;
    @FXML
    private TableView<RegisterConverter> registerTable;
    @FXML
    private TableColumn<RegisterConverter, String> registerCol;
    @FXML
    private TableColumn<RegisterConverter, String> valueCol;
    @FXML
    private TableView<MemoryGUIInterface> dataTable;
    @FXML
    private TableColumn<MemoryGUIInterface, String> addressCol;
    @FXML
    private TableColumn<MemoryGUIInterface, String> ramValueCol;
    @FXML
    private TableColumn<MemoryGUIInterface, String> ramValueHCol;
    public DebugWindowController() {
    }

    public void fillRegisterTable() {
        registerCol.setCellValueFactory(new PropertyValueFactory<RegisterConverter, String>("name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<RegisterConverter, String>("value"));
        registerTable.getItems().setAll(getRegisterItemsToAdd());
        addressCol.setCellValueFactory(new PropertyValueFactory<MemoryGUIInterface, String>("address"));
        ramValueCol.setCellValueFactory(new PropertyValueFactory<MemoryGUIInterface, String>("value"));
        ramValueHCol.setCellValueFactory(new PropertyValueFactory<MemoryGUIInterface, String>("valueH"));

    }

    private List<RegisterConverter> getRegisterItemsToAdd() {
        List<RegisterConverter> registerData = new ArrayList<>();
        registerData.add(new RegisterConverter(RegisterA.getInstance()));
        registerData.add(new RegisterConverter(RegisterB.getInstance()));
        registerData.add(new RegisterConverter(RegisterC.getInstance()));
        registerData.add(new RegisterConverter(RegisterD.getInstance()));
        registerData.add(new RegisterConverter(RegisterE.getInstance()));
        registerData.add(new RegisterConverter(RegisterF.getInstance()));
        registerData.add(new RegisterConverter(RegisterH.getInstance()));
        registerData.add(new RegisterConverter(RegisterL.getInstance()));
        registerData.add(new RegisterConverter(RegisterAF.getInstance()));
        registerData.add(new RegisterConverter(RegisterBC.getInstance()));
        registerData.add(new RegisterConverter(RegisterDE.getInstance()));
        registerData.add(new RegisterConverter(RegisterHL.getInstance()));
        registerData.add(new RegisterConverter(RegisterPC.getInstance()));
        registerData.add(new RegisterConverter(RegisterSP.getInstance()));
        return registerData;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.prefWidthProperty().bind(vbox.widthProperty());
        pane.prefHeightProperty().bind(vbox.heightProperty().subtract(40.0));

        fillRegisterTable();
    }

    @FXML
    private void update() {
        dataTable.requestFocus();
        dataTable.getSelectionModel().select(Integer.parseInt(RegisterPC.getInstance().getValueHex(), 16));
        dataTable.focusModelProperty().get().focus(Integer.parseInt(RegisterPC.getInstance().getValueHex(), 16));
        dataTable.scrollTo(Integer.parseInt(RegisterPC.getInstance().getValueHex(), 16) - 10);
        Supervisor.getInstance().startEmulator();
    }

}
