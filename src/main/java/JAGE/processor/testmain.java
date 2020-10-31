package JAGE.processor;

import JAGE.cartridges.NormalCartridge;
import JAGE.processor.Registers.RegisterPC;

public class testmain {
    public static void main(String[] args) {
        try {
            NormalCartridge cartridge = new NormalCartridge("C:\\Users\\Paul\\Desktop\\test\\sp.gb");
            cartridge.loadCartridge();
            System.out.println(cartridge.getDataLength());
            while (true) {
                Instruction in = Interpreter.getInstance().interpret(RegisterPC.getInstance().getValue());
                System.out.println("0x" + RegisterPC.getInstance().getValueHex() + " " + in.toString());
                Executer.getInstance().exec(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
