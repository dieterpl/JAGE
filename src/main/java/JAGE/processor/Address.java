/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor;

import JAGE.processor.Registers.RegisterBC;
import JAGE.processor.Registers.RegisterC;
import JAGE.processor.Registers.RegisterDE;
import JAGE.processor.Registers.RegisterHL;
import JAGE.processor.interfaces.Parameter;
import JAGE.utils.Utilities;

/**
 * Address parameter for assembler commands
 */
public class Address implements Parameter {

    int value = 0;
    Type type;

    public Address(Type t) {
        type = t;
    }

    public Address(Type t, int value) {
        type = t;
        this.value = value;
    }

    @Override
    public String toString() {
        try {
            switch (this.type) {
                case Address8AndFixedOffsetType:
                    return this.type + "(" + Utilities.intToHex(this.getValue() & 0x00FF) + "h," + Utilities.intToHex((value & 0xFF) + 0xFF00) + "h)";
                case Address16Type:
                    return this.type + "(" + Utilities.intToHex(this.getValue() & 0xFFFF) + "h," + Utilities.intToHex(value & 0xFFFF) + "h)";
                case AddressInRegisterHLType:
                    return this.type + "(" + Utilities.intToHex(this.getValue() & 0x00FF) + "h," + Utilities.intToHex(RegisterHL.getInstance().getValue() & 0xFFFF) + "h)";
                case AddressInRegisterBCType:
                    return this.type + "(" + Utilities.intToHex(this.getValue() & 0x00FF) + "h," + Utilities.intToHex(RegisterBC.getInstance().getValue() & 0xFFFF) + "h)";
                case AddressInRegisterDEType:
                    return this.type + "(" + Utilities.intToHex(this.getValue() & 0x00FF) + "h," + Utilities.intToHex(RegisterDE.getInstance().getValue() & 0xFFFF) + "h)";
                case AddressInRegisterCAndFixedOffsetType:
                    return this.type + "(" + this.getValueHex().toUpperCase() + "h," + Utilities.intToHex((RegisterC.getInstance().getValue() & 0xFF) + 0xFF00) + "h)";
            }
            throw new Exception("Unknown Type in Address!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    @Override
    public void addValue(int value) throws Exception {
        switch (this.type) {
            case Address8AndFixedOffsetType:
                Memory.getInstance().writeRamValue((value & 0xFF) + 0xFF00, (byte) ((value + this.getValue()) % 256));
                return;
            case Address16Type:
                Memory.getInstance().writeRamValue(value & 0xFFFF, (byte) ((value + this.getValue()) % 256));
                return;
            case AddressInRegisterHLType:
                Memory.getInstance().writeRamValue(RegisterHL.getInstance().getValue() & 0xFFFF, (byte) ((value + this.getValue()) % 256));
                return;
            case AddressInRegisterBCType:
                Memory.getInstance().writeRamValue(RegisterBC.getInstance().getValue() & 0xFFFF, (byte) ((value + this.getValue()) % 256));
                return;
            case AddressInRegisterDEType:
                Memory.getInstance().writeRamValue(RegisterDE.getInstance().getValue() & 0xFFFF, (byte) ((value + this.getValue()) % 256));
                return;
            case AddressInRegisterCAndFixedOffsetType:
                Memory.getInstance().writeRamValue((RegisterC.getInstance().getValue() & 0xFF) + 0xFF00, (byte) ((value + this.getValue()) % 256));
                return;
        }
        throw new Exception("Unknown Type in Address!");
    }

    @Override
    public int getValue() throws Exception {
        switch (this.type) {
            case Address8AndFixedOffsetType:
                return Memory.getInstance().getRamValue((value & 0xFF) + 0xFF00) & 0xFFFF;
            case Address16Type:
                return Memory.getInstance().getRamValue(value & 0xFFFF) & 0xFFFF;
            case AddressInRegisterHLType:
                return Memory.getInstance().getRamValue(RegisterHL.getInstance().getValue() & 0xFFFF) & 0xFFFF;
            case AddressInRegisterBCType:
                return Memory.getInstance().getRamValue(RegisterBC.getInstance().getValue() & 0xFFFF) & 0xFFFF;
            case AddressInRegisterDEType:
                return Memory.getInstance().getRamValue(RegisterDE.getInstance().getValue() & 0xFFFF) & 0xFFFF;
            case AddressInRegisterCAndFixedOffsetType:
                return Memory.getInstance().getRamValue((RegisterC.getInstance().getValue() & 0xFF) + 0xFF00) & 0xFFFF;
        }
        throw new Exception("Unknown Type in Address!");
    }

    @Override
    public void setValue(int value) throws Exception {
        switch (this.type) {
            case Address8AndFixedOffsetType:
                Memory.getInstance().writeRamValue((this.value & 0xFF) + 0xFF00, (byte) (value & 0xFF));
                return;
            case Address16Type:
                Memory.getInstance().writeRamValue(this.value & 0xFFFF, (byte) value);
                return;
            case AddressInRegisterHLType:
                Memory.getInstance().writeRamValue(RegisterHL.getInstance().getValue() & 0xFFFF, (byte) value);
                return;
            case AddressInRegisterBCType:
                Memory.getInstance().writeRamValue(RegisterBC.getInstance().getValue() & 0xFFFF, (byte) value);
                return;
            case AddressInRegisterDEType:
                Memory.getInstance().writeRamValue(RegisterDE.getInstance().getValue() & 0xFFFF, (byte) value);
                return;
            case AddressInRegisterCAndFixedOffsetType:
                Memory.getInstance().writeRamValue((RegisterC.getInstance().getValue() & 0xFF) + 0xFF00, (byte) value);
                return;
        }
        throw new Exception("Unknown Type in Address!");
    }

    @Override
    public String getValueHex() throws Exception {
        return Utilities.intToHex(this.getValue());
    }

    @Override
    public void setValueHex(String hex) throws Exception {
        this.setValue(Utilities.hexToInt(hex));
    }

    @Override
    public Type getType() {
        return type;
    }

    public int getAddress() throws Exception {
        switch (this.type) {
            case Address8AndFixedOffsetType:
                return (value & 0xFF) + 0xFF00;
            case Address16Type:
                return value & 0xFFFF;
            case AddressInRegisterHLType:
                return RegisterHL.getInstance().getValue() & 0xFFFF;
            case AddressInRegisterBCType:
                return RegisterBC.getInstance().getValue() & 0xFFFF;
            case AddressInRegisterDEType:
                return RegisterDE.getInstance().getValue() & 0xFFFF;
            case AddressInRegisterCAndFixedOffsetType:
                return RegisterC.getInstance().getValue() & 0xFF + 0xFF00;
        }
        throw new Exception("Unknown Type in Address!");

    }

    public void setAddress(int value) {
        this.value = value;
    }

}
