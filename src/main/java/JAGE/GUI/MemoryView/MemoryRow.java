/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.GUI.MemoryView;

import JAGE.utils.Utilities;
import javafx.beans.property.SimpleStringProperty;


public class MemoryRow {
    private final SimpleStringProperty index = new SimpleStringProperty("0");
    private final SimpleStringProperty off0 = new SimpleStringProperty("0");

    private final SimpleStringProperty off1 = new SimpleStringProperty("0");
    private final SimpleStringProperty off2 = new SimpleStringProperty("0");
    private final SimpleStringProperty off3 = new SimpleStringProperty("0");
    private final SimpleStringProperty off4 = new SimpleStringProperty("0");
    private final SimpleStringProperty off5 = new SimpleStringProperty("0");
    private final SimpleStringProperty off6 = new SimpleStringProperty("0");
    private final SimpleStringProperty off7 = new SimpleStringProperty("0");
    private final SimpleStringProperty text = new SimpleStringProperty("");


    public MemoryRow(int index, int off0, int off1, int off2, int off3, int off4, int off5, int off6, int off7) {
        switch (MemoryViewWindow.encoding) {
            case BINARY:
                setOff0(Utilities.intToBitString(off0));
                setOff1(Utilities.intToBitString(off1));
                setOff2(Utilities.intToBitString(off2));
                setOff3(Utilities.intToBitString(off3));
                setOff4(Utilities.intToBitString(off4));
                setOff5(Utilities.intToBitString(off5));
                setOff6(Utilities.intToBitString(off6));
                setOff7(Utilities.intToBitString(off7));
                break;
            case HEX:
                setOff0(Utilities.intToHex(off0));
                setOff1(Utilities.intToHex(off1));
                setOff2(Utilities.intToHex(off2));
                setOff3(Utilities.intToHex(off3));
                setOff4(Utilities.intToHex(off4));
                setOff5(Utilities.intToHex(off5));
                setOff6(Utilities.intToHex(off6));
                setOff7(Utilities.intToHex(off7));
                break;
            case INT:
                setOff0(String.valueOf(off0));
                setOff1(String.valueOf(off1));
                setOff2(String.valueOf(off2));
                setOff3(String.valueOf(off3));
                setOff4(String.valueOf(off4));
                setOff5(String.valueOf(off5));
                setOff6(String.valueOf(off6));
                setOff7(String.valueOf(off7));
                break;
        }
        setIndex(Utilities.intToHex(index));
        setText(
                Character.toString((char) ((byte) off0)) +
                        Character.toString((char) ((byte) off1)) +
                        Character.toString((char) ((byte) off2)) +
                        Character.toString((char) ((byte) off3)) +
                        Character.toString((char) ((byte) off4)) +
                        Character.toString((char) ((byte) off5)) +
                        Character.toString((char) ((byte) off6)) +
                        Character.toString((char) ((byte) off7)));
    }

    public String getIndex() {
        return index.get();
    }

    public void setIndex(String index) {
        this.index.set(index);
    }

    public SimpleStringProperty indexProperty() {
        return index;
    }

    public String getOff0() {
        return off0.get();
    }

    public void setOff0(String off0) {
        this.off0.set(off0);
    }

    public SimpleStringProperty off0Property() {
        return off0;
    }

    public String getOff1() {
        return off1.get();
    }

    public void setOff1(String off1) {
        this.off1.set(off1);
    }

    public SimpleStringProperty off1Property() {
        return off1;
    }

    public String getOff2() {
        return off2.get();
    }

    public void setOff2(String off2) {
        this.off2.set(off2);
    }

    public SimpleStringProperty off2Property() {
        return off2;
    }

    public String getOff3() {
        return off3.get();
    }

    public void setOff3(String off3) {
        this.off3.set(off3);
    }

    public SimpleStringProperty off3Property() {
        return off3;
    }

    public String getOff4() {
        return off4.get();
    }

    public void setOff4(String off4) {
        this.off4.set(off4);
    }

    public SimpleStringProperty off4Property() {
        return off4;
    }

    public String getOff5() {
        return off5.get();
    }

    public void setOff5(String off5) {
        this.off5.set(off5);
    }

    public SimpleStringProperty off5Property() {
        return off5;
    }

    public String getOff6() {
        return off6.get();
    }

    public void setOff6(String off6) {
        this.off6.set(off6);
    }

    public SimpleStringProperty off6Property() {
        return off6;
    }

    public String getOff7() {
        return off7.get();
    }

    public void setOff7(String off7) {
        this.off7.set(off7);
    }

    public SimpleStringProperty off7Property() {
        return off7;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public SimpleStringProperty textProperty() {
        return text;
    }


}