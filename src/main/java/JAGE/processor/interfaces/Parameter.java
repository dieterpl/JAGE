/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */

package JAGE.processor.interfaces;

/**
 * Interface containing a description for assembler command parameters
 */
public interface Parameter {


    @Override
    String toString();

    void addValue(int value) throws Exception;

    int getValue() throws Exception;

    void setValue(int value) throws Exception;

    String getValueHex() throws Exception;

    void setValueHex(String hex) throws Exception;

    Type getType();

    enum Type {
        RegisterType, Address8AndFixedOffsetType, Address16Type, Value8Type, Value16Type, AddressInRegisterCAndFixedOffsetType, Value8SignedType,
        AddressInRegisterBCType, AddressInRegisterDEType, AddressInRegisterHLType, Value8Fixed00HType, Value8Fixed02HType, Value8Fixed04HType, Value8Fixed08HType, Value8Fixed10HType, Value8Fixed20HType, Value8Fixed40HType, Value8Fixed80HType,
        Value8Fixed18HType, Value8Fixed28HType, Value8Fixed30HType, Value8Fixed38HType, SpecialRegisterType,

    }

}
