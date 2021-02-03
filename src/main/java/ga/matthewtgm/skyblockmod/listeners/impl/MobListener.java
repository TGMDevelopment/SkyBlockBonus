package ga.matthewtgm.skyblockmod.listeners.impl;

import ga.matthewtgm.skyblockmod.features.impl.playerhider.FeaturePlayerHider;
import ga.matthewtgm.skyblockmod.listeners.SkyBlockModListener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MobListener extends SkyBlockModListener {

    @SubscribeEvent
    public void onEntityRender(RenderLivingEvent.Pre<EntityOtherPlayerMP> event) {
        if (event.entity instanceof EntityPlayerSP) return;
        if (!(event.entity instanceof EntityOtherPlayerMP)) return;
        if (FeaturePlayerHider.getShouldHidePlayers() && !this.isNPC(event.entity)) event.setCanceled(true);
    }

    public boolean isNPC(Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        return entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0F && !entityLivingBase.isPlayerSleeping();
    }

}