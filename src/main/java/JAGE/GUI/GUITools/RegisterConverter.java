/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.GUITools;

import JAGE.processor.interfaces.Register;
import javafx.beans.property.SimpleStringProperty;

public class RegisterConverter implements RegisterGUIInterface {
    private Register r;

    public RegisterConverter(Register r) {
        this.r = r;
    }

    @Override
    public String getName() {
        return r.getClass().getSimpleName();
    }

    @Override
    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(r.getClass().getSimpleName());
    }

    @Override
    public String getValue() {
        return r.toString();
    }

    @Override
    public SimpleStringProperty valueProperty() {
        return new SimpleStringProperty(r.toString());
    }
}
