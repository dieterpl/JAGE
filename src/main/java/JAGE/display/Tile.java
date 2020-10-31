package JAGE.display;

import JAGE.processor.Memory;
import JAGE.utils.Utilities;
import javafx.scene.paint.Color;

public class Tile {


    public static Color[][] getTile(int location) throws Exception {
        Color[][] tile = new Color[8][8];
        location = location * 0x10 + 0x8000;
        for (int offset = 0; offset < 16; offset += 2) {
            int value1 = Memory.getInstance().getRamValue(location + offset);
            int value2 = Memory.getInstance().getRamValue(location + offset + 1);
            String bit1 = Utilities.intToBitString(value1);
            String bit2 = Utilities.intToBitString(value2);
            for (int bit = 0; bit < 8; bit++) {
                Color color = new Color(0, 0, 0, 1);
                if (bit1.charAt(bit) == '0' && bit2.charAt(bit) == '0') {
                    color = new Color(1, 1, 1, 1);
                }
                if (bit1.charAt(bit) == '1' && bit2.charAt(bit) == '0') {
                    color = new Color(1, 0, 0, 1);
                }
                if (bit1.charAt(bit) == '0' && bit2.charAt(bit) == '1') {
                    color = new Color(0, 1, 0, 1);
                }
                if (bit1.charAt(bit) == '1' && bit2.charAt(bit) == '1') {
                    color = new Color(0, 0, 0, 1);
                }
                tile[offset / 2][bit] = color;
            }

        }
        return tile;
    }

}
