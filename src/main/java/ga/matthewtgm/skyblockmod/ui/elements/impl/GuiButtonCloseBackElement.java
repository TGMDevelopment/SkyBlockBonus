package ga.matthewtgm.skyblockmod.ui.elements.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.net.URL;

public class GuiButtonCloseBackElement extends GuiImagedButtonElement {

    private final ResourceLocation xRl = initializeX();

    public GuiButtonCloseBackElement(int x, int y, int width, int height, GuiButtonElementInteract interaction, GuiButtonElementColour colour) {
        super(x, y, width, height, null, interaction, colour);
        setLocation(xRl);
    }

    private ResourceLocation initializeX() {
        try {
            return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("x.png", new DynamicTexture(ImageIO.read(new URL("https://raw.githubusercontent.com/TGMDevelopment/Common-Modding-Assets/main/gui/x.png"))));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}