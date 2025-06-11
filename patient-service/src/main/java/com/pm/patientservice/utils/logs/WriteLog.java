package com.pm.patientservice.utils.logs;

/**
 * Utility class for logging messages with different styles.
 * Provides methods to log information and errors with specific color codes.
 *
 * @author caito
 */
public class WriteLog {

    /**
     * Logs an informational message with yellow color.
     *
     * @param message the message to log
     * @return the formatted message with yellow color
     */
    public static String logInfo(String message) {
        return "\u001B[32m"+ message + "\u001B[0m";
    }

    /**
     * Logs an error message with red color.
     *
     * @param message the message to log
     * @return the formatted message with red color
     */
    public static String logError(String message) {
        return "\u001B[31m"+message + "\u001B[0m";
    }

    /**
     * Logs a warning message with yellow color.
     *
     * @param message the message to log
     * @return the formatted message with yellow color
     */
    public static String logWarning(String message) {
        return "\u001B[33m"+message + "\u001B[0m";
    }
}
