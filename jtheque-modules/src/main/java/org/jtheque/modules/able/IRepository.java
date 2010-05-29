package org.jtheque.modules.able;

import org.jtheque.utils.bean.InternationalString;

import java.util.Collection;

/**
 * A repository. It's simply an online repository to provide a list of modules for the application.
 *
 * @author Baptiste Wicht
 */
public interface IRepository {
    /**
     * Return the title of the repository.
     *
     * @return The title of the repository.
     */
    InternationalString getTitle();

    /**
     * Return the application name.
     *
     * @return The application name.
     */
    String getApplication();

    /**
     * Return all the modules of the repository.
     *
     * @return A List containing the description of the modules.
     */
    Collection<IModuleDescription> getModules();
}
