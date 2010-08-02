package org.jtheque.ui.able;

/**
 * The state of a JTheque Window.
 *
 * @author Baptiste Wicht
 */
public interface WindowState {
    /**
     * Make the window waiting.
     */
    void startWait();

    /**
     * Make the window stop waiting. 
     */
    void stopWait();
}
