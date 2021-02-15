package ga.matthewtgm.skyblockmod.utils;

import java.util.Arrays;

public class ActionBarParser {

    private String health;
    private String maxHealth;
    private String mana;
    private String maxMana;
    private String defense;

    public String parse(String actionBar) {
        String[] splitMessage = actionBar.split(" {3,}");

        for (String section : splitMessage) {
            try {
                String returned = parseSection(section);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return actionBar;
    }

    private String parseSection(String section) {
        String numbersOnly = TextUtils.getNumbersOnly(section).trim();
        String[] splitStats = numbersOnly.split("/");

        if (section.contains("\u2764")) {
            return parseHealth(section, splitStats);
        } else if (section.contains("\u2748")) {
            return parseDefense(section, numbersOnly);
        } else if (section.contains("\u270E")) {
            return parseMana(section, splitStats);
        }

        return section;
    }

    public String parseHealth(String section, String[] statsSplit) {
        String returnString = section;
        int newHealth;
        int maxHealth;
        if (section.startsWith("\u00A76")) {
            section = section.trim().substring(2).trim();
            statsSplit[0] = statsSplit[0].trim().substring(1).trim();
        }
        if (section.contains("+")) {
            String[] splitHealthWand = section.split("\\+");
            String[] split = TextUtils.getNumbersOnly(splitHealthWand[0]).split("/");
            newHealth = Integer.parseInt(split[0]);
            maxHealth = Integer.parseInt(split[1]);
        } else {
            newHealth = Integer.parseInt(statsSplit[0]);
            maxHealth = Integer.parseInt(statsSplit[1]);
        }
        setHealth(String.valueOf(newHealth));
        setMaxHealth(String.valueOf(maxHealth));
        return returnString;
    }

    public String parseMana(String section, String[] splitStats) {
        int mana = Integer.parseInt(splitStats[0]);
        int maxMana = Integer.parseInt(splitStats[1]);
        setMana(String.valueOf(mana));
        setMaxMana(String.valueOf(maxMana));
        return section;
    }

    public String parseDefense(String section, String numbersOnly) {
        int defense = Integer.parseInt(numbersOnly);
        setDefense(String.valueOf(defense));
        return section;
    }

    public String getHealth() {
        return health;
    }

    public String getMaxHealth() {
        return maxHealth;
    }

    public String getMana() {
        return mana;
    }

    public String getMaxMana() {
        return maxMana;
    }

    public String getDefense() {
        return defense;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setMaxHealth(String maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }

    public void setMaxMana(String maxMana) {
        this.maxMana = maxMana;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

}