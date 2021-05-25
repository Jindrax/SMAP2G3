package sma.grupo3.Retailer.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Configuration {

    private static Map<String, String> loadEnv() {
        Map<String, String> newEnv = new Hashtable<>();
        try {
            Scanner reader = new Scanner(new File("config/.env"));
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] atoms = data.split("=");
                newEnv.put(atoms[0], atoms[1]);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return newEnv;
    }

    private static final Map<String, String> env = loadEnv();

    public static int getInt(String key) {
        return Integer.parseInt(env.get(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(env.get(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(env.get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(env.get(key));
    }

    public static String get(String key) {
        return env.get(key);
    }

}
