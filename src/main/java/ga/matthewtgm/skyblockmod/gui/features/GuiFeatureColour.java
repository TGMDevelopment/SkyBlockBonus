package ga.matthewtgm.skyblockmod.gui.features;

import ga.matthewtgm.lib.gui.components.GuiTransButton;
import ga.matthewtgm.lib.gui.components.GuiTransSlider;
import ga.matthewtgm.skyblockmod.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiFeatureColour extends GuiScreen {

    private final Feature feature;
    private final GuiScreen parent;

    public GuiFeatureColour(Feature feature, GuiScreen screen) {
        this.feature = feature;
        this.parent = screen;
    }

    private GuiSlider rSlider;
    private GuiSlider gSlider;
    private GuiSlider bSlider;

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.parent == null ? "Save and close" : "Save and go back"));
        this.buttonList.add(rSlider = new GuiTransSlider(1, this.width / 2 - 105, this.height / 2 - 70, 100, 20, "Red: ", "", 0, 255, this.feature.colour.getR(), false, true));
        this.buttonList.add(gSlider = new GuiTransSlider(2, this.width / 2 + 5, this.height / 2 - 70, 100, 20, "Green: ", "", 0, 255, this.feature.colour.getG(), false, true));
        this.buttonList.add(bSlider = new GuiTransSlider(3, this.width / 2 - 105, this.height / 2 - 40, 100, 20, "Blue: ", "", 0, 255, this.feature.colour.getB(), false, true));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(this.getParent());
                break;
            case 1:
                this.feature.colour.setR(this.rSlider.getValueInt());
                break;
            case 2:
                this.feature.colour.setG(this.gSlider.getValueInt());
                break;
            case 3:
                this.feature.colour.setB(this.bSlider.getValueInt());
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.feature.colour.setR(this.rSlider.getValueInt());
        this.feature.colour.setG(this.gSlider.getValueInt());
        this.feature.colour.setB(this.bSlider.getValueInt());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        this.feature.onSave();
        this.feature.onLoad();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public GuiScreen getParent() {
        return parent;
    }

}