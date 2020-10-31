package JAGE.processor;

import JAGE.processor.interfaces.Parameter;
import JAGE.utils.Utilities;

public class Instruction {
    int instructionCycles;
    int instructionLength;

    Byte opcode;
    Byte subopcode;
    String cmd;

    Parameter[] args;


    public Instruction(String cmd, int instructionCycles, int instructionLength, byte opcode, Parameter... parameter) {
        args = parameter;
        this.cmd = cmd;
        this.instructionCycles = instructionCycles;
        this.instructionLength = instructionLength;
        this.opcode = opcode;
    }

    public Instruction(String cmd, int instructionCycles, int instructionLength, byte opcode, byte subopcode, Parameter... parameter) {
        args = parameter;
        this.cmd = cmd;
        this.instructionCycles = instructionCycles;
        this.instructionLength = instructionLength;
        this.opcode = opcode;
        this.subopcode = subopcode;
    }

    public int getInstructionLength() {
        return instructionLength;
    }

    public void setInstructionLength(int instructionLength) {
        this.instructionLength = instructionLength;
    }

    public int getInstructionCycles() {
        return instructionCycles;
    }

    public String toString() {
        String instr = cmd + " " + Utilities.intToHex((opcode & 0xFF)).toUpperCase() + " ";
        for (int i = 0; i < args.length; i++) {
            instr += args[i].toString() + ",";
        }
        instr = instr.substring(0, instr.length() - 1);
        return instr;

    }

    public Instruction getClone() throws CloneNotSupportedException {
        return (Instruction) clone();
    }

}




