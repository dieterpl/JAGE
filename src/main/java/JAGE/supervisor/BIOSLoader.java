package JAGE.supervisor;

import JAGE.processor.Memory;
import JAGE.utils.Utilities;
import org.apache.log4j.Logger;

public class BIOSLoader {
    private static BIOSLoader biosLoader;
    private static Logger logger = Logger.getRootLogger();

    private BIOSLoader() {
    }


    public static BIOSLoader getInstance() {
        if (biosLoader == null) {
            biosLoader = new BIOSLoader();
        }
        return biosLoader;
    }

    public void setInitalValues() throws Exception {
        Memory.getInstance().writeRamValue(Utilities.hexToInt("FF40"), Utilities.hexToByte("90"));
    }

}
