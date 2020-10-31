package JAGE.processor.Registers;

import JAGE.processor.interfaces.Register;

public class RegisterD extends Register {
    private static RegisterD r;

    private RegisterD() {
        this.maxValue = 255;
        this.minValue = 0;
    }

    public static RegisterD getInstance() {
        if (r == null) {
            r = new RegisterD();
        }
        return RegisterD.r;
    }
}
