package JAGE.supervisor;


import JAGE.cartridges.NormalCartridge;
import JAGE.display.GPU;
import JAGE.processor.Executer;
import JAGE.processor.Instruction;
import JAGE.processor.Interpreter;
import JAGE.processor.Memory;
import JAGE.processor.Registers.RegisterA;
import JAGE.processor.Registers.RegisterPC;
import JAGE.processor.Registers.RegisterSP;
import JAGE.utils.Utilities;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Supervisor {

    private static Supervisor supervisor;
    private static Logger logger = Logger.getRootLogger();
    String disassembly = "";


    private Supervisor() {
    }

    public static Supervisor getInstance() {
        if (supervisor == null) {
            supervisor = new Supervisor();
        }
        return supervisor;
    }

    public String generateDissasembly() {
        if (disassembly.equals("")) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < Utilities.hexToInt("8000"); i++) {

                try {
                    stringBuilder.append(Interpreter.getInstance().interpret(i).toString());
                } catch (Exception e) {
                    stringBuilder.append("Not Implemented");
                }
                stringBuilder.append("\n");

            }
            disassembly = stringBuilder.toString();
        }
        return disassembly;
    }


    public void startEmulator() {
        try {
            logger.setLevel(Level.INFO);

            NormalCartridge cartridge = new NormalCartridge("testRoms/cpu_instrs/individual/01-special.gb");

            cartridge.loadCartridge();
            BIOSLoader.getInstance().setInitalValues();

            int index = 1600000;
            RegisterPC.getInstance().setValue(Utilities.hexToInt("100"));
            GPU.getInstance();
            int prev = 0;

            //TODO
            int prev_temp = 0;

            while (index > 0) {
                index--;
                Instruction in = Interpreter.getInstance().interpret(RegisterPC.getInstance().getValue());
                String before = in.toString();
                String address = RegisterPC.getInstance().getValueHex();
                Executer.getInstance().exec(in);
                /*
                if (Memory.getInstance().getRamValue(0xff42) != prev_temp) {
                    logger.debug("0x" + address + " B:" + before + " A: " + in + "SP: " + RegisterSP.getInstance().getValueHex() + " o_d81dh: " +prev_temp + " n_d81dh: "+Memory.getInstance().getRamValue(0xff42)+"\n");
                    prev_temp = Memory.getInstance().getRamValue(0xff42);
                }
                else

                */
                logger.debug("0x" + address + " B:" + before + " A: " + in+"SP: "+ RegisterSP.getInstance().getValueHex()+"\n");
                // SB TEST
                if (Memory.getInstance().getRamValue(0xFF01) != prev) {
                    prev = Memory.getInstance().getRamValue(0xFF01);
                    if(logger.getLevel()==Level.DEBUG)
                        logger.debug("SERIAL : " + Character.toString((char) prev) + " PC:" + RegisterPC.getInstance().getValueHex() + " A:" + RegisterA.getInstance().getValue()+"\n");
                    else
                        logger.info(Character.toString((char) prev));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
