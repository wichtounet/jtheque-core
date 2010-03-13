package org.jtheque.file.able;

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
 * A Backup Reader. It seems an object who read a backup file and persist the data from the file.
 *
 * @author Baptiste Wicht
 */
public interface ModuleBackuper {
    /**
     * Return the name of the schema.
     *
     * @return The name of the schema.
     */
    String getId();

    /**
     * Return all the dependencies of the schema.
     *
     * @return An array containing all the dependencies of the schema.
     */
    String[] getDependencies();

    ModuleBackup backup();
    void restore(ModuleBackup backup);
}