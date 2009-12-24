package org.jtheque.core.managers.log;

/**
 * A JTheque Logger. The logger is based on SLF4J and the real implementation is the SLF4J Log4J bridge
 * functionality.
 *
 * @author Baptiste Wicht
 */
public interface IJThequeLogger {
    /**
     * Log a message.
     *
     * @param message  The message to log.
     * @param replaces The objects to replace the {} occurrences.
     */
    void message(String message, Object... replaces);

    /**
     * Log a message of level debug.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void debug(String message, Object... replaces);

    /**
     * Log a message of level trace.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void trace(String message, Object... replaces);

    /**
     * Log a message of level warning.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void warn(String message, Object... replaces);

    /**
     * Log an exception.
     *
     * @param e The exception to log
     */
    void error(Throwable e);

    /**
     * Log a message of level error.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void error(String message, Object... replaces);

    /**
     * Log a message and an exception with error level.
     *
     * @param message The message to log.
     * @param e       The exception to log.
     */
    void error(Exception e, String message);
}
