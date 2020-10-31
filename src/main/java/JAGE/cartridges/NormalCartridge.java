package JAGE.cartridges;

import JAGE.processor.Memory;
import JAGE.utils.Utilities;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NormalCartridge {

    private byte[] data = null;
    private Path path;

    public NormalCartridge(String filePath) throws IOException {
        path = Paths.get(filePath);

    }

    public int getDataLength() {
        return data == null ? 0 : data.length;
    }

    public byte[] getData() {
        return data;
    }

    public void writeFile() throws IOException {
        Files.write(path, data);
    }

    public boolean isChecksumCorrect() {
        int sum = 0;
        for (int i = 308; i <= 333; i++) {
            sum += Integer.valueOf(data[i]);
        }
        sum += 25;
        byte[] checksum = ByteBuffer.allocate(4).putInt(sum).array();

        return checksum[checksum.length - 1] == 0 ? true : false;
    }

    public void fixChecksumCorrect() {
        int sum = 0;
        for (int i = 308; i <= 333; i++) {
            sum += Integer.valueOf(data[i]);
        }
        sum += 25;
        byte[] checksum = ByteBuffer.allocate(4).putInt(sum).array();
        Utilities.printByte(checksum[checksum.length - 1]);
        if (Integer.valueOf(data[333]) < Integer.valueOf(checksum[checksum.length - 1])) {
            data[333] += (byte) (0xFF - checksum[checksum.length - 1] + 1);
        } else {
            data[333] = (byte) (data[333] - checksum[checksum.length - 1]);
        }
        Utilities.printByte(data[333]);
    }

    public void loadCartridge() throws Exception {
        data = Files.readAllBytes(path);
        if (this.getDataLength() <= Utilities.hexToInt("8000")) {
            for (int i = 0; i < this.getDataLength(); i++) {
                Memory.getInstance().writeRamValue(i, data[i]);
                //System.out.println( a.getValueHex()+" "+(Memory.getInstance().getRamValue(a)&0xFF));
            }
        }
    }


}
