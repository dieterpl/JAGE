/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterB extends Register {

    private static RegisterB r;

    private RegisterB() {
        this.maxValue = 255;
        this.minValue = 0;
    }

    public static RegisterB getInstance() {
        if (r == null) {
            r = new RegisterB();
        }
        return RegisterB.r;
    }


}
