package org.jtheque.core.utils.io.able;

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
 * A JTheque file.
 *
 * @author Baptiste Wicht
 */
public interface JTFile {
    String JT_KEY = "1W@JTHEQUE@W1";
    long JT_CATEGORY_SEPARATOR = -89569876428459544L;

    /**
     * Indicate if the file is valid.
     *
     * @return true if the file is valid else false.
     */
    boolean isValid();
}