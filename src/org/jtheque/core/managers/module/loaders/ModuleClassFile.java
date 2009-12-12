package org.jtheque.core.managers.module.loaders;

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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A module class file.
 *
 * @author Baptiste Wicht
 */
final class ModuleClassFile {
    private File file;
    private String moduleContext;
    private final Collection<URL> resources;

    /**
     * Construct a new ModuleClassFile.
     */
    ModuleClassFile() {
        super();

        resources = new ArrayList<URL>(10);
    }

    /**
     * Return the file.
     *
     * @return The file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Set the file.
     *
     * @param file The file.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Return the context of the module.
     *
     * @return The module context.
     */
    public String getModuleContext() {
        return moduleContext;
    }

    /**
     * Set the module context.
     *
     * @param moduleContext The module context.
     */
    public void setModuleContext(String moduleContext) {
        this.moduleContext = moduleContext;
    }

    /**
     * Return all the resources of the module class file.
     *
     * @return A List containing all the resources of the module.
     */
    public Collection<URL> getResources() {
        return resources;
    }

    @Override
    public String toString() {
        return "ModuleClassFile{" +
                "file=" + file +
                ", moduleContext='" + moduleContext + '\'' +
                ", resources=" + resources +
                '}';
    }
}