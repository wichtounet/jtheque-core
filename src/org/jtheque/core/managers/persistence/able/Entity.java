package org.jtheque.core.managers.persistence.able;

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

import org.jtheque.core.managers.persistence.context.TemporaryContext;

/**
 * An entity specification.
 *
 * @author Baptiste Wicht
 */
public interface Entity extends Comparable<Entity> {
    /**
     * Return the temporary context of the data.
     *
     * @return The temporary context of the data.
     */
    TemporaryContext getTemporaryContext();

    /**
     * Return the id of this Entity.
     *
     * @return The id
     */
    int getId();

    /**
     * Set the id of this Entity.
     *
     * @param id The new id to set
     */
    void setId(int id);

    /**
     * Return a displayable text of the Entity. This is not the same as toString(). This method
     * provide an text of display on the application.
     *
     * @return A string representation of the Entity.
     */
    String getDisplayableText();

    /**
     * Indicate if the entity is saved or not.
     *
     * @return true if the entity is saved, else false.
     */
    boolean isSaved();

    /**
     * Save the state of the data object to a memento. By default this method isn't supported by Entity.
     */
    void saveToMemento();

    /**
     * Restore the state of the data object from the memento. By default this method isn't supported by Entity.
     */
    void restoreMemento();
}
