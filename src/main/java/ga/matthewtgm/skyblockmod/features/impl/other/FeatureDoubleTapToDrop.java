package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.skyblockmod.events.DropItemEvent;
import ga.matthewtgm.skyblockmod.events.ModChatMessageSentEvent;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import ga.matthewtgm.skyblockmod.utils.MainUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureDoubleTapToDrop extends Feature {

    private String lastItemName;
    private long lastDropTime;

    public FeatureDoubleTapToDrop() {
        super("Double Tap To Drop", FeatureCategory.OTHER, false);
    }

    @Override
    public void onEnabled() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisabled() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    protected void onItemDropped(DropItemEvent.Pre event) {
        if (!MainUtils.getInstance().isPlayerInDungeon()) {
            ItemStack held = mc.thePlayer.getHeldItem();
            if (held != null) {
                lastDropTime = Minecraft.getSystemTime();
                String heldName = held.hasDisplayName() ? held.getDisplayName() : held.getUnlocalizedName();
                if (lastItemName == null || !lastItemName.equals(heldName) || Minecraft.getSystemTime() - lastDropTime >= 3000L) {
                    lastItemName = heldName;
                    event.setCanceled(true);
                    ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.FEATURE, "Press drop again to drop this item!");
                }
            }
        }
    }

}