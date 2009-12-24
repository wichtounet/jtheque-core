package org.jtheque.core.utils.file.jt.able;

import org.jtheque.core.utils.file.jt.AbstractJTFileHeader;

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
 * A JTheque non zipped file.
 *
 * @author Baptiste Wicht
 */
public interface JTNotZippedFile extends JTFile {
    /**
     * Return the header of the file.
     *
     * @return The JT Header.
     */
    AbstractJTFileHeader getHeader();

    /**
     * Set if the file has the correct separators or not.
     *
     * @param correctSeparators true if the file has correct separators else false.
     */
    void setCorrectSeparators(boolean correctSeparators);

    /**
     * Set if the file has the correct separators or not.
     *
     * @return true if the file has correct separators else false.
     */
    boolean isCorrectSeparators();
}