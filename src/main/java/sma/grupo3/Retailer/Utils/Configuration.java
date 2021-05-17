package sma.grupo3.Retailer.Utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {
    private static Dotenv dotenv = Dotenv.configure().directory("src/main/resources/.env").load();

    public static int getInt(String key) {
        return Integer.parseInt(dotenv.get(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(dotenv.get(key));
    }

    public static String get(String key) {
        return dotenv.get(key);
    }

}
