package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterA extends Register {

    private static RegisterA r;

    private RegisterA() {

    }

    public static RegisterA getInstance() {
        if (r == null) {
            r = new RegisterA();
        }
        return RegisterA.r;
    }

}
