package ga.matthewtgm.skyblockmod.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackUtils {

    private static ItemStackUtils INSTANCE;

    public static ItemStackUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ItemStackUtils();
        return INSTANCE;
    }

    public String getSkyBlockId(ItemStack is) {
        if (is == null) throw new NullPointerException("The ItemStack passed cannot be null.");
        if (!is.hasTagCompound()) return null;
        NBTTagCompound ExtraAttributes = is.getTagCompound().getCompoundTag("ExtraAttributes");
        if (ExtraAttributes != null) {
            String skyblockId = ExtraAttributes.getString("id");
            if (skyblockId != null || skyblockId != "") {
                return skyblockId;
            }
        }
        return null;
    }

}