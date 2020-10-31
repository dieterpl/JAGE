/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.interfaces;

import JAGE.processor.Registers.RegisterF;
import JAGE.utils.Utilities;

/**
 * 16 bit register extension of 8 bit register
 */
public abstract class Register16 extends Register {


    public abstract Register getHigher();

    public abstract Register getLower();


    @Override
    public int getValue() throws Exception {
        return (getHigher().getValue() << 8 | getLower().getValue()) & maxValue;
    }

    @Override
    public void setValue(int value) throws Exception {
        evaluateInput(value);
        byte[] data = Utilities.split16BitTo8BitByte(value);
        getHigher().setValue(data[1] & 0xFF);
        getLower().setValue(data[0] & 0xFF);
    }

    @Override
    public String getValueHex() throws Exception {
        return Utilities.intToHex((getHigher().getValue() << 8 | getLower().getValue()) & 0xFFFF);
    }

    @Override
    public void setValueHex(String hex) throws Exception {
        int value = evaluateInput(hex);
        setValue(value);
    }


    @Override
    public void addValue(int value) throws Exception {
        if (value < 0) {
            if (getLower().getValue() < value) {
                getHigher().addValue(-1);
            }
            getLower().setValue((getLower().getValue() + value) & 0xFF);

            return;
        }
        // Positive
        if (getLower().getValue() + (value & 0xFF) > 0xFF) {
            getHigher().addValue(1);
            RegisterF.getInstance().setHalfCarryFlag(true);
        } else {
            RegisterF.getInstance().setHalfCarryFlag(false);
        }
        getLower().addValue(value & 0xFF);
        if (getHigher().getValue() + ((value >> 8) & 0xFF) > 0xFF)
            RegisterF.getInstance().setCarryFlag(true);
        else
            RegisterF.getInstance().setCarryFlag(false);
        getHigher().addValue(value >> 8 & 0xFF);

    }
}
