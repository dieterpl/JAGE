package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;
import JAGE.processor.interfaces.Register16;

public class RegisterAF extends Register16 {
    private static RegisterAF r;


    private RegisterAF() {
        maxValue = 0xFFFF;
    }

    public static RegisterAF getInstance() {
        if (r == null) {
            r = new RegisterAF();
        }
        return RegisterAF.r;
    }


    @Override
    public Register getHigher() {
        return RegisterA.getInstance();
    }

    @Override
    public Register getLower() {
        return RegisterF.getInstance();
    }


}
