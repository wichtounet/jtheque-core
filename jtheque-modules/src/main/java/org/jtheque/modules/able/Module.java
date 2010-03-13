package org.jtheque.modules.able;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.modules.able.ModuleState;
import org.jtheque.utils.bean.Version;
import org.osgi.framework.Bundle;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: Mar 3, 2010
 * Time: 6:27:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Module {
    /**
     * Return the real module.
     *
     * @return The real module.
     */
    Bundle getModule();

    /**
     * Return the current state of the module.
     *
     * @return The current state of the module.
     */
    ModuleState getState();

    /**
     * Return the id of the module.
     *
     * @return The id of the module.
     */
    String getId();

    /**
     * Return the name key of the module.
     *
     * @return The name key of the module.
     */
    String getName();

    /**
     * Return the author key of the module.
     *
     * @return The author key of the module.
     */
    String getAuthor();

    /**
     * Return the key description of the module.
     *
     * @return The key description of the module.
     */
    String getDescription();

    Version getVersion();

    String getI18n();

    Version getCoreVersion();

    String getUrl();

    String getUpdateUrl();

    String[] getBundles();

    String[] getDependencies();

    String getMessagesUrl();

    Version getMostRecentVersion();

    void setCollection(boolean collection);

    boolean isCollection();

    void setState(ModuleState state);
}
