package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;
import JAGE.utils.Utilities;

public class RegisterSP extends Register {
    private static RegisterSP r;
    //Default Value
    protected int value = 0xFFFF;

    private RegisterSP() {
        maxValue = 0xFFFF;
    }

    public static RegisterSP getInstance() {
        if (r == null) {
            r = new RegisterSP();
        }
        return RegisterSP.r;
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
}
