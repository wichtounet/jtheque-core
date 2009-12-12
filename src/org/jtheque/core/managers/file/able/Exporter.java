package org.jtheque.core.managers.file.able;

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
 * An exporter. It's simply an object who export to a specific format.
 *
 * @author Baptiste Wicht
 */
public interface Exporter {
    /**
     * Indicate if the exporter can export to a specific fileType.
     *
     * @param fileType The fileType to export to.
     * @return true if the exporter can export to this file type, else false.
     */
    boolean canExportTo(FileType fileType);
}