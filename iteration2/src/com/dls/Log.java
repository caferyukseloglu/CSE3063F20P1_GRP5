package com.dls;
import java.util.logging.*;

/**
 * The Log class logs significant messages to the given path.
 * @version iteration-2.0
 * @since 2020-12-01
 *
 */
public class Log {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /*
     * Construct method of the Label class
     * @return                          nothing
     * Different Levels in order.
     *    OFF
     *    SEVERE
     *    WARNING
     *    INFO
     *    CONFIG
     *    FINE
     *    FINER
     *    FINEST
     *    ALL
     */
    public static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);
        try {
            FileHandler file = new FileHandler("Logger.txt", true);
            file.setLevel(Level.FINE);
            logger.addHandler(file);
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "File logger not working.", e);
        }
    }
}