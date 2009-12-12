package org.jtheque.core.managers;

import javax.annotation.PostConstruct;
import java.util.Map;

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
 * A container for a list of managers.
 *
 * @author Baptiste Wicht
 */
public final class ManagerContainer {
    private final Map<Class<?>, IManager> managers;

    /**
     * Construct a new ManagerContainer.
     *
     * @param managers The managers list.
     */
    public ManagerContainer(Map<Class<?>, IManager> managers) {
        this.managers = managers;
    }

    /**
     * Return the managers.
     *
     * @return A List containing all the managers.
     */
    public Map<Class<?>, IManager> getManagers() {
        return managers;
    }

    /**
     * Transfer the manager's list to the Managers class.
     */
    @PostConstruct
    public void transfer() {
        Managers.loadManagers(this);
    }
}