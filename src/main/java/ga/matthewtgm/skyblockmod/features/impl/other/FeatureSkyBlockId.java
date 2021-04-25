package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.utils.ItemStackUtils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureSkyBlockId extends Feature {

    public FeatureSkyBlockId() {
        super("Show SkyBlock IDs", FeatureCategory.OTHER, false);
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
    protected void onTooltip(ItemTooltipEvent event) {
        if (ItemStackUtils.getInstance().hasSkyBlockId(event.itemStack)) {
            event.toolTip.add(EnumChatFormatting.DARK_GRAY + "skyblock:" + ItemStackUtils.getInstance().getSkyBlockId(event.itemStack));
        }
    }

}