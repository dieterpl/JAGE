package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterF extends Register {

    private static RegisterF r;

    private RegisterF() {
        this.maxValue = 0xF0;
    }

    public static RegisterF getInstance() {
        if (r == null) {
            r = new RegisterF();
        }
        return RegisterF.r;
    }

    public boolean getZeroFlag() throws Exception {
        return getBit(7);
    }

    public void setZeroFlag(boolean b) throws Exception {
        if (b)
            setBit(7);
        else
            unsetBit(7);
    }

    public boolean getSubtractFlag() throws Exception {
        return getBit(6);
    }

    public void setSubtractFlag(boolean b) throws Exception {
        if (b)
            setBit(6);
        else
            unsetBit(6);
    }

    public boolean getHalfCarryFlag() throws Exception {
        return getBit(5);
    }

    public void setHalfCarryFlag(boolean b) throws Exception {
        if (b)
            setBit(5);
        else
            unsetBit(5);
    }

    public boolean getCarryFlag() throws Exception {
        return getBit(4);
    }

    public void setCarryFlag(boolean b) throws Exception {
        if (b)
            setBit(4);
        else
            unsetBit(4);
    }


    public void resetAllFlags() throws Exception {
        this.setValue(this.getValue() & 0x00);
    }



    @Override
    public String toString() {
        try {
            return this.getClass().getSimpleName() + "(" + this.getValueHex() + "h)" + " " + "C:" + this.getCarryFlag() + " H:" + this.getHalfCarryFlag() + " Z:" + this.getZeroFlag() + " N:" + this.getSubtractFlag();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
