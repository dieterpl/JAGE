package JAGE.display;

import JAGE.processor.Memory;
import javafx.scene.paint.Color;

public class FrameBuffer {

    public static final int width = 256;
    public static final int height = 256;
    private static FrameBuffer instance;
    private Color[][] frameBuffer = new Color[width][height];

    private FrameBuffer() throws Exception {
        for (int x = 0; x < FrameBuffer.width; x++) {
            for (int y = 0; y < FrameBuffer.height; y++) {
                this.setPixel(x, y, new Color(1, 1, 1, 1));
            }
        }
    }

    public static FrameBuffer getInstance() throws Exception {
        if (instance == null) {
            instance = new FrameBuffer();
        }
        return instance;
    }



    public void setPixel(int x, int y, Color color) throws Exception {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new Exception("Pixel x:" + x + "and y:" + y + " out of bounds.");
        }
        this.frameBuffer[x][y] = color;
        return;
    }

    public Color getPixel(int x, int y) throws Exception {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new Exception("Pixel x:" + x + "and y:" + y + " out of bounds.");
        }
        return this.frameBuffer[x][y];
    }


}
