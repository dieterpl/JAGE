/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.processor;

import JAGE.processor.Registers.*;
import JAGE.processor.interfaces.Parameter;
import JAGE.utils.Utilities;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Interprets raw values into instructions based on YML File
 */
public class Interpreter {

    private static Interpreter interpreter;
    private HashMap<Byte, HashMap<Byte, Instruction>> instructionDatabase = new HashMap<>();

    private Interpreter() throws Exception {
        createInstructionDatabase();
    }

    public static Interpreter getInstance() throws Exception {
        if (interpreter == null) {
            interpreter = new Interpreter();
        }
        return interpreter;
    }

    private void createInstructionDatabase() throws Exception {
        // Parse JSON
        String instructionsYAML = "";
        String resource = "instructions.yaml";
        ClassLoader loader = this.getClass().getClassLoader();
        URL resourceUrl = loader.getResource(resource);
        if (resourceUrl != null) {
            try (InputStream input = resourceUrl.openStream()) {
                StringWriter writer = new StringWriter();
                IOUtils.copy(input, writer, "UTF-8");
                instructionsYAML = writer.toString();
            }
        }
        // Load JSON in Objects
        Yaml yaml = new Yaml();
        Map<String, ArrayList<Map<String, Object>>> main = yaml.load(instructionsYAML);
        ArrayList<Map<String, Object>> instructions = main.get("Instructions");
        for (Map<String, Object> i : instructions) {
            byte opcode = (byte) ((int) i.get("opcode"));
            byte subopcode = 0x0;
            if (i.containsKey("subopcode"))
                subopcode = (byte) ((int) i.get("subopcode"));
            String cmd = (String) i.get("cmd");
            int instLength = (int) i.get("instructionLength");
            int instCycles = (int) i.get("instructionCycles");

            //Convert to array and attach Register Object
            ArrayList<String> paramList = null;
            Parameter[] paramArray = null;
            if (i.containsKey("parameters")) {
                if (i.get("parameters") instanceof String) {
                    paramList = new ArrayList<String>();
                    paramList.add((String) i.get("parameters"));
                } else
                    paramList = (ArrayList<String>) i.get("parameters");
                paramArray = new Parameter[paramList.size()];
                for (int index = 0; index < paramList.size(); index++) {
                    String s = paramList.get(index);
                    switch (s) {
                        case "RegisterA":
                            paramArray[index] = RegisterA.getInstance();
                            break;
                        case "RegisterB":
                            paramArray[index] = RegisterB.getInstance();
                            break;
                        case "RegisterC":
                            paramArray[index] = RegisterC.getInstance();
                            break;
                        case "RegisterD":
                            paramArray[index] = RegisterD.getInstance();
                            break;
                        case "RegisterE":
                            paramArray[index] = RegisterE.getInstance();
                            break;
                        case "RegisterF":
                            paramArray[index] = RegisterF.getInstance();
                            break;
                        case "RegisterH":
                            paramArray[index] = RegisterH.getInstance();
                            break;
                        case "RegisterL":
                            paramArray[index] = RegisterL.getInstance();
                            break;
                        case "RegisterAF":
                            paramArray[index] = RegisterAF.getInstance();
                            break;
                        case "RegisterBC":
                            paramArray[index] = RegisterBC.getInstance();
                            break;
                        case "RegisterDE":
                            paramArray[index] = RegisterDE.getInstance();
                            break;
                        case "RegisterHL":
                            paramArray[index] = RegisterHL.getInstance();
                            break;
                        case "RegisterPC":
                            paramArray[index] = RegisterPC.getInstance();
                            break;
                        case "RegisterSP":
                            paramArray[index] = RegisterSP.getInstance();
                            break;
                        case "Value8":
                            paramArray[index] = new Value(Parameter.Type.Value8Type);
                            break;
                        case "Value8Signed":
                            paramArray[index] = new Value(Parameter.Type.Value8SignedType);
                            break;
                        case "Value8Fixed00H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed00HType);
                            break;
                        case "Value8Fixed02H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed02HType);
                            break;
                        case "Value8Fixed04H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed04HType);
                            break;
                        case "Value8Fixed08H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed08HType);
                            break;
                        case "Value8Fixed10H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed10HType);
                            break;
                        case "Value8Fixed20H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed20HType);
                            break;
                        case "Value8Fixed40H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed40HType);
                            break;
                        case "Value8Fixed80H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed80HType);
                            break;
                        case "Value8Fixed18H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed18HType);
                            break;
                        case "Value8Fixed28H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed28HType);
                            break;
                        case "Value8Fixed30H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed30HType);
                            break;
                        case "Value8Fixed38H":
                            paramArray[index] = new Value(Parameter.Type.Value8Fixed38HType);
                            break;
                        case "Value16":
                            paramArray[index] = new Value(Parameter.Type.Value16Type);
                            break;
                        case "Address8AndFixedOffset":
                            paramArray[index] = new Address(Parameter.Type.Address8AndFixedOffsetType);
                            break;
                        case "Address16":
                            paramArray[index] = new Address(Parameter.Type.Address16Type);
                            break;
                        case "AddressInRegisterHL":
                            paramArray[index] = new Address(Parameter.Type.AddressInRegisterHLType);
                            break;
                        case "AddressInRegisterBC":
                            paramArray[index] = new Address(Parameter.Type.AddressInRegisterBCType);
                            break;
                        case "AddressInRegisterDE":
                            paramArray[index] = new Address(Parameter.Type.AddressInRegisterDEType);
                            break;
                        case "AddressInRegisterCAndFixedOffset":
                            paramArray[index] = new Address(Parameter.Type.AddressInRegisterCAndFixedOffsetType);
                            break;
                        default:
                            throw new Exception("Parameter " + s + " not definined!");
                    }
                }

            }

            // Build Instruction
            if (i.containsKey("subopcode")) {
                HashMap<Byte, Instruction> m = new HashMap<>();
                if (!instructionDatabase.containsKey(opcode))
                    instructionDatabase.put(opcode, m);
                else {
                    if (i.containsKey("parameters"))
                        instructionDatabase.get(opcode).put(subopcode, new Instruction(cmd, instCycles, instLength, opcode, subopcode, paramArray));
                    else
                        instructionDatabase.get(opcode).put(subopcode, new Instruction(cmd, instCycles, instLength, opcode, subopcode));
                }
            } else {
                if (i.containsKey("parameters"))
                    instructionDatabase.put(opcode, singleCMD(new Instruction(cmd, instCycles, instLength, opcode, paramArray)));
                else
                    instructionDatabase.put(opcode, singleCMD(new Instruction(cmd, instCycles, instLength, opcode)));
            }


        }

    }

