package org.jtheque.core.utils;

/**
 * Created by IntelliJ IDEA. User: wichtounet Date: Sep 13, 2010 Time: 2:01:47 PM To change this template use File |
 * Settings | File Templates.
 */
public interface WebHelper {
    /**
     * Indicate if the descriptor is not reachable.
     *
     * @param url The descriptors url to test for reachability.
     *
     * @return true if the descriptor is not reachable else false.
     */
    boolean isNotReachable(String url);
}
