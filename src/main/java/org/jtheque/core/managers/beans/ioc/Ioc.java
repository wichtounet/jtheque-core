package org.jtheque.core.managers.beans.ioc;

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
 * The IoC manager.
 *
 * @author Baptiste Wicht
 */
public final class Ioc {
    private static final IocContainer CONTAINER = new SpringContainer();

    /**
     * This is an utility class, not instanciable.
     */
    private Ioc() {
        super();
    }

    /**
     * Return the IoC container implementation.
     *
     * @return The IoC container implementation.
     */
    public static IocContainer getContainer() {
        return CONTAINER;
    }
}
