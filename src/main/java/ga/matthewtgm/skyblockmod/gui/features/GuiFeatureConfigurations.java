package ga.matthewtgm.skyblockmod.gui.features;

import ga.matthewtgm.lib.gui.components.GuiTransButton;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;

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
        this.setupCategoryButtons("init", null);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            Minecraft.getMinecraft().displayGuiScreen(this.getParent());
        }
        this.setupCategoryButtons("action", button);
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

    private void setupCategoryButtons(String type, GuiButton button) {
        int offset = this.height / 2 - 50;
        for (FeatureCategory category : FeatureCategory.values()) {
            if (type.equals("init")) {
                this.buttonList.add(new GuiTransButton(Arrays.asList(FeatureCategory.values()).indexOf(category) + 1, this.width / 2 - 50, this.height - offset, 100, 20, category.getProperName()));
                offset += 25;
                if (offset > ((this.height / 2) / Arrays.asList(FeatureCategory.values()).size() * 20)) {
                    offset = this.height / 2 - 50;
                }
            } else if (type.equals("action")) {
                if (button.id == Arrays.asList(FeatureCategory.values()).indexOf(category) + 1) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiFeatureCategory(category, this));
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}