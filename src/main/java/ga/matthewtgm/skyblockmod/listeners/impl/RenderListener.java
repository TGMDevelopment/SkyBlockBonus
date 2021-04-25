package ga.matthewtgm.skyblockmod.listeners.impl;

import ga.matthewtgm.skyblockmod.events.EntityNametagRenderEvent;
import ga.matthewtgm.skyblockmod.listeners.SkyBlockModListener;
import ga.matthewtgm.skyblockmod.utils.MainUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RenderListener extends SkyBlockModListener {

    private static RenderListener INSTANCE;

    public static RenderListener getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RenderListener();
        return INSTANCE;
    }

    private String title;
    private int titleTicks;

    @SubscribeEvent
    protected void onArmourStandRendered(RenderLivingEvent.Pre<EntityArmorStand> event) {
        if (event.entity instanceof EntityArmorStand && event.entity.hasCustomName() && event.entity.isInvisible()) {
            EntityNametagRenderEvent event1 = new EntityNametagRenderEvent(event.entity.getCustomNameTag(), event.entity);
            if (!event1.isCanceled()) {
                MinecraftForge.EVENT_BUS.post(event1);
            } else {
                event.setCanceled(true);
            }
        }
    }

    /**
     * Adapted from Skytils under GNU v3.0 license
     * @link https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author SkytilsMod
     */
    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        if (titleTicks > 0) {
            titleTicks--;
        } else {
            titleTicks = 0;
            title = null;
        }
    }

    @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
        renderTitles(event.resolution);
    }

    /**
     * Adapted from SkyblockAddons under MIT license
     * @link https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     */
    private void renderTitles(ScaledResolution scaledResolution) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        int scaledWidth = scaledResolution.getScaledWidth();
        int scaledHeight = scaledResolution.getScaledHeight();
        if (title != null) {
            int stringWidth = mc.fontRendererObj.getStringWidth(title);

            float scale = 4; // Scale is normally 4, but if its larger than the screen, scale it down...
            if (stringWidth * scale > (scaledWidth * 0.9F)) {
                scale = (scaledWidth * 0.9F) / (float) stringWidth;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) (scaledWidth / 2), (float) (scaledHeight / 2), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale); // TODO Check if changing this scale breaks anything...

            mc.fontRendererObj.drawString(title, (float) (-mc.fontRendererObj.getStringWidth(title) / 2), -20.0F, 0xFF0000, true);

            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            GlStateManager.color(1, 1, 1);
            mc.getTextureManager().bindTexture(Gui.icons);
        }
    }

    public void setRenderedTitle(String title, int ticks) {
        this.title = title;
        this.titleTicks = ticks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleTicks() {
        return titleTicks;
    }

    public void setTitleTicks(int titleTicks) {
        this.titleTicks = titleTicks;
    }

}