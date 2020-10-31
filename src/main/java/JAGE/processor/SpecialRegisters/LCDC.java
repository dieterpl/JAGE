/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor.SpecialRegisters;

import JAGE.processor.interfaces.SpecialRegister;

/**
 * LCD Control register
 */
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
