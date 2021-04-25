package ga.matthewtgm.skyblockmod.gui.features;

import ga.matthewtgm.lib.gui.BetterGuiScreen;
import ga.matthewtgm.lib.gui.components.GuiTransButton;
import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;
import ga.matthewtgm.skyblockmod.runnables.GuiButtonRunnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

public class GuiFeature extends BetterGuiScreen {

    private final Feature feature;
    private final GuiScreen parent;

    public GuiFeature(Feature feature, GuiScreen parent) {
        this.feature = feature;
        this.parent = parent;
    }

    @Override
    public void initGui() {
        buttonList.add(new GuiTransButton(0, width / 2 - 50, height - 20, 100, 20, parent == null ? "Close" : "Back"));
        buttonList.add(new GuiTransButton(1, width / 2 - 50, height / 2 - 50, 100, 20, "Toggle: " + (feature.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        if(this.feature.isRendered()) {
            buttonList.add(new GuiTransButton(2, this.width / 2 - 50, this.height / 2 - 20, 100, 20, "Colour"));
        }

        for (GuiButtonRunnable runnable : this.feature.getAdditionalConfigButtons())
            buttonList.add(new GuiTransButton(runnable.getId(), runnable.getX(), runnable.getY(), runnable.getWidth(), runnable.getHeight(), runnable.getText()));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(this.getParent());
                break;
            case 1:
                feature.setToggleState(!feature.isToggled());
                GuiScreenUtils.getInstance().fixDisplayText(button, "Toggle: " + (feature.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
                if (feature.isToggled()) {
                    feature.onEnabled();
                } else {
                    feature.onDisabled();
                }
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(new GuiFeatureColour(this.getFeature(), this));
        }
        for (GuiButtonRunnable runnable : feature.getAdditionalConfigButtons())
            if (button.id == runnable.getId()) runnable.onActionPerformed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        feature.onRendered(new FeaturePosition(this.width / 2, 0));
        super.drawComponents(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        getFeature().onSave();
        getFeature().onLoad();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public Feature getFeature() {
        return feature;
    }

    @Override
    public GuiScreen getParent() {
        return parent;
    }

}