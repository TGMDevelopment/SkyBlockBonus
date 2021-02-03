package ga.matthewtgm.skyblockmod.skyblock;

public enum Rarity {

    COMMON("COMMON"),
    UNCOMMON("UNCOMMON"),
    RARE("RARE"),
    EPIC("EPIC"),
    LEGENDARY("LEGENDARY"),
    MYTHIC("MYTHIC"),
    SPECIAL("SPECIAL"),
    VERY_SPECIAL("VERY SPECIAL"),
    SUPREME("SUPREME");

    public String rarity;

    Rarity(String rarity) {
        this.rarity = rarity;
    }

}