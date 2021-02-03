package ga.matthewtgm.skyblockmod.features.impl.petitemsaver;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class FeaturePetItemSaver extends Feature {

    private final List<String> petUpgradeItemsList = Arrays.asList(
            "all skills exp boost",
            "mining exp boost",
            "farming exp boost",
            "fishing exp boost",
            "combat exp boost",
            "foraging exp boost",
            "big teeth",
            "iron claws",
            "hardened scales",
            "sharpened claws",
            "bubblegum",
            "textbook",
            "lucky clover",
            "saddle",
            "tier boost",
            "exp share"
    );

    public FeaturePetItemSaver() {
        super("Pet Item Saver", false);
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
        if (Minecraft.getMinecraft().thePlayer == null) return;
        if (event.entityPlayer != Minecraft.getMinecraft().thePlayer) return;
        final ItemStack held = event.entityPlayer.getHeldItem();
        if(held == null) return;
        for (String upgrade : this.petUpgradeItemsList) {
            System.out.println(StringUtils.stripControlCodes(held.getDisplayName()).toLowerCase().contains(upgrade));
            if (!StringUtils.stripControlCodes(held.getDisplayName()).toLowerCase().contains(upgrade)) return;
            else {
                event.setCanceled(true);
                ChatUtils.getInstance().sendModMessage("A feature has stopped you from using this item!");
            }
        }
    }

}