package org.jtheque.core.managers.module.beans;

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

/**
 * A module based on collection choose. If a module implements this interface, the core will display
 * a collection choose view and after the choose of the collection, the plugCollection method is called.
 *
 * @author Baptiste Wicht
 */
public interface CollectionBasedModule {
    /**
     * Plug a collection. This method must only make the choose collection process and return the
     * result of the choose. This method must not expensive cost process launch. All the
     * module loading treatments must be made in the plugCollection() method.
     *
     * @param collection The collection to use.
     * @param password   The entered password.
     * @param create     Indicate if we must create the collection or only select it.
     * @return true if the collection choose process has been good processed.
     */
    boolean chooseCollection(String collection, String password, boolean create);

    /**
     * Plug the module after the collection choose. This method can expensive cost operations execute.
     * During this method, the view will not be blocked.
     */
    void plugCollection();
}
