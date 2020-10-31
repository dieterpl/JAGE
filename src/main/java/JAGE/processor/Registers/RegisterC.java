/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterC extends Register {
    private static RegisterC r;

    private RegisterC() {
        this.maxValue = 255;
        this.minValue = 0;
    }

    public static RegisterC getInstance() {
        if (r == null) {
            r = new RegisterC();
        }
        return RegisterC.r;
    }


}
