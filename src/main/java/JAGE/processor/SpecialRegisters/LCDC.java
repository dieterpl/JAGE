package JAGE.processor.SpecialRegisters;

import JAGE.processor.interfaces.SpecialRegister;

public class LCDC extends SpecialRegister {

    // Singleton
    private static LCDC r;
    private final int RAMADDRESS = 0xFF40;

    private LCDC() {
    }

    public static LCDC getInstance() {
        if (r == null) {
            r = new LCDC();
        }
        return LCDC.r;
    }


}
