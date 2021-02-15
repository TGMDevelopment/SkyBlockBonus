package ga.matthewtgm.skyblockmod.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class TextUtils {

    private static final Pattern NUMBERS_SLASHES = Pattern.compile("[^0-9 /]");
    private static final Pattern SCOREBOARD_CHARACTERS = Pattern.compile("[^a-z A-Z:0-9/'.]");

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.##");

    public static String keepScoreboardCharacters(String text) {
        return SCOREBOARD_CHARACTERS.matcher(text).replaceAll("");
    }

    public static String getNumbersOnly(String text) {
        return NUMBERS_SLASHES.matcher(text).replaceAll("");
    }

}