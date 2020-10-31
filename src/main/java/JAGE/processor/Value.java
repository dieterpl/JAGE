/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor;

import JAGE.processor.interfaces.Parameter;
import JAGE.utils.Utilities;

/**
 * Value parameter for asm commands
 */
public class Value implements Parameter {

    Type type;
    int value;

    Value(Type t) {
        type = t;
    }

    Value(Type t, int value) {
        type = t;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.type + "(" + this.getValueHex().toUpperCase() + "h)";
    }

    @Override
    public void addValue(int value) throws Exception {
        switch (type) {
            case Value8Type:
                this.value = (this.value + value) & 0xFF;
                return;
            case Value8SignedType:
                this.value = (this.value + value) & 0xFF;
                return;
            case Value16Type:
                this.value = (this.value + value) % 65536 & 0xFFFF;
                return;
            case Value8Fixed00HType:
                this.value = 0;
                return;
            case Value8Fixed02HType:
                this.value = 0x2;
                return;
            case Value8Fixed04HType:
                this.value = 0x4;
                return;
            case Value8Fixed08HType:
                this.value = 0x8;
                return;
            case Value8Fixed10HType:
                this.value = 0x10;
                return;
            case Value8Fixed18HType:
                this.value = 0x18;
                return;
            case Value8Fixed20HType:
                this.value = 0x20;
                return;
            case Value8Fixed28HType:
                this.value = 0x28;
                return;
            case Value8Fixed30HType:
                this.value = 0x30;
                return;
            case Value8Fixed38HType:
                this.value = 0x38;
                return;
            case Value8Fixed40HType:
                this.value = 0x40;
                return;
            case Value8Fixed80HType:
                this.value = 0x80;
                return;
        }
        throw new Exception("Unknown Type in Value!");
    }

    @Override
    public int getValue() throws Exception {
        switch (type) {
            case Value8Type:
                return this.value & 0xFF;
            case Value8SignedType:
                return this.value;
            case Value16Type:
                return this.value & 0xFFFF;
            case Value8Fixed00HType:
                return 0x00;
            case Value8Fixed02HType:
                return 0x02;
            case Value8Fixed04HType:
                return 0x04;
            case Value8Fixed08HType:
                return 0x08;
            case Value8Fixed10HType:
                return 0x10;
            case Value8Fixed18HType:
                return 0x18;
            case Value8Fixed20HType:
                return 0x20;
            case Value8Fixed28HType:
                return 0x28;
            case Value8Fixed30HType:
                return 0x30;
            case Value8Fixed38HType:
                return 0x38;
            case Value8Fixed40HType:
                return 0x02;
            case Value8Fixed80HType:
                return 0x80;
        }
        throw new Exception("Unknown Type in Value!");
    }

    @Override
    public void setValue(int value) throws Exception {
        switch (type) {
            case Value8Type:
                this.value = value & 0xFF;
                return;
            case Value8SignedType:
                this.value = value & 0xFF;
                if (((this.value >> 7) & 1) == 1) {
                    this.value = -128 + (this.value & 0x7F);
                } else {
                    this.value = this.value & 0x7F;
                }
                return;
            case Value16Type:
                this.value = value & 0xFFFF;
                return;
            case Value8Fixed00HType:
                this.value = 0;
                return;
            case Value8Fixed02HType:
                this.value = 0x2;
                return;
            case Value8Fixed04HType:
                this.value = 0x4;
                return;
            case Value8Fixed08HType:
                this.value = 0x8;
                return;
            case Value8Fixed10HType:
                this.value = 0x10;
                return;
            case Value8Fixed18HType:
                this.value = 0x18;
                return;
            case Value8Fixed20HType:
                this.value = 0x20;
                return;
            case Value8Fixed28HType:
                this.value = 0x28;
                return;
            case Value8Fixed30HType:
                this.value = 0x30;
                return;
            case Value8Fixed38HType:
                this.value = 0x38;
                return;
            case Value8Fixed40HType:
                this.value = 0x40;
                return;
            case Value8Fixed80HType:
                this.value = 0x80;
                return;
        }
        throw new Exception("Unknown Type in Value!");
    }

    @Override
    public String getValueHex() {
        try {
            return Utilities.intToHex(getValue());
        } catch (Exception e) {
            System.err.println("Unkown Type in Value");
        }
        return "0";
    }

    @Override
    public void setValueHex(String hex) throws Exception {
        this.setValue(Utilities.hexToInt(hex));
    }

    @Override
    public Type getType() {
        return type;
    }
}


