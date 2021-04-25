package ga.matthewtgm.skyblockmod.features.impl.raredropmessage;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import ga.matthewtgm.json.parsing.JsonParser;
import ga.matthewtgm.lib.util.WebUtils;
import ga.matthewtgm.skyblockmod.events.InventoryUpdateEvent;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.skyblock.Rarity;
import ga.matthewtgm.skyblockmod.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class FeatureRareDropMessage extends Feature {

    private List<LinkedTreeMap> rareDropList;

    public FeatureRareDropMessage() {
        super("Rare Drop Message", FeatureCategory.CHAT, false);
        this.rareDropList = (List) JsonParser.parseArr(new Gson().toJsonTree(WebUtils.getInstance().getJsonOnline("https://raw.githubusercontent.com/TGMDevelopment/SkyBlock-Bonus-Data/main/features/raredropmessage/raredrops.json")).getAsString());
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
    protected void onInventoryUpdate(InventoryUpdateEvent event) {
        if (event.diff.getAsItemStack() == null) return;
        final ItemStack item = event.diff.getAsItemStack();
        this.rareDropList.forEach(drop -> {
            String itemId = ItemStackUtils.getInstance().getSkyBlockId(item);
            if (itemId == null) return;
            if (!itemId.contains((CharSequence) drop.get("id"))) return;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "RARE DROP! " + Rarity.valueOf((String) drop.get("rarity")).getColour() + StringUtils.stripControlCodes(item.getDisplayName())));
        });
    }

}