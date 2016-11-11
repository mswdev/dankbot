package client.api.util;

/**
 * Created by Sphiinx on 11/5/2016.
 */
public class Logging {

    /**
     * Prints the specified message via the Console with the header "Debug: ".
     *
     * @param message The message that is being printed.
     */
    public static void debug(String message) {
        System.out.println("[DEBUG]: " + message);
    }

    /**
     * Prints the specified message via the Console with the header "ERROR: ".
     *
     * @param message The message that is being printed.
     */
    public static void error(String message) {
        System.out.println("[ERROR]: " + message);
    }

    /**
     * Prints the specified message via the Console with the header "WARNING: "
     *
     * @param message The message that is being printed.
     */
    public static void warning(String message) {
        System.out.println("[WARNING]: " + message);
    }

    /**
     * Prints the specified message via the Console with the header "Status: ".
     *
     * @param message The message that is being printed.
     */
    public static void status(String message) {
        System.out.println("[STATUS]: " + message);
    }

}