    private HashMap<Byte, Instruction> singleCMD(Instruction i) {
        HashMap<Byte, Instruction> hm = new HashMap<>();
        hm.put((byte) 0x00, i);
        return hm;
    }

    public Instruction interpret(int address) throws Exception {
        Instruction instruction;
        if (instructionDatabase.containsKey(Memory.getInstance().getRamValue(address))) {
            if (instructionDatabase.get(Memory.getInstance().getRamValue(address)).size() == 1) {
                instruction = instructionDatabase.get(Memory.getInstance().getRamValue(address)).get((byte) 0x00);
            } else {
                instruction = instructionDatabase.get(Memory.getInstance().getRamValue(address)).get(Memory.getInstance().getRamValue(address + 1));
                address += 1;
            }
            address += 1;

            if (instruction.args != null)
                for (int i = 0; i < instruction.args.length; i++) {
                    switch (instruction.args[i].getType()) {
                        case Value8Type:
                            instruction.args[i].setValue(Memory.getInstance().getRamValue(address));
                            break;
                        case Value8SignedType:
                            instruction.args[i].setValue(Memory.getInstance().getRamValue(address));
                            break;
                        case Value16Type:
                            instruction.args[i].setValue(Utilities.mergeByteTo16Bit(Memory.getInstance().getRamValue(address + 1), Memory.getInstance().getRamValue(address)));
                            break;
                        case Address8AndFixedOffsetType:
                            Address a = (Address) instruction.args[i];
                            a.setAddress(Memory.getInstance().getRamValue(address));
                            break;
                        case Address16Type:
                            a = (Address) instruction.args[i];
                            a.setAddress(Utilities.mergeByteTo16Bit(Memory.getInstance().getRamValue(address + 1), Memory.getInstance().getRamValue(address)));
                            break;
                    }
                }

            return instruction;
        }
        throw new Exception("Not implemented function! OPCode: " + Utilities.byteToHex(Memory.getInstance().getRamValue(address)) + " At 0x" + Utilities.intToHex(address));

    }

}
