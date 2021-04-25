package ga.matthewtgm.skyblockmod.mixins.rendering;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureDisableHurtCam;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureTransparentSkinRendering;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public class EntityRendererMixin {

    @Inject(method = "hurtCameraEffect(F)V", cancellable = true, at = @At("HEAD"))
    private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureDisableHurtCam.class))
            ci.cancel();
    }

    @Inject(method = "renderHand(FI)V", at = @At("HEAD"))
    private void renderPre(float partialTicks, int xOffset, CallbackInfo info)
    {
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureTransparentSkinRendering.class))
        {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
        }
    }

    @Inject(method = "renderHand(FI)V", at = @At("RETURN"))
    private void renderPost(float partialTicks, int xOffset, CallbackInfo info)
    {
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureTransparentSkinRendering.class))
        {
            GlStateManager.disableBlend();
        }
    }

}