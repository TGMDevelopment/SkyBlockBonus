package ga.matthewtgm.skyblockmod.mixins.rendering;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.playerhider.FeaturePlayerHider;
import ga.matthewtgm.skyblockmod.utils.NPCUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Render.class})
public class RenderMixin {

    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    public void onShadowRendered(Entity entity, double x, double y, double z, float shadowAlpha, float partialTicks, CallbackInfo ci) {
        if ((entity instanceof EntityOtherPlayerMP || entity instanceof EntityPlayerMP) && !(entity instanceof EntityPlayerSP) && SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeaturePlayerHider.class) && !NPCUtils.isNPC(entity))
            ci.cancel();
    }

}