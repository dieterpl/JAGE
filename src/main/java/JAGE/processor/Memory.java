package JAGE.processor;


import JAGE.utils.Utilities;

public class Memory {

    private static Memory singleton = null;
    byte[] romBank016 = new byte[0x4000];
    byte[] switchableRomBank16 = new byte[0x4000];
    byte[] vRam8 = new byte[0x2000];
    byte[] switchableRamBank8 = new byte[0x2000];
    byte[] internalRam8 = new byte[0x2000];
    byte[] echoInternalRam = new byte[0x1E00];
    byte[] spriteAttributeMem = new byte[0xA0];
    byte[] emptyRam = new byte[0x60];
    byte[] ioMem = new byte[0x4C];
    byte[] emptyRam2 = new byte[0x34];
    byte[] internalRam = new byte[0x80];
    private int ramLength = 0xFFFF;

    private Memory() {

        for (int i = 0; i < ramLength; i++) {
            try {
                this.writeRamValue(i, (byte) 0x0);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public static Memory getInstance() {
        if (singleton == null) {
            singleton = new Memory();
        }
        return singleton;
    }

    public int getMemoryLength() {
        return ramLength;
    }

    public void writeRamValue(int address, byte value) throws Exception {
        if (address < romBank016.length) {
            romBank016[address] = value;
            return;
        }
        int offset = romBank016.length;
        if (address < offset + switchableRomBank16.length) {
            switchableRomBank16[address - offset] = value;
            return;
        }
        offset += switchableRomBank16.length;

        if (address < offset + vRam8.length) {
            vRam8[address - offset] = value;
            return;
        }
        offset += vRam8.length;

        if (address < offset + switchableRamBank8.length) {
            switchableRamBank8[address - offset] = value;
            return;
        }
        offset += switchableRamBank8.length;

        if (address < offset + internalRam8.length) {
            internalRam8[address - offset] = value;
            return;
        }
        offset += internalRam8.length;

        if (address < offset + echoInternalRam.length) {
            echoInternalRam[address - offset] = value;
            return;
        }
        offset += echoInternalRam.length;

        if (address < offset + spriteAttributeMem.length) {
            spriteAttributeMem[address - offset] = value;
            return;
        }
        offset += spriteAttributeMem.length;

        if (address < offset + emptyRam.length) {
            emptyRam[address - offset] = value;
            return;
        }
        offset += emptyRam.length;

        if (address < offset + ioMem.length) {
            ioMem[address - offset] = value;
            return;
        }
        offset += ioMem.length;


        if (address < offset + emptyRam2.length) {
            emptyRam2[address - offset] = value;
            return;
        }
        offset += emptyRam2.length;

        if (address < offset + internalRam.length) {
            internalRam[address - offset] = value;
            return;
        }

        throw new Exception("Ram Address 0x" + Utilities.intToHex(address) + " not found!");

    }

    public byte getRamValue(int address) throws Exception {
        if (address < romBank016.length) {
            return romBank016[address];
        }

        int offset = romBank016.length;
        if (address < offset + switchableRomBank16.length) {
            return switchableRomBank16[address - offset];

        }

        offset += switchableRomBank16.length;
        if (address < offset + vRam8.length) {
            return vRam8[address - offset];
        }
        offset += vRam8.length;

        if (address < offset + switchableRamBank8.length) {
            return switchableRamBank8[address - offset];
        }
        offset += switchableRamBank8.length;

        if (address < offset + internalRam8.length) {
            return internalRam8[address - offset];
        }
        offset += internalRam8.length;

        if (address < offset + echoInternalRam.length) {
            return echoInternalRam[address - offset];
        }
        offset += echoInternalRam.length;

        if (address < offset + spriteAttributeMem.length) {
            return spriteAttributeMem[address - offset];
        }
        offset += spriteAttributeMem.length;

        if (address < offset + emptyRam.length) {
            return emptyRam[address - offset];

        }
        offset += emptyRam.length;

        if (address < offset + ioMem.length) {
            return ioMem[address - offset];
        }
        offset += ioMem.length;


        if (address < offset + emptyRam2.length) {
            return emptyRam2[address - offset];
        }
        offset += emptyRam2.length;

        if (address < offset + internalRam.length) {
            return internalRam[address - offset];
        }

        throw new Exception("Ram Address 0x" + Utilities.intToHex(address) + " not found!");
    }

}
