/*
 * Copyright 2020 P. Dieterich
 * All rights reserved.
 */
package JAGE.display;

import JAGE.processor.Memory;
import JAGE.processor.SpecialRegisters.SCROLLY;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Emulates LCD Controller
 */
public class GPU {
    private static GPU instance;
    private static Logger logger = Logger.getRootLogger();

    public GPU() throws Exception {


    }

    public static GPU getInstance() throws Exception {
        if (instance == null) {
            instance = new GPU();

        }
        return instance;
    }

    /**
     * Updates background by repellingly looking at memory changes.
     * @throws Exception
     */
    public void updateBackgroundTiles() throws Exception {
        int col = 0, row = 0;
        //int start = 0x9800;
        int scry = SCROLLY.getInstance().getValue();
        if (scry < 0)
            scry += 256;
        int start = 0x9800;
        for (int address = start; address <= start + 0x3FF; address += 1) {
            Color[][] tile = Tile.getTile(Memory.getInstance().getRamValue(address));
            for (int c = 0; c < 8; c++) {
                for (int r = 0; r < 8; r++) {
                    FrameBuffer.getInstance().setPixel((col + c) % 256, abs(row + r + (256 - scry)) % 256, tile[r][c]);
                }
            }
            col += 8;
            if (col == 256)
                row += 8;
            col %= 256;
        }
    }

    public void saveVRAM(byte[] array) {
        byte[] subArray = Arrays.copyOfRange(array, 0x8000, 0x9fff);
        try {
            PrintWriter writer = new PrintWriter("test.txt", "UTF-8");
            int i = 0;
            for (byte b : subArray) {
                String b1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
                writer.println(Integer.toHexString(i) + " " + b1);
                i++;
            }


            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
