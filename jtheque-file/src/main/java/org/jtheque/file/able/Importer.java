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
 * An importer.
 *
 * @author Baptiste Wicht
 */
public interface Importer {
    /**
     * Indicate if the importer can import from a specific file format or not.
     *
     * @param fileType The file format.
     * @return true ou false
     */
    boolean canImportFrom(FileType fileType);
}