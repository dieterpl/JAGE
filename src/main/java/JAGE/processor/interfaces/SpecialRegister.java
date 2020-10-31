/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.interfaces;

import JAGE.processor.Memory;
import JAGE.utils.Utilities;


/**
 * Special register with values based on ram
 */
public abstract class SpecialRegister extends Register {

    public int RAMADDRESS = 0x0;

    @Override
    public void addValue(int value) throws Exception {
        this.setValue((this.getValue() + (0xFF & value)) & 0xFF);
    }

    @Override
    public int getValue() throws Exception {
        return Memory.getInstance().getRamValue(this.RAMADDRESS);
    }

    @Override
    public void setValue(int value) throws Exception {
        Memory.getInstance().writeRamValue(this.RAMADDRESS, (byte) (value & 0xFF));
    }

    @Override
    public String getValueHex() throws Exception {
        return Utilities.byteToHex((byte) this.getValue());
    }

    @Override
    public void setValueHex(String hex) throws Exception {
        Memory.getInstance().writeRamValue(this.RAMADDRESS, Utilities.hexToByte(hex));
    }

    @Override
    public Type getType() {
        return Type.SpecialRegisterType;
    }

    @Override
    public String toString() {
        try {
            return this.getClass().getSimpleName() + "(" + this.getValueHex() + "h)";
        } catch (Exception exception) {
            return "Error";
        }

    }
}
