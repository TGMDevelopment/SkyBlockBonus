package ga.matthewtgm.skyblockmod.skyblock;

public enum EnumLocation {

    ISLAND("Your Island"),
    GUEST_ISLAND("'s Island"),
    AUCTION_HOUSE("Auction House"),
    BANK("Bank"),
    BAZAAR("Bazaar Alley"),
    CANVAS_ROOM("Canvas Room"),
    COAL_MINE("Coal Mine"),
    COLOSSEUM("Colosseum"),
    COLOSSEUM_ARENA("Colosseum Arena"),
    DUEL_ZONE("Duel Zone"),
    FARM("Farm"),
    FASHION_SHOP("Fashion Shop"),
    FISHERMANS_HUT("Fisherman's Hut"),
    FLOWER_HOUSE("Flower House"),
    FOREST("Forest"),
    GRAVEYARD("Graveyard"),
    HIGH_LEVEL("High Level"),
    LIBRARY("Library"),
    MOUNTAIN("Mountain"),
    RUINS("Ruins"),
    TAVERN("Tavern"),
    VILLAGE("Village"),
    WILDERNESS("Wilderness"),
    WIZARD_TOWER("Wizard Tower"),
    BIRCH_PARK("Birch Park"),
    SPRUCE_WOODS("Spruce Woods"),
    SAVANNA_WOODLAND("Savanna Woodland"),
    DARK_THICKET("Dark Thicket"),
    JUNGLE_ISLAND("Jungle Island"),
    GOLD_MINE("Gold Mine"),
    DEEP_CAVERNS("Deep Caverns"),
    GUNPOWDER_MINES("Gunpowder Mines"),
    LAPIS_QUARRY("Lapis Quarry"),
    PIGMAN_DEN("Pigmen's Den"),
    SLIMEHILL("Slimehill"),
    DIAMOND_RESERVE("Diamond Reserve"),
    OBSIDIAN_SANCTUARY("Obsidian Sanctuary"),
    THE_BARN("The Barn"),
    MUSHROOM_DESERT("Mushroom Desert"),
    SPIDERS_DEN("Spider's Den"),
    BLAZING_FORTRESS("Blazing Fortress"),
    THE_END("The End"),
    DRAGONS_NEST("Dragon's Nest"),
    JERRY_POND("Jerry Pond"),
    JERRYS_WORKSHOP("Jerry's Workshop"),
    DUNGEON("Dungeon"),
    DUNGEON_HUB("Dungeon Hub"),
    DWARVEN_MINES("Dwarven Mines"),
    NONE("None"),
    UNKNOWN("Unknown");

    private String scoreboardName;

    EnumLocation(String scoreboardName) {
        this.scoreboardName = scoreboardName;
    }

    public String getScoreboardName() {
        return scoreboardName;
    }

}