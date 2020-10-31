/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.GUITools;

import javafx.beans.property.SimpleStringProperty;

public interface RegisterGUIInterface {

    public String getName();

    public SimpleStringProperty nameProperty();

    public String getValue();

    public SimpleStringProperty valueProperty();


}
