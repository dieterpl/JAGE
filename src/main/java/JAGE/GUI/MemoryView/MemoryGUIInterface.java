package JAGE.GUI.MemoryView;

import javafx.beans.property.SimpleStringProperty;

public interface MemoryGUIInterface {

    public String getAddress() throws Exception;

    public String getValue() throws Exception;

    public SimpleStringProperty addressProperty();

    public SimpleStringProperty valueProperty() throws Exception;

    public SimpleStringProperty valueHProperty() throws Exception;
}
