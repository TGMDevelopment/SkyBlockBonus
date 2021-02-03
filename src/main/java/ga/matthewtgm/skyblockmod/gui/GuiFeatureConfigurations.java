package ga.matthewtgm.skyblockmod.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

public class GuiFeatureConfigurations extends GuiScreen {

    private final GuiScreen parent;

    public GuiFeatureConfigurations(GuiScreen parent) {
        this.parent = parent;
    }

    public GuiScreen getParent() {
        return parent;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, (parent == null ? "Close" : "Back")));
        this.setupElementButtons("init", null);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            Minecraft.getMinecraft().displayGuiScreen(this.getParent());
        }
        this.setupElementButtons("action", button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        GlStateManager.pushMatrix();
        int scale = 3;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.GREEN + Constants.NAME, width / 2 / scale, 5 / scale + 10, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void setupElementButtons(String type, GuiButton button) {
        int offset = 20;
        int offsetX = 0;
        for (Feature feature : SkyBlockBonus.getInstance().getFeatureManager().getFeatures()) {
            if (type.equalsIgnoreCase("init")) {
                this.buttonList.add(new GuiTransButton(SkyBlockBonus.getInstance().getFeatureManager().getFeatures().indexOf(feature) + 1, offsetX, this.height - offset, 100, 20, feature.getName()));
                offset = offset + 20;
                if (offset > 240) {
                    offsetX = this.width - 100;
                    offset = 20;
                }
                if (offset > SkyBlockBonus.getInstance().getFeatureManager().getFeatures().size() * 20) offset = 20;
            } else if (type.equalsIgnoreCase("action")) {
                if (button.id == SkyBlockBonus.getInstance().getFeatureManager().getFeatures().indexOf(feature) + 1) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiFeature(feature, this));
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}