package sma.grupo3.Retailer.Utils;

import java.security.SecureRandom;

public class Randomizer {
    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomInt(int upperLimit) {
        return random.nextInt(upperLimit);
    }

    public static int randomInt(int lowerLimit, int upperLimit) {
        return random.nextInt(upperLimit) + lowerLimit;
    }
}
