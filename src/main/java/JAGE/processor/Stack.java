/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor;

import JAGE.processor.Registers.RegisterSP;

/**
 * Modifies the stack pointer and writes the stack to memory
 */
public class Stack {
    private static Stack stack;

    private Stack() {
    }

    public static Stack getInstance() {
        if (stack == null) {
            stack = new Stack();
        }
        return stack;
    }

    public void push(int a) throws Exception {
        if (RegisterSP.getInstance().getValue() > 0x0000 && a <= 0xFFFF) {
            RegisterSP.getInstance().setValue(RegisterSP.getInstance().getValue() - 1);
        } else {
            throw new Exception("Can't push to RAM");
        }
        Memory.getInstance().writeRamValue(RegisterSP.getInstance().getValue(), (byte) a);
    }

    public int pop() throws Exception {
        if (RegisterSP.getInstance().getValue() >= 0xFFFF) {
            return Memory.getInstance().getRamValue(RegisterSP.getInstance().getValue());
        } else {
            int value = Memory.getInstance().getRamValue(RegisterSP.getInstance().getValue());
            RegisterSP.getInstance().setValue(RegisterSP.getInstance().getValue() + 1);
            return value;
        }
    }
}
