package org.jtheque.resources.able;

import org.jtheque.utils.bean.Version;

import java.util.List;

/**
 * A resource specification.
 *
 * @author Baptiste Wicht
 */
public interface IResource {
    /**
     * Return the id of the resource.
     *
     * @return The id of the resource.
     */
    String getId();

    List<SimpleResource> getResources();

    /**
     * Return the version of the resource.
     *
     * @return The version of the resource.
     */
    Version getVersion();

    /**
     * Return the URL of the resource.
     *
     * @return The URL of the resource.
     */
    String getUrl();

    /**
     * Indicate if the resource is installed or not.
     *
     * @return true if the resource is installed else false.
     */
    boolean isInstalled();
}
