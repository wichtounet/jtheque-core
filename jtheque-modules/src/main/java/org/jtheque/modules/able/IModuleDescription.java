package org.jtheque.modules.able;

import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: May 15, 2010
 * Time: 4:34:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IModuleDescription {
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
	 * Return the versions file URL.
	 *
	 * @return The URL of the versions file.
	 */
	String getVersionsFileURL();

	/**
	 * Return the core version.
	 *
	 * @return The version of the core.
	 */
	Version getCoreVersion();

	/**
	 * Return the id of the module.
	 *
	 * @return The id of the module.
	 */
	String getId();
}
