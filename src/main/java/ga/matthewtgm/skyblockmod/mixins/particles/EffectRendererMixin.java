package ga.matthewtgm.skyblockmod.mixins.particles;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureDisableBlockParticles;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EffectRenderer.class})
public class EffectRendererMixin {

    @Inject(method = "addBlockDestroyEffects", at = @At("HEAD"), cancellable = true)
    protected void onBlockDestroyParticles(BlockPos pos, IBlockState state, CallbackInfo ci) {
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureDisableBlockParticles.class))
            ci.cancel();
    }

    @Inject(method = "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V", at = @At("HEAD"), cancellable = true)
    protected void onBlockHitParticles(BlockPos pos, EnumFacing facing, CallbackInfo ci) {
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureDisableBlockParticles.class))
            ci.cancel();
    }

}