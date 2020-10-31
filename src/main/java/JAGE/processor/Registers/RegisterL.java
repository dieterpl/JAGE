/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterL extends Register {
    private static RegisterL r;

    private RegisterL() {

    }

    public static RegisterL getInstance() {
        if (r == null) {
            r = new RegisterL();
        }
        return RegisterL.r;
    }

}
