package ga.matthewtgm.skyblockmod.custommenus.impl;

import ga.matthewtgm.skyblockmod.custommenus.CustomMenu;

public class CustomSkyBlockMenu extends CustomMenu {

    @Override
    public void initGui() {
        System.out.println("Debug!");
    }

    @Override
    public AbstractGuiRunnable getGuiRunnable() {
        return new AbstractGuiRunnable(this) {
            @Override
            public void onScreenDrawn(int mouseX, int mouseY, float partialTicks) {
                fontRendererObj.drawString("Test", 2, 2, -1);
            }
        };
    }

    @Override
    public String parentName() {
        return "SkyBlock Menu";
    }

}