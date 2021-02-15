package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import ga.matthewtgm.skyblockmod.skyblock.EnumLocation;
import ga.matthewtgm.skyblockmod.utils.ItemStackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureDisableEmberRod extends Feature {

    public FeatureDisableEmberRod() {
        super("Disable Ember Rods", FeatureCategory.OTHER, false);
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
        if(event.entityPlayer == null) return;
        if(event.entityPlayer.getHeldItem() == null) return;
        if(SkyBlockBonus.getInstance().getLocListener().getLocation() != EnumLocation.ISLAND) return;
        EntityPlayer player = event.entityPlayer;
        ItemStack held = player.getHeldItem();
        String itemId = ItemStackUtils.getInstance().getSkyBlockId(held);
        if(!itemId.contains("EMBER_ROD")) return;
        event.setCanceled(true);
        ChatUtils.getInstance().sendModMessage("A feature has stopped you from using this item!");
    }

}