package ga.matthewtgm.skyblockmod.gui;

import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

public class GuiFeature extends GuiScreen {

    private final Feature feature;
    private final GuiScreen parent;

    public GuiFeature(Feature feature, GuiScreen parent) {
        this.feature = feature;
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.parent == null ? "Close" : "Back"));
        this.buttonList.add(new GuiTransButton(1, this.width / 2 - 50, this.height / 2, 100, 20, "Toggle: " + (this.feature.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(this.getParent());
                break;
            case 1:
                this.feature.setToggleState(!this.feature.isToggled());
                GuiScreenUtils.getInstance().fixDisplayText(button, "Toggle: " + (this.feature.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
                if (this.feature.isToggled()) {
                    this.feature.onEnabled();
                } else {
                    this.feature.onDisabled();
                }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.feature.onRendered(new FeaturePosition(this.width / 2, 0));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        this.getFeature().onSave(new JsonObject());
        this.getFeature().onLoad();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public Feature getFeature() {
        return feature;
    }

    public GuiScreen getParent() {
        return parent;
    }

}