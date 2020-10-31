/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.interfaces;

import JAGE.processor.Registers.RegisterF;
import JAGE.utils.Utilities;

/**
 * Abstract Register that contains the most basic version of a 8-Bit register
 */
public abstract class Register implements Parameter {


    protected int value = 0;
    protected int minValue = 0;
    protected int maxValue = 0xFF;
    private Type type = Type.RegisterType;

    protected int evaluateInput(int value) throws Exception {
        if (value > maxValue || value < minValue)
            throw new Exception("Value in " + this.getClass().getSimpleName() + " is wrong " + value);
        return value;
    }

    protected int evaluateInput(String hex) throws Exception {
        int value = Utilities.hexToInt(hex);
        if (value > maxValue || value < minValue)
            throw new Exception("Value in " + this.getClass().getSimpleName() + " is wrong " + value);
        return value;
    }

    @Override
    public String toString(){

        try {
            return this.getClass().getSimpleName() + "(" + this.getValueHex() + "h)";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void addValue(int value) throws Exception {
        this.value += value;

        if (this.value > maxValue || this.value < minValue)
            RegisterF.getInstance().setCarryFlag(true);
        else
            RegisterF.getInstance().setCarryFlag(false);

        this.value = this.value % (maxValue + 1);

        if (this.value == 0) {
            RegisterF.getInstance().setZeroFlag(true);
        } else {
            RegisterF.getInstance().setZeroFlag(false);
        }
        RegisterF.getInstance().setHalfCarryFlag(isHalfCarry(this.value, value));
        RegisterF.getInstance().setSubtractFlag(false);

    }

    public boolean isHalfCarry(int a, int b) {
        a = a & 0xF;
        b = b & 0xF;
        return (a + b) > 0xF ? true : false;
    }

    public boolean isBorrow(int a, int b) {
        a = a & 0xF;
        b = b & 0xF;
        if (a > 0xF)
            return (a - b) < 0xF ? true : false;
        return false;
    }

    public void dec() throws Exception {
        RegisterF.getInstance().setSubtractFlag(true);
        if (isBorrow(this.value, 1))
            RegisterF.getInstance().setHalfCarryFlag(false);
        else
            RegisterF.getInstance().setHalfCarryFlag(true);
        this.addValue(-1);
        this.setValue(this.getValue() % (maxValue + 1));
        if (this.getValue() == 0) {
            RegisterF.getInstance().setZeroFlag(true);
        } else {
            RegisterF.getInstance().setZeroFlag(false);
        }

    }

    public void inc() throws Exception {

        RegisterF.getInstance().setSubtractFlag(false);
        if (isHalfCarry(this.value, 1))
            RegisterF.getInstance().setHalfCarryFlag(true);
        else
            RegisterF.getInstance().setHalfCarryFlag(false);
        this.addValue(1);
        this.setValue(this.getValue() % (maxValue + 1));
        if (this.getValue() == 0) {
            RegisterF.getInstance().setZeroFlag(true);
        } else {
            RegisterF.getInstance().setZeroFlag(false);
        }
    }

    @Override
    public int getValue() throws Exception {
        return (this.value & maxValue);
    }

    @Override
    public void setValue(int value) throws Exception {
        this.value = evaluateInput(value & maxValue);
    }

    @Override
    public String getValueHex() throws Exception {
        return Utilities.intToHex(this.value & maxValue);
    }

    @Override
    public void setValueHex(String hex) throws Exception {
        this.value = evaluateInput(value & maxValue);
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public void setBit(int bit) throws Exception {
        value = (byte) (value | (1 << bit));
    }

    public void unsetBit(int bit) throws Exception {
        value = (byte) (value & ~(1 << bit));
    }

    public boolean getBit(int pos) throws Exception {
        int bit = ((value >> pos) & 1);
        if (bit == 1) {
            return true;
        }
        return false;
    }


}
