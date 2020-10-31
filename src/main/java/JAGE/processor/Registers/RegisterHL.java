package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;
import JAGE.processor.interfaces.Register16;

public class RegisterHL extends Register16 {
    private static RegisterHL r;

    private RegisterHL() {
        maxValue = 0xFFFF;

    }

    public static RegisterHL getInstance() {
        if (r == null) {
            r = new RegisterHL();
        }
        return RegisterHL.r;
    }


    @Override
    public Register getHigher() {
        return RegisterH.getInstance();
    }

    @Override
    public Register getLower() {
        return RegisterL.getInstance();
    }


}
