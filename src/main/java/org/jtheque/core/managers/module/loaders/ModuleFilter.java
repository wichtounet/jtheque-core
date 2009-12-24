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
import java.io.FileFilter;
import java.util.Locale;

/**
 * A module file filter. This filter accept only the JAR files.
 *
 * @author Baptiste Wicht
 */
final class ModuleFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        return file.isFile() && file.getName().toLowerCase(Locale.getDefault()).endsWith(".jar");
    }
}