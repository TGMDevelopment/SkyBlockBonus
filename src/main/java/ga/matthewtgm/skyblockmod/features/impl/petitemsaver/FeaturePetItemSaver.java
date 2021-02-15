package ga.matthewtgm.skyblockmod.features.impl.petitemsaver;

import ga.matthewtgm.json.parsing.JsonParser;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import ga.matthewtgm.skyblockmod.utils.ItemStackUtils;
import ga.matthewtgm.skyblockmod.utils.WebUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class FeaturePetItemSaver extends Feature {

    private List<String> petUpgradeItemsList;

    public FeaturePetItemSaver() {
        super("Pet Item Saver", FeatureCategory.OTHER, false);
        this.petUpgradeItemsList = (List<String>) JsonParser.parseArr(WebUtils.getInstance().getJsonOnline("https://raw.githubusercontent.com/TGMDevelopment/SkyBlock-Bonus-Data/main/features/petitemsaver/petitems.json"));
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
        if (held == null) return;
        for (String upgradeId : this.petUpgradeItemsList) {
            String itemId = ItemStackUtils.getInstance().getSkyBlockId(held);
            if (itemId == null) return;
            if (!itemId.contains(upgradeId)) return;
            event.setCanceled(true);
            ChatUtils.getInstance().sendModMessage("A feature has stopped you from using this item!");
        }
    }

}