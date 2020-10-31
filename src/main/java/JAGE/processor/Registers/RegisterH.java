/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterH extends Register {
    private static RegisterH r;

    private RegisterH() {

    }

    public static RegisterH getInstance() {
        if (r == null) {
            r = new RegisterH();
        }
        return RegisterH.r;
    }

}
