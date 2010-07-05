package org.jtheque.resources.able;

import org.jtheque.resources.impl.Library;
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

    /**
     * Return all the files of the resources.
     *
     * @return A List containing all the files of the resource. 
     */
    List<String> getFiles();

    /**
     * Return all the libraries of the resource.
     *
     * @return A List containing all the libraries of the resource.
     */
    List<Library> getLibraries();

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
