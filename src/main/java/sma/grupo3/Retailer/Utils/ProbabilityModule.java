package sma.grupo3.Retailer.Utils;

public class ProbabilityModule {
    private static final int malfunctionProb = Configuration.getInt("TRANSPORTER_MALFUNCTION_PROB");

    public static boolean transporterMalfunction() {
        int randomChance = Randomizer.randomInt(0, 101);
        return randomChance < malfunctionProb;
    }
}
