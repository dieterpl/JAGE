/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;
import JAGE.processor.interfaces.Register16;

public class RegisterDE extends Register16 {
    private static RegisterDE r;


    private RegisterDE() {
        maxValue = 0xFFFF;

    }

    public static RegisterDE getInstance() {
        if (r == null) {
            r = new RegisterDE();
        }
        return RegisterDE.r;
    }

    @Override
    public Register getHigher() {
        return RegisterD.getInstance();
    }

    @Override
    public Register getLower() {
        return RegisterE.getInstance();
    }

}
