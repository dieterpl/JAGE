/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.Disassembler;

import javafx.beans.property.SimpleStringProperty;


public class DisassemblerTableGUI implements DisassemblerTableGUIInterface {
    @Override
    public String getAddress() throws Exception {
        return "a";
    }

    @Override
    public String getCMD() throws Exception {
        return "b";
    }

    @Override
    public String getParameter() throws Exception {
        return "c";
    }

    @Override
    public SimpleStringProperty getAddressProperty() {
        return new SimpleStringProperty("d");
    }

    @Override
    public SimpleStringProperty getCMDProperty() throws Exception {
        return new SimpleStringProperty("");
    }

    @Override
    public SimpleStringProperty getParameterProperty() throws Exception {
        return new SimpleStringProperty("");
    }
}
