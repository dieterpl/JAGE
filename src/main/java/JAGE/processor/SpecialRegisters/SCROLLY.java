package JAGE.processor.SpecialRegisters;

import JAGE.processor.interfaces.SpecialRegister;

public class SCROLLY extends SpecialRegister {


    private static SCROLLY r;

    private SCROLLY() {
        this.RAMADDRESS = 0xFF42;
    }

    public static SCROLLY getInstance() {

        if (r == null) {
            r = new SCROLLY();
        }
        return SCROLLY.r;
    }

}

