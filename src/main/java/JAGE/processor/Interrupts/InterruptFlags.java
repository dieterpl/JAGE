package JAGE.processor.Interrupts;

public class InterruptFlags {

    private static InterruptFlags irf;
    boolean interruptMasterEnableFlag;

    private InterruptFlags() {

    }

    public static InterruptFlags getInstance() {
        if (irf == null) {
            irf = new InterruptFlags();
        }
        return InterruptFlags.irf;
    }

    public boolean isInterruptMasterEnableFlag() {
        return interruptMasterEnableFlag;
    }

    //TODO RELEASE CHANGE WITH NEXT FINISHED CMD
    public void setInterruptMasterEnableFlag(boolean interruptMasterEnableFlag) {
        this.interruptMasterEnableFlag = interruptMasterEnableFlag;
    }


}
