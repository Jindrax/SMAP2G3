package sma.grupo3.Retailer.Utils;

import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

public class Randomizer {
    private static final SecureRandom random = new SecureRandom();
    private static final SplittableRandom splitRandom = new SplittableRandom();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static Localities randomLocality() {
        List<Localities> available = Arrays.stream(Localities.values()).filter(localities -> localities.enable).collect(Collectors.toList());
        Collections.shuffle(available);
        return available.get(0);
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

    public static long randomLong(long upperLimit) {
        return splitRandom.nextLong(upperLimit);
    }
}
