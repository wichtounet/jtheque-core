package org.jtheque.modules.impl;

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

import org.jtheque.utils.bean.InternationalString;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A module repository.
 *
 * @author Baptiste Wicht
 */
public final class Repository {
    private InternationalString title;
    private String application;
    private final Collection<ModuleDescription> modules = new ArrayList<ModuleDescription>(10);

    /**
     * Return the title of the repository.
     *
     * @return The title of the repository.
     */
    public InternationalString getTitle() {
        return title;
    }

    /**
     * Set the title of the repository.
     *
     * @param title The title of the repository.
     */
    public void setTitle(InternationalString title) {
        this.title = title;
    }

    /**
     * Return the application name.
     *
     * @return The application name.
     */
    public String getApplication() {
        return application;
    }

    /**
     * Set the application name.
     *
     * @param application The application name.
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Return all the modules of the repository.
     *
     * @return A List containing the description of the modules.
     */
    public Collection<ModuleDescription> getModules() {
        return modules;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "title=" + title +
                ", application='" + application + '\'' +
                ", modules=" + modules +
                '}';
    }
}