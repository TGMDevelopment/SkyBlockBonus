package ga.matthewtgm.skyblockmod.skyblock;

import net.minecraft.util.EnumChatFormatting;

public enum Rarity {

    COMMON("COMMON", EnumChatFormatting.WHITE),
    UNCOMMON("UNCOMMON", EnumChatFormatting.GREEN),
    RARE("RARE", EnumChatFormatting.BLUE),
    EPIC("EPIC", EnumChatFormatting.DARK_PURPLE),
    LEGENDARY("LEGENDARY", EnumChatFormatting.GOLD),
    MYTHIC("MYTHIC", EnumChatFormatting.LIGHT_PURPLE),
    SPECIAL("SPECIAL", EnumChatFormatting.RED),
    VERY_SPECIAL("VERY SPECIAL", EnumChatFormatting.RED),
    SUPREME("SUPREME", EnumChatFormatting.DARK_RED);

    private final String rarity;
    private final EnumChatFormatting colour;

    Rarity(String rarity, EnumChatFormatting colour) {
        this.rarity = rarity;
        this.colour = colour;
    }

    public String getRarity() {
        return rarity;
    }

    public EnumChatFormatting getColour() {
        return colour;
    }

}