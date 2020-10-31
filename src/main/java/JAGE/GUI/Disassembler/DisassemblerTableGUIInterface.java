package JAGE.GUI.Disassembler;

import javafx.beans.property.SimpleStringProperty;

public interface DisassemblerTableGUIInterface {
    public String getAddress() throws Exception;

    public String getCMD() throws Exception;

    public String getParameter() throws Exception;

    public SimpleStringProperty getAddressProperty();

    public SimpleStringProperty getCMDProperty() throws Exception;

    public SimpleStringProperty getParameterProperty() throws Exception;

}
