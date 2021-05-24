package sma.grupo3.Retailer.Utils;

public class ConsoleRainbow {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private static String getCallerCallerClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    private static String processMessage(String m) {
        return "[" + getCallerCallerClassName() + "]::" + m;
    }


    public static void warning(String m) {
        System.out.println(ANSI_RED + processMessage(m) + ANSI_RESET);
    }

    public static void good(String m) {
        System.out.println(ANSI_GREEN + processMessage(m) + ANSI_RESET);
    }

    public static void alert(String m) {
        System.out.println(ANSI_YELLOW + processMessage(m) + ANSI_RESET);
    }

    public static void fail(String m) {
        System.out.println(ANSI_RED_BACKGROUND + ANSI_BLACK + processMessage(m) + ANSI_RESET);
    }

    public static void success(String m) {
        System.out.println(ANSI_GREEN_BACKGROUND + ANSI_BLACK + processMessage(m) + ANSI_RESET);
    }
}
