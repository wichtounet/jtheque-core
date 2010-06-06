package org.jtheque.resources.able;

import org.jtheque.resources.impl.Library;
import org.jtheque.utils.bean.Version;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: Jun 6, 2010
 * Time: 8:27:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IResource {
    String getId();

    List<String> getFiles();

    List<Library> getLibraries();

    Version getVersion();

    String getUrl();

    boolean isInstalled();
}
