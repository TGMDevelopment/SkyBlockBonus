package ga.matthewtgm.skyblockmod.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.gui.GuiTransImageButton;
import ga.matthewtgm.skyblockmod.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiSkyBlockMod extends GuiScreen {

    private GuiButton patreon;
    private GuiButton youtube;
    private GuiButton discord;

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Close"));
        this.buttonList.add(new GuiTransButton(1, this.width - 80, 0, 80, 20, "HUD Editor"));
        this.buttonList.add(new GuiTransButton(2, this.width - 80, 20, 80, 20, "Features"));

        this.buttonList.add(patreon = new GuiTransImageButton(100, 10, this.height - 30, 30, 30, new ResourceLocation(Constants.ID, "textures/patreon.png")));
        this.buttonList.add(youtube = new GuiTransImageButton(101, 40, this.height - 30, 30, 30, new ResourceLocation(Constants.ID, "textures/youtube.png")));
        this.buttonList.add(discord = new GuiTransImageButton(102, 70, this.height - 30, 30, 30, new ResourceLocation(Constants.ID, "textures/discord.png")));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(new GuiHudEditor(this));
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(new GuiFeatureConfigurations(this));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.renderHoverText(mouseX, mouseY);
        GlStateManager.pushMatrix();
        int scale = 3;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.GREEN + Constants.NAME, width / 2 / scale, 5 / scale + 10, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void renderHoverText(int mouseX, int mouseY) {
        if ((mouseX >= patreon.xPosition && mouseX <= patreon.width + patreon.xPosition) && (mouseY >= patreon.yPosition && mouseY <= patreon.height + patreon.yPosition)) {
            this.fontRendererObj.drawString(EnumChatFormatting.GOLD + "Patreon", patreon.xPosition - 3, patreon.yPosition - 10, -1);
        }

        if ((mouseX >= youtube.xPosition && mouseX <= youtube.width + youtube.xPosition) && (mouseY >= youtube.yPosition && mouseY <= youtube.height + youtube.yPosition)) {
            this.fontRendererObj.drawString(EnumChatFormatting.RED + "YouTube", youtube.xPosition - 4, youtube.yPosition - 10, -1);
        }

        if ((mouseX >= discord.xPosition && mouseX <= discord.width + discord.xPosition) && (mouseY >= discord.yPosition && mouseY <= discord.height + discord.yPosition)) {
            this.fontRendererObj.drawString(EnumChatFormatting.LIGHT_PURPLE + "Discord", discord.xPosition - 3, discord.yPosition - 10, -1);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}