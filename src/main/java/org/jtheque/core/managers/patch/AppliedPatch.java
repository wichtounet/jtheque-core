package org.jtheque.core.managers.patch;

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

import org.jtheque.utils.bean.IntDate;

/**
 * An applied patch.
 *
 * @author Baptiste Wicht
 */
final class AppliedPatch {
    private String name;
    private IntDate date;

    /**
     * Return the name of the applied patch.
     *
     * @return The name of the applied patch.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the applied patch.
     *
     * @param name The name of the applied.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the date when the patch has been applied.
     *
     * @return The date of the apply of the patch.
     */
    public IntDate getDate() {
        return date;
    }

    /**
     * Set the date of the apply of the patch.
     *
     * @param date The date.
     */
    public void setDate(IntDate date) {
        this.date = date;
	}
}