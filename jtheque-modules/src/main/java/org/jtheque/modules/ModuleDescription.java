package org.jtheque.modules;

import org.jtheque.core.Versionable;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;

/**
 * A module description. This description is acquired from a repository.
 *
 * @author Baptiste Wicht
 */
public interface ModuleDescription extends Versionable {
    /**
     * Return the id of the module.
     *
     * @return The id of the module.
     */
    String getId();

    /**
     * Return the name of the module.
     *
     * @return The name of the module.
     */
    String getName();

    /**
     * Return the description of the module.
     *
     * @return The description of the module.
     */
    InternationalString getDescription();

    /**
     * Return the core version.
     *
     * @return The version of the core.
     */
    Version getCoreVersion();
}
