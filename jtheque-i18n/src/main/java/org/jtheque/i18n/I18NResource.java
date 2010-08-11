package org.jtheque.i18n;

import org.springframework.core.io.Resource;

/**
 * An i18n resource. Namely, it's a resource to a i18n file.
 *
 * @author Baptiste Wicht
 */
public interface I18NResource {
    /**
     * Return the filename of the resource.
     *
     * @return The filename of the resource.
     */
    String getFileName();

    /**
     * Return the resource to the i18n file.
     *
     * @return The resource to the i18n file.
     */
    Resource getResource();
}
