package ga.matthewtgm.skyblockmod.utils;

import net.minecraft.item.ItemStack;

public class InvDiff {

    public final Long lifespan = 10000L;
    private final String displayName;
    private ItemStack asItemStack;
    private int amount;
    private long timestamp;

    public InvDiff(String displayName, ItemStack is, int amount) {
        this.displayName = displayName;
        this.asItemStack = is;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    public void add(int amount) {
        this.amount += amount;
        if (this.amount == 0)
            this.timestamp -= this.lifespan;
        else
            this.timestamp = System.currentTimeMillis();
    }

    public long getLifeTime() {
        return System.currentTimeMillis() - this.timestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ItemStack getAsItemStack() {
        return asItemStack;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Long getLifespan() {
        return lifespan;
    }

}