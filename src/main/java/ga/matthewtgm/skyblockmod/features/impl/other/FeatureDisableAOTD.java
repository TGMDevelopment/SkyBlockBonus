package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.skyblockmod.events.ModChatMessageSentEvent;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import ga.matthewtgm.skyblockmod.utils.ItemStackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureDisableAOTD extends Feature {

    public FeatureDisableAOTD() {
        super("Disable AOTD", FeatureCategory.OTHER, false);
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
    protected void onItemInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (event.entityPlayer == null) return;
            if (event.entityPlayer.getHeldItem() == null) return;
            EntityPlayer player = event.entityPlayer;
            ItemStack held = player.getHeldItem();
            String itemId = ItemStackUtils.getInstance().getSkyBlockId(held);
            if (itemId == null) return;
            if (!itemId.contains("ASPECT_OF_THE_DRAGON")) return;
            event.setCanceled(true);
            ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.FEATURE, "A feature has stopped you from using this item!");
        }
    }

}