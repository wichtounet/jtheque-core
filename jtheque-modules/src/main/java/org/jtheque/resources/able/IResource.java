package org.jtheque.resources.able;

import org.jtheque.resources.impl.Library;
import org.jtheque.utils.bean.Version;

import java.util.List;

public interface IResource {
    /**
     * Return the id of the resource.
     * @return The id of the resource.
     */
    String getId();

    List<String> getFiles();

    List<Library> getLibraries();

    /**
     * Return the version of the resource.
     *
     * @return The version of the resource.
     */
    Version getVersion();

    /**
     * Return the URL of the resource.
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
