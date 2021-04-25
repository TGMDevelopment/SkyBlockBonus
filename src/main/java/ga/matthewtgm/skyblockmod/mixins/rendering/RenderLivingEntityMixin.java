package ga.matthewtgm.skyblockmod.mixins.rendering;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureHideCreeperVeil;
import ga.matthewtgm.skyblockmod.features.impl.playerhider.FeaturePlayerHider;
import ga.matthewtgm.skyblockmod.features.impl.playerhider.FeatureHidePlayersNearNPCs;
import ga.matthewtgm.skyblockmod.utils.NPCUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RendererLivingEntity.class})
public class RenderLivingEntityMixin<T extends EntityLivingBase> {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if ((entity instanceof EntityOtherPlayerMP || entity instanceof EntityPlayerMP) && !(entity instanceof EntityPlayerSP) && SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeaturePlayerHider.class) && !NPCUtils.isNPC(entity))
            ci.cancel();

        if ((entity instanceof EntityOtherPlayerMP || entity instanceof EntityPlayerMP) && !(entity instanceof EntityPlayerSP) && NPCUtils.isNearNPC(entity) && !NPCUtils.isNPC(entity) && SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureHidePlayersNearNPCs.class))
            ci.cancel();

        if (entity instanceof EntityCreeper) {
            EntityCreeper creeper = (EntityCreeper) entity;
            if (creeper.getPowered() && creeper.isInvisible() && !creeper.getDisplayName().getUnformattedText().toLowerCase().contains("Ghost") && SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureHideCreeperVeil.class))
                ci.cancel();
        }
    }

}