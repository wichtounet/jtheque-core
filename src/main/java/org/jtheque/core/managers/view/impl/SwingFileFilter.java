package org.jtheque.core.managers.view.impl;

import org.jtheque.utils.io.SimpleFilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

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
 * A file chooser filter who use an IO filter to filter the file chooser.
 *
 * @author Baptiste Wicht
 */
public final class SwingFileFilter extends FileFilter {
    private final SimpleFilter filter;

    /**
     * Construct a new SwingFileFilter with a SimpleFilter.
     *
     * @param filter The filter to use.
     */
    public SwingFileFilter(SimpleFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean accept(File f) {
        return filter.accept(f);
    }

    @Override
    public String getDescription() {
        return filter.getDescription();
    }
}
