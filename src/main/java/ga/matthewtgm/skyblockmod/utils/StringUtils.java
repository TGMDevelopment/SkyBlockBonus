package ga.matthewtgm.skyblockmod.utils;

import java.util.Random;

public class StringUtils {

    private static StringUtils INSTANCE;

    public static StringUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new StringUtils();
        return INSTANCE;
    }

    public String generateRandomAlphabeticString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}