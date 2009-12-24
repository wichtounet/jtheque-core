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

import org.jtheque.utils.io.FileException;

import java.io.File;
import java.util.Collection;

/**
 * A Restorer.
 *
 * @author Baptiste Wicht
 */
public interface Restorer extends Importer {
    /**
     * Import all the data from the file.
     *
     * @param file    The file.
     * @param readers The readers to use to restore.
     * @throws FileException When an error occurs during the restore process.
     */
    void restore(File file, Collection<BackupReader> readers) throws FileException;
}