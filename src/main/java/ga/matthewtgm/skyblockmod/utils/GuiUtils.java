package ga.matthewtgm.skyblockmod.utils;

public class GuiUtils {

    private static GuiUtils INSTANCE;
    private int lastHoveredSlot = -1;

    public static GuiUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GuiUtils();
        return INSTANCE;
    }

    public int getLastHoveredSlot() {
        return lastHoveredSlot;
    }

    public void setLastHoveredSlot(int lastHoveredSlot) {
        this.lastHoveredSlot = lastHoveredSlot;
    }

}