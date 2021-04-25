package ga.matthewtgm.skyblockmod.features.impl.trackers.revstonetracker;

import ga.matthewtgm.lib.util.threading.ThreadRunnable;
import ga.matthewtgm.lib.util.threading.ThreadUtils;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;
import ga.matthewtgm.skyblockmod.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class FeatureReviveStoneTracker extends Feature {

    private int tickTimer = 1;
    private ResourceLocation revStoneTex = initRevStoneTex();
    private int revStoneCount = 0;

    public FeatureReviveStoneTracker() {
        super("Revive Stone Tracker", FeatureCategory.DUNGEONS, true);
    }

    @Override
    public void onEnabled() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisabled() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @Override
    public void onRendered(FeaturePosition position) {
        if (this.revStoneTex == null) {
            revStoneTex = initRevStoneTex();
        }

        String revStoneCountStr = String.valueOf(revStoneCount);

        Minecraft.getMinecraft().getTextureManager().bindTexture(this.revStoneTex);
        Gui.drawModalRectWithCustomSizedTexture(position.getX(), position.getY(), 0, 0, 16, 16, 16, 16);
        this.fontRenderer.drawString(revStoneCountStr, position.getX() + 18, position.getY() + 4, this.colour.getRGBA());
        this.width = 16 + 4 + this.fontRenderer.getStringWidth(revStoneCountStr);
        this.height = 16;
    }

    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tickTimer++;
            if (tickTimer % 300 == 0) {
                ThreadUtils.getInstance().runAsync(new ThreadRunnable() {
                    @Override
                    public void run() {
                        if (mc.thePlayer == null) return;
                        if (mc.thePlayer.inventory == null) return;
                        revStoneCount = 0;
                        for (ItemStack stack : mc.thePlayer.inventory.mainInventory) {
                            if (stack == null) return;
                            if (ItemStackUtils.getInstance().hasSkyBlockId(stack)) {
                                String id = ItemStackUtils.getInstance().getSkyBlockId(stack);
                                if (id.equals("REVIVE_STONE")) ++revStoneCount;
                            }
                        }
                    }
                });
            }
        }
    }

    private ResourceLocation initRevStoneTex() {
        try {
            return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("revive_stone.png", new DynamicTexture(ImageIO.read(new URL("https://raw.githubusercontent.com/TGMDevelopment/SkyBlock-Bonus-Data/main/features/revivestonetracker/revive_stone.png"))));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}