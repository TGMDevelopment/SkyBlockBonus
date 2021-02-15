package ga.matthewtgm.skyblockmod.gui.features;

import ga.matthewtgm.lib.gui.components.GuiTransButton;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

public class GuiFeatureCategory extends GuiScreen {

    private final FeatureCategory category;
    private final GuiScreen parent;

    public GuiFeatureCategory(FeatureCategory category, GuiScreen screen) {
        this.category = category;
        this.parent = screen;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.parent == null ? "Close" : "Back"));
        this.setupFeatureButtons("init", null);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0)
            Minecraft.getMinecraft().displayGuiScreen(this.getParent());
        this.setupFeatureButtons("action", button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        GlStateManager.pushMatrix();
        int scale = 3;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.GREEN + this.category.getProperName(), width / 2 / scale, 5 / scale + 10, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void setupFeatureButtons(String type, GuiButton button) {
        int offset = this.height / 2 - 50;
        int offsetX = this.width / 2 - 50;
        for (Feature feature : SkyBlockBonus.getInstance().getFeatureManager().getFeatures()) {
            if (feature.getCategory() == this.category) {
                if (type.equalsIgnoreCase("init")) {
                    this.buttonList.add(new GuiTransButton(SkyBlockBonus.getInstance().getFeatureManager().getFeatures().indexOf(feature) + 1, offsetX, this.height - offset, 100, 20, feature.getName()));
                    offset += 25;
                    if (offset > ((this.height / 2) / SkyBlockBonus.getInstance().getFeatureManager().getFeatures().size() * 20)) {
                        offsetX = this.width / 2 + 5;
                        offset = this.height / 2 - 50;
                    }
                } else if (type.equalsIgnoreCase("action")) {
                    if (button.id == SkyBlockBonus.getInstance().getFeatureManager().getFeatures().indexOf(feature) + 1) {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiFeature(feature, this));
                    }
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public GuiScreen getParent() {
        return parent;
    }

}