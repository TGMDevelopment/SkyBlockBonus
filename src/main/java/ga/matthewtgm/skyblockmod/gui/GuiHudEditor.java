package ga.matthewtgm.skyblockmod.gui;

import ga.matthewtgm.lib.gui.components.GuiTransButton;
import ga.matthewtgm.lib.util.RenderUtils;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

public class GuiHudEditor extends GuiScreen {

    private final Logger logger = LogManager.getLogger(Constants.NAME + " (" + this.getClass().getSimpleName() + ")");
    private final GuiScreen parent;
    private boolean dragging;
    private Optional<Feature> selected = Optional.empty();

    private GuiButton selectedButton;

    private int prevX, prevY;

    public GuiHudEditor(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.parent == null ? "Close" : "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) Minecraft.getMinecraft().displayGuiScreen(this.parent);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.updateFeaturePosition(mouseX, mouseY);
        for (Feature f : SkyBlockBonus.getInstance().getFeatureManager().getFeatures()) {
            if (f.isToggled() && f.isRendered()) {
                final RenderUtils utils = new RenderUtils();
                utils.drawHollowRect(f.getRenderedPosition().getX() - 1, f.getRenderedPosition().getY() - 2, f.width, f.height, new Color(255, 255, 255, 111).getRGB());
                Gui.drawRect(f.getRenderedPosition().getX() - 1, f.getRenderedPosition().getY() - 2, f.getRenderedPosition().getX() + f.width, f.getRenderedPosition().getY() + f.height, new Color(255, 255, 255, 43).getRGB());
                f.onRendered(f.getRenderedPosition());
            }
        }
        GlStateManager.pushMatrix();
        int scale = 3;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.GREEN + Constants.NAME, width / 2 / scale, 5 / scale + 10, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.prevX = mouseX;
        this.prevY = mouseY;
        this.selected = SkyBlockBonus.getInstance().getFeatureManager().getFeatures().stream().filter(new MouseHoveringFeature(mouseX, mouseY)).findFirst();

        if (selected.isPresent()) {
            this.dragging = true;
        }

        if (mouseButton == 0 && !this.selected.isPresent()) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                        break;
                    guibutton = event.button;
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
    }

    @Override
    public void onGuiClosed() {
        for (Feature feature : SkyBlockBonus.getInstance().getFeatureManager().getFeatures()) {
            feature.onSave();
            feature.onLoad();
        }
    }

    protected void updateFeaturePosition(int x, int y) {
        if (selected.isPresent() && this.dragging) {
            Feature element = selected.get();
            FeaturePosition position = element.getRenderedPosition();
            position.setPosition(position.getX() + x - this.prevX, position.getY() + y - this.prevY);
        }
        this.prevX = x;
        this.prevY = y;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private static class MouseHoveringFeature implements Predicate<Feature> {

        private final int x, y;

        public MouseHoveringFeature(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public boolean test(Feature element) {
            if(element.isRendered()) {
                FeaturePosition position = element.getRenderedPosition();
                int posX = position.getX();
                int posY = position.getY();
                if (x >= posX && x <= posX + element.width) {
                    return y >= posY && y <= posY + element.height;
                }
            }
            return false;
        }
    }

}