package org.jtheque.core.utils;

/**
 * A web helper. It's a simple utility class to test for reachability of URLs. It automatically log exceptions
 * and add errors to the view if the URL is not reachable. 
 *
 * @author Baptiste Wicht
 */
public interface WebHelper {
    /**
     * Indicate if the descriptor is reachable. The errors are automatically logged. 
     *
     * @param url The descriptors url to test for reachability.
     *
     * @return true if the descriptor is reachable else false.
     */
    boolean isReachable(String url);
}
