/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;
import JAGE.processor.interfaces.Register16;

public class RegisterBC extends Register16 {
    private static RegisterBC r;

    private RegisterBC() {
        maxValue = 0xFFFF;
    }

    public static RegisterBC getInstance() {
        if (r == null) {
            r = new RegisterBC();
        }
        return RegisterBC.r;
    }


    @Override
    public Register getHigher() {
        return RegisterB.getInstance();
    }

    @Override
    public Register getLower() {
        return RegisterC.getInstance();
    }


}
