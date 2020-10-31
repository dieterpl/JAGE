package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;
import JAGE.utils.Utilities;

public class RegisterPC extends Register {
    private static RegisterPC r;
    int value;

    private RegisterPC() {
        maxValue = 0xFFFF;
    }

    public static RegisterPC getInstance() {
        if (r == null) {
            r = new RegisterPC();
        }
        return RegisterPC.r;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void setValue(int value) throws Exception {
        evaluateInput(value);
        this.value = value;
    }

    @Override
    public String getValueHex() {
        return Utilities.intToHex(this.value);
    }

    @Override
    public void setValueHex(String hex) throws Exception {
        this.value = evaluateInput(hex);
    }

    @Override
    public void addValue(int value) {
        this.value += value;
        value = value % (maxValue + 1);
    }
}
