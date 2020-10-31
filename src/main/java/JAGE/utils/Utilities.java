/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.utils;

/**
 * Util functions for bit wise operation and conversion
 */
public class Utilities {


    public static String byteToBitString(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public static void printByte(byte b) {
        System.out.println(String.format("%16X", b));
    }

    public static String byteToHex(byte b) {
        return String.format("%16X", b).trim();
    }

    public static String intToHex(int n) {
        if (n < 0) {
            return "-" + Integer.toHexString(n * -1);
        }
        return Integer.toHexString(n);

    }


    public static int unsetBit(int bit, int target) {
        // Create mask
        int mask = 1 << bit;
        mask = ~mask & 0xff;
        // Set bit
        return target & mask;
    }


    public static int setBit(int bit, int target) {
        // Create mask
        int mask = 1 << bit;
        // Set bit
        return target | mask;
    }

    public static String intToBitString(int n) {
        String s = String.format("%16s", Integer.toBinaryString(n)).replace(' ', '0');
        return s.substring(s.length() - 8, s.length());
    }

    public static int hexToInt(String hex) {
        return Integer.parseInt(hex.trim(), 16);
    }

    public static byte[] hexToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte hexToByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data[0];
    }

    // lowerbyte 0 highbyte 1
    public static byte[] split16BitTo8BitByte(int value) throws Exception {
        if (value < 0 && value > 65535) {
            throw new Exception("Values is not 16 bit.");
        }
        byte[] data = new byte[2];
        data[0] = (byte) (value & 0xFF);
        data[1] = (byte) ((value >> 8) & 0xFF);
        return data;

    }

    // lowerbyte 0 highbyte 1
    public static int mergeByteTo16Bit(int higher, int lower) throws Exception {
        return ((higher & 0xff) << 8) | (lower & 0xff);

    }

}
