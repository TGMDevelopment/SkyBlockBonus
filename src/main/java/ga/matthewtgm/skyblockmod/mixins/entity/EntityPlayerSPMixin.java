package ga.matthewtgm.skyblockmod.mixins.entity;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureLockSlots;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import ga.matthewtgm.skyblockmod.skyblock.EnumLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ContainerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayerSP.class})
public class EntityPlayerSPMixin {

    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    public void dropOneItem(CallbackInfoReturnable<EntityItem> cir) {
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).isToggled() &&
                SkyBlockBonus.getInstance().getLocListener().getLocation() != EnumLocation.DUNGEON) {
            int currentSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem + 36;
            FeatureLockSlots lockSlots = SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class);
            if (lockSlots.getProfileLockedSlots().contains(currentSlot) &&
                    (currentSlot >= 9 || Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerPlayer && currentSlot >= 5)) {
                ChatUtils.getInstance().sendModMessage("A feature has stopped you from dropping this item.");
                cir.cancel();
            }
        }
    }

}