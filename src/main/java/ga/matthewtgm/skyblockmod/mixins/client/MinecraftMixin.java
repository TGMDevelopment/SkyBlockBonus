package ga.matthewtgm.skyblockmod.mixins.client;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public class MinecraftMixin {

    @Inject(method = "startGame", at = @At("HEAD"))
    public void onStart(CallbackInfo ci) {
        SkyBlockBonus.getInstance().truPreInitialization();
    }

}