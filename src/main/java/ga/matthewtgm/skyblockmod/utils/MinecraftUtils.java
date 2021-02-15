package ga.matthewtgm.skyblockmod.utils;

import net.minecraft.inventory.*;

public class MinecraftUtils {

    private static MinecraftUtils INSTANCE;

    public static MinecraftUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MinecraftUtils();
        return INSTANCE;
    }

    public int getSlotDifference(Container container) {
        if (container instanceof ContainerChest) return 9-((ContainerChest)container).getLowerChestInventory().getSizeInventory();
        else if (container instanceof ContainerHopper) return 4;
        else if (container instanceof ContainerFurnace) return 6;
        else if (container instanceof ContainerBeacon) return 8;
        else return 0;
    }

}