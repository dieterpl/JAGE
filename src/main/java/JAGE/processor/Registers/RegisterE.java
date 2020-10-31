package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterE extends Register {

    private static RegisterE r;

    private RegisterE() {

    }

    public static RegisterE getInstance() {
        if (r == null) {
            r = new RegisterE();
        }
        return RegisterE.r;
    }


}
