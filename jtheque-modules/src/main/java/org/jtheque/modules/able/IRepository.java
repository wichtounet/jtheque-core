package org.jtheque.modules.able;

import org.jtheque.utils.bean.InternationalString;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: May 15, 2010
 * Time: 4:31:50 PM
 * To change this template use File | Settings | File Templates.
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
