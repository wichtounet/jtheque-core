package org.jtheque.resources.able;

import org.jtheque.resources.impl.Library;
import org.jtheque.utils.bean.Version;

import java.util.List;

public interface IResource {
    String getId();

    List<String> getFiles();

    List<Library> getLibraries();

    /**
     * Return the version of the resource.
     *
     * @return The version of the resource.
     */
    Version getVersion();

    String getUrl();

    boolean isInstalled();
}
