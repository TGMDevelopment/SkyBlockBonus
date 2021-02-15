package ga.matthewtgm.skyblockmod.mixins.gui;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureLockSlots;
import ga.matthewtgm.skyblockmod.utils.GuiUtils;
import ga.matthewtgm.skyblockmod.utils.MinecraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin({GuiContainer.class})

public class GuiContainerMixin {

    private ResourceLocation lockRl = new ResourceLocation("sbb", "textures/features/slotlocking/lock.png");

    /*@Inject(method = "drawSlot", at = @At(value = "HEAD"))
    public void drawSlot(Slot slot, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        if (slot != null) {
            if (SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).isToggled()) {
                int slotNumber = slot.slotNumber + MinecraftUtils.getInstance().getSlotDifference(Minecraft.getMinecraft().thePlayer.openContainer);
                if (SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).getProfileLockedSlots()
                        .contains(slotNumber) && (slotNumber >= 9 || (Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerPlayer && slotNumber >= 5))) {
                    GlStateManager.color(1, 1, 1, 0.3F);
                    mc.getTextureManager().bindTexture(lockRl);
                    mc.ingameGUI.drawTexturedModalRect(slot.xDisplayPosition, slot.yDisplayPosition, 0, 0, 16, 16);
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                }
            }
        }
    }*/

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGradientRect(IIIIII)V"))
    public void drawScreen_drawGradientRect_setLastHoveredSlot(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        GuiContainer $instance = (GuiContainer) (Object) this;
        if ($instance.getSlotUnderMouse() != null) {
            int slotNumber = $instance.getSlotUnderMouse().slotNumber + MinecraftUtils.getInstance().getSlotDifference(Minecraft.getMinecraft().thePlayer.openContainer);
            GuiUtils.getInstance().setLastHoveredSlot(slotNumber);
            if (SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).isToggled() &&
                    SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).getProfileLockedSlots().contains(slotNumber) &&
                    (slotNumber >= 9 || Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerPlayer && slotNumber >= 5)) {
                Slot slot = $instance.getSlotUnderMouse();
                Gui.drawRect(slot.xDisplayPosition,
                        slot.yDisplayPosition,
                        slot.xDisplayPosition + 16,
                        slot.yDisplayPosition + 16,
                        new Color(255, 0, 0, 120).getRGB());
            }
        }
    }

    @Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true)
    public void keyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).isToggled() && (keyCode != 1 && keyCode != mc.gameSettings.keyBindInventory.getKeyCode())) {
            int slot = GuiUtils.getInstance().getLastHoveredSlot();
            if (mc.thePlayer.inventory.getItemStack() == null) {
                for (int i = 0; i < 9; ++i) {
                    if (keyCode == mc.gameSettings.keyBindsHotbar[i].getKeyCode()) {
                        slot = i + 36;
                    }
                }
            }
            if (slot >= 9 || mc.thePlayer.openContainer instanceof ContainerPlayer && slot >= 5) {
                FeatureLockSlots lockSlots = SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class);
                if (lockSlots.getProfileLockedSlots().contains(slot)) {
                    if (keyCode == lockSlots.getLockSlotKeybinding().getKeyCode()) {
                        lockSlots.getProfileLockedSlots().remove(slot);
                        lockSlots.onSave();
                        lockSlots.onSave();
                    } else {
                        ci.cancel();
                        return;
                    }
                } else {
                    if (keyCode == lockSlots.getLockSlotKeybinding().getKeyCode()) {
                        lockSlots.getProfileLockedSlots().add(slot);
                        lockSlots.onSave();
                        lockSlots.onSave();
                    }
                }
            }
        }
    }

    @Inject(method = "handleMouseClick", at = @At("HEAD"), cancellable = true)
    public void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo ci) {
        if (slotIn != null) {
            if (SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).isToggled() &&
                    SkyBlockBonus.getInstance().getFeatureManager().getFeature(FeatureLockSlots.class).getProfileLockedSlots().contains(slotIn.slotNumber + MinecraftUtils.getInstance().getSlotDifference(Minecraft.getMinecraft().thePlayer.openContainer))) {
                ci.cancel();
            }
        }
    }

}