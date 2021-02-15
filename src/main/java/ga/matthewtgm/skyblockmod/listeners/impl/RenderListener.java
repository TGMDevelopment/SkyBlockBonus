package ga.matthewtgm.skyblockmod.listeners.impl;

import ga.matthewtgm.skyblockmod.listeners.SkyBlockModListener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderListener extends SkyBlockModListener {

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {

    }

    @SubscribeEvent
    protected void onTooltip(ItemTooltipEvent event) {
        NBTTagCompound tagCompound = event.itemStack.getTagCompound();
        if(tagCompound == null) return;
        NBTTagCompound extraAttributes = tagCompound.getCompoundTag("ExtraAttributes");
        if (extraAttributes == null) return;
        if (extraAttributes.getString("id") == null) return;
        event.toolTip.add(extraAttributes.getString("id"));
    }

}