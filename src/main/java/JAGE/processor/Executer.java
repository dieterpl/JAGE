package JAGE.processor;

import JAGE.processor.Interrupts.InterruptFlags;
import JAGE.processor.Registers.RegisterA;
import JAGE.processor.Registers.RegisterF;
import JAGE.processor.Registers.RegisterHL;
import JAGE.processor.Registers.RegisterPC;
import JAGE.processor.interfaces.Parameter;
import JAGE.processor.interfaces.Register;
import JAGE.utils.Utilities;
import org.apache.log4j.Logger;

import static JAGE.utils.Utilities.*;

public class Executer {

    private static Executer executer;
    private static Logger logger = Logger.getRootLogger();

    private Executer() {
    }

    public static Executer getInstance() {
        if (executer == null) {
            executer = new Executer();
        }
        return executer;
    }

    public boolean exec(Instruction i) throws Exception {

        switch (i.cmd) {
            case "NOP":
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "LD":
                ld(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "LDIR":
                ldir(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "LDIL":
                ldil(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "LDDL":
                lddl(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "ADD":
                add(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "INC":
                inc(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "DEC":
                dec(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "ADC":
                adc(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "JP":
                jp(i);
                break;
            case "JRNZ":
                jrnz(i);
                break;
            case "JRZ":
                jrz(i);
                break;
            case "DI":
                disableInterrupts(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "LDH":
                ldh(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "CALL":
                call(i);
                break;
            case "CALLNZ":
                callnz(i);
                break;
            case "RET":
                ret(i);
                break;
            case "RETNC":
                retnc(i);
                break;
            case "RETC":
                retc(i);
                break;
            case "RETZ":
                retz(i);
                break;
            case "JR":
                jr(i);
                break;
            case "JRNC":
                jrnc(i);
                break;
            case "JRC":
                jrc(i);
                break;
            case "JPNZ":
                jpnz(i);
                break;
            case "SUB":
                sub(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "PUSH":
                push(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "POP":
                pop(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "RST":
                rst(i);
                break;
            case "OR":
                or(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "AND":
                and(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "XOR":
                xor(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "CP":
                cp(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "SRL":
                srl(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "RR":
                rr(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "RRA":
                rra(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "DAA":
                daa(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "CPL":
                cpl(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            case "SWAP":
                swap(i);
                RegisterPC.getInstance().addValue(i.instructionLength);
                break;
            default:
                throw new Exception("CMD  not found: " + i);
        }
        return true;
    }


    private void swap(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (i.args[0].getType() == Parameter.Type.RegisterType) {

                i.args[0].setValue((((i.args[0].getValue() << 4) & 0xF0) | ((i.args[0].getValue() >> 4) & 0x0F)) & 0xFF);
                return;
            }
            //if (i.args[0].getType()== Parameter.Type.AddressInRegisterHLType) {
            //}
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void cpl(Instruction i) throws Exception {
        if (i.args.length == 0) {
            RegisterF.getInstance().setSubtractFlag(true);
            RegisterF.getInstance().setHalfCarryFlag(true);
            //System.out.println(RegisterA.getInstance().toString());
            RegisterA.getInstance().setValue((~RegisterA.getInstance().getValue()) & 0xFF);
            //System.out.println(RegisterA.getInstance().toString());
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void daa(Instruction i) throws Exception {
        // https://ehaskins.com/2018-01-30%20Z80%20DAA/
        //logger.debug(RegisterF.getInstance().toString());

        //logger.debug("DAA START" + RegisterA.getInstance().getValue());


        boolean half_carry = RegisterF.getInstance().getHalfCarryFlag();
        boolean carry = RegisterF.getInstance().getCarryFlag();
        boolean sub = RegisterF.getInstance().getSubtractFlag();
        int value = RegisterA.getInstance().getValue() & 0xFF;
        int corr = 0;
        if (carry)
            corr |= 0x60;

        if (half_carry)
            corr |= 0x06;

        if (sub)
            value = value - corr;
        else {
            if ((value & 0x0F) > 0x09)
                corr |= 0x06;

            if (value > 0x99)
                corr |= 0x60;

            value = value + corr;
        }

        value = value & 0xFF;
        RegisterA.getInstance().setValue(value);

        RegisterF.getInstance().resetAllFlags();
        RegisterF.getInstance().setSubtractFlag(sub);

        if ((corr & 0x60) != 0)
            RegisterF.getInstance().setCarryFlag(true);


        if ((RegisterA.getInstance().getValue() & 0xFF) == 0)
            RegisterF.getInstance().setZeroFlag(true);


        //logger.debug("DAA END" + RegisterA.getInstance().getValue());
        //throw new Exception("CMD not executable: "+ i);
    }

    private void rra(Instruction i) throws Exception {
        i.args = new Parameter[1];
        i.args[0] = RegisterA.getInstance();
        rr(i);
    }

    private void rr(Instruction i) throws Exception {
        if (i.args.length == 1 && i.args[0].getType() == Register.Type.RegisterType) {

            RegisterF.getInstance().setHalfCarryFlag(false);
            RegisterF.getInstance().setSubtractFlag(false);
            RegisterF.getInstance().setZeroFlag(false);
            boolean old_carry = RegisterF.getInstance().getCarryFlag();

            int value = i.args[0].getValue();

            if (intToBitString(value).charAt(7) == '1') {
                // Carry
                RegisterF.getInstance().setCarryFlag(true);
            } else {
                // No Carry
                RegisterF.getInstance().setCarryFlag(false);
            }

            value = (value >> 1) & 0xFF;
            if (old_carry) {
                value = setBit(7, value);
            } else {
                value = unsetBit(7, value);
            }

            if (value == 0) {
                RegisterF.getInstance().setZeroFlag(true);
            }
            i.args[0].setValue(value & 0xFF);
            return;

        }
        throw new Exception("CMD not executable: " + i);
    }

    private void srl(Instruction i) throws Exception {
        if (i.args.length == 1 && i.args[0].getType() == Register.Type.RegisterType) {

            RegisterF.getInstance().setHalfCarryFlag(false);
            RegisterF.getInstance().setSubtractFlag(false);

            int shifted = i.args[0].getValue();
            if (intToBitString(shifted).charAt(7) == '1') {
                // Carry
                RegisterF.getInstance().setCarryFlag(true);
            } else {
                // No Carry
                RegisterF.getInstance().setCarryFlag(false);
            }
            shifted = (shifted >> 1) & 0xFF;
            if (shifted == 0) {
                RegisterF.getInstance().setZeroFlag(true);
            }
            i.args[0].setValue(shifted & 0xFF);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void rst(Instruction i) throws Exception {
        jp(i);
    }

    private void pop(Instruction i) throws Exception {
        if (i.args.length == 1) {
            int firstByte = Stack.getInstance().pop();
            int secondByte = Stack.getInstance().pop();
            int merged = Utilities.mergeByteTo16Bit(firstByte, secondByte);
            i.args[0].setValue(merged);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void push(Instruction i) throws Exception {
        if (i.args.length == 1) {
            byte[] bytes = Utilities.split16BitTo8BitByte(i.args[0].getValue());
            for (int index = 0; index < bytes.length; index++) {
                Stack.getInstance().push(bytes[index]);
            }
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void sub(Instruction i) throws Exception {
        if (i.args.length == 1) {
            RegisterA.getInstance().addValue(i.args[0].getValue() * -1);
            return;
        }
        if (i.args.length == 2) {
            i.args[0].addValue(i.args[1].getValue() * -1);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void ret(Instruction i) throws Exception {
        if (i.args.length == 0) {
            int firstByte = Stack.getInstance().pop();
            int secondByte = Stack.getInstance().pop();
            int merged = Utilities.mergeByteTo16Bit(firstByte, secondByte);
            RegisterPC.getInstance().setValue(merged);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void retz(Instruction i) throws Exception {
        if (i.args.length == 0) {
            if (!RegisterF.getInstance().getZeroFlag()) {
                RegisterPC.getInstance().addValue(i.instructionLength);
                return;
            }
            ret(i);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void retnc(Instruction i) throws Exception {
        if (i.args.length == 0) {
            if (RegisterF.getInstance().getCarryFlag()) {
                RegisterPC.getInstance().addValue(i.instructionLength);
                return;
            }
            ret(i);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void retc(Instruction i) throws Exception {
        if (i.args.length == 0) {
            if (!RegisterF.getInstance().getCarryFlag()) {
                RegisterPC.getInstance().addValue(i.instructionLength);
                return;
            }
            ret(i);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void jr(Instruction i) throws Exception {
        if (i.args.length == 1 && i.args[0].getType() == Parameter.Type.Value8SignedType) {
            int value = i.args[0].getValue();
            RegisterPC.getInstance().setValue(RegisterPC.getInstance().getValue() + value + i.instructionLength);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void call(Instruction i) throws Exception {
        if (i.args.length == 1 && i.args[0].getType() == Parameter.Type.Value16Type) {
            // TODO Splitting is ugly here
            byte[] bytes = Utilities.split16BitTo8BitByte(RegisterPC.getInstance().getValue() + i.instructionLength);
            for (int index = 0; index < bytes.length; index++) {
                Stack.getInstance().push(bytes[index]);
            }
            RegisterPC.getInstance().setValue(i.args[0].getValue());
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void callnz(Instruction i) throws Exception {
        if (!RegisterF.getInstance().getZeroFlag()) {
            call(i);
            return;
        } else {
            RegisterPC.getInstance().setValue(RegisterPC.getInstance().getValue() + i.instructionLength);
            return;
        }
    }

    private void ldh(Instruction i) throws Exception {
        if (i.args.length == 2 && (i.args[0].getType() == Parameter.Type.Address8AndFixedOffsetType || i.args[1].getType() == Parameter.Type.Address8AndFixedOffsetType)) {
            i.args[0].setValue(i.args[1].getValue());
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void disableInterrupts(Instruction i) throws Exception {
        InterruptFlags.getInstance().setInterruptMasterEnableFlag(false);
        return;
    }

    private void lddl(Instruction i) throws Exception {
        if (i.args.length == 2 && i.args[0].getType() == Parameter.Type.AddressInRegisterHLType) {
            i.args[0].setValue(i.args[1].getValue() & 0x00FF);
            RegisterHL.getInstance().addValue(-1);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void ldil(Instruction i) throws Exception {
        if (i.args.length == 2 && i.args[0].getType() == Parameter.Type.AddressInRegisterHLType) {
            i.args[0].setValue(i.args[1].getValue() & 0x00FF);
            RegisterHL.getInstance().addValue(1);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }


    private void ldir(Instruction i) throws Exception {
        if (i.args.length == 2 && i.args[1].getType() == Parameter.Type.AddressInRegisterHLType) {
            i.args[0].setValue(i.args[1].getValue() & 0x00FF);
            RegisterHL.getInstance().addValue(1);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void dec(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (i.args[0].getType().equals(Parameter.Type.AddressInRegisterHLType)) {
                ((Address) i.args[0]).addValue(-1);
                return;
            }
            ((Register) i.args[0]).dec();
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void inc(Instruction i) throws Exception {
        if (i.args.length == 1) {
            ((Register) i.args[0]).inc();
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void adc(Instruction i) throws Exception {
        if (i.args.length == 2) {
            if (RegisterF.getInstance().getCarryFlag())
                i.args[0].addValue(i.args[1].getValue() + 1);
            else
                i.args[0].addValue(i.args[1].getValue());
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void jrc(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (RegisterF.getInstance().getCarryFlag()) {
                int value = i.args[0].getValue();
                RegisterPC.getInstance().setValue(RegisterPC.getInstance().getValue() + value + i.instructionLength);
                return;
            }
            RegisterPC.getInstance().addValue(i.instructionLength);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void jrnc(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (!RegisterF.getInstance().getCarryFlag()) {
                int value = i.args[0].getValue();
                RegisterPC.getInstance().setValue(RegisterPC.getInstance().getValue() + value + i.instructionLength);
                return;
            }
            RegisterPC.getInstance().addValue(i.instructionLength);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void jpnz(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (!RegisterF.getInstance().getZeroFlag()) {
                RegisterPC.getInstance().setValue(i.args[0].getValue());
                //TODO REMOVE
                //System.out.println(RegisterPC.getInstance().getValueHex());
                //throw new Exception("CMD not executable: "+ i);
            }
            RegisterPC.getInstance().addValue(i.instructionLength);
            return;
        }

        throw new Exception("CMD not executable: " + i);
    }

    //BUG?
    private void jrnz(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (!RegisterF.getInstance().getZeroFlag()) {
                int value = i.args[0].getValue();

                //RegisterPC.getInstance().setValue(RegisterPC.getInstance().getValue() + value+i.instructionLength);
                RegisterPC.getInstance().setValue(RegisterPC.getInstance().getValue() + value + i.instructionLength);
                //RegisterPC.getInstance().addValue(value);
                //RegisterPC.getInstance().addValue(i.instructionLength);
                return;
            }
            RegisterPC.getInstance().addValue(i.instructionLength);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void jrz(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (RegisterF.getInstance().getZeroFlag()) {
                int value = i.args[0].getValue();
                RegisterPC.getInstance().setValue(RegisterPC.getInstance().getValue() + value + i.instructionLength);
                return;
            }
            RegisterPC.getInstance().addValue(i.instructionLength);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void jp(Instruction i) throws Exception {
        if (i.args.length == 1) {
            if (i.args[0].getType() == Parameter.Type.AddressInRegisterHLType) {
                //throw new Exception("Value "+Utilities.intToHex( ((Address)i.args[0]).getAddress()));
                RegisterPC.getInstance().setValue(((Address) i.args[0]).getAddress());
                return;
            }
            RegisterPC.getInstance().setValue(i.args[0].getValue());
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void ld(Instruction i) throws Exception {
        if (i.args.length == 2) {
            i.args[0].setValue(i.args[1].getValue());
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void add(Instruction i) throws Exception {
        if (i.args.length == 2) {
            i.args[0].addValue(i.args[1].getValue());
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void or(Instruction i) throws Exception {
        if (i.args.length == 1) {
            int result = (RegisterA.getInstance().getValue() | i.args[0].getValue()) & 0xFF;
            RegisterF.getInstance().resetAllFlags();
            if (result == 0) {
                RegisterF.getInstance().setZeroFlag(true);
            }
            RegisterA.getInstance().setValue(result);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void and(Instruction i) throws Exception {
        if (i.args.length == 1) {
            int result = (RegisterA.getInstance().getValue() & i.args[0].getValue()) & 0xFF;
            RegisterF.getInstance().resetAllFlags();
            RegisterF.getInstance().setHalfCarryFlag(true);
            if (result == 0) {
                RegisterF.getInstance().setZeroFlag(true);
            }
            RegisterA.getInstance().setValue(result);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void xor(Instruction i) throws Exception {
        if (i.args.length == 1) {
            int result = (RegisterA.getInstance().getValue() ^ i.args[0].getValue()) & 0xFF;
            RegisterF.getInstance().resetAllFlags();
            if (result == 0) {
                RegisterF.getInstance().setZeroFlag(true);
            }
            RegisterA.getInstance().setValue(result);
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

    private void cp(Instruction i) throws Exception {
        if (i.args.length == 1) {
            int result = (RegisterA.getInstance().getValue() - i.args[0].getValue()) & 0xFF;
            RegisterF.getInstance().resetAllFlags();
            RegisterF.getInstance().setSubtractFlag(true);
            if (RegisterF.getInstance().isBorrow(RegisterA.getInstance().getValue(), i.args[0].getValue())) {
                RegisterF.getInstance().setHalfCarryFlag(true);
            }
            if (result > 0) {
                RegisterF.getInstance().setCarryFlag(true);
            }
            if (result == 0) {
                RegisterF.getInstance().setZeroFlag(true);
            }
            return;
        }
        throw new Exception("CMD not executable: " + i);
    }

}
