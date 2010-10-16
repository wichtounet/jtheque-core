package org.jtheque.views.windows;

import org.jtheque.ui.WindowView;

/**
 * An error view specification.
 *
 * @author Baptiste Wicht
 */
public interface ErrorView extends WindowView {
    /**
     * Indicate if the error view is not empty.
     *
     * @return {@code true} if the error view is not empty otherwise {@code false}.
     */
    boolean isNotEmpty();
}
