package org.jtheque.persistence.able;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    ITemporaryContext getTemporaryContext();

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
