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

import org.jtheque.utils.annotations.ThreadSafe;

import java.awt.Image;

/**
 * A DAO to manage notes. You cannot delete or create new notes, there are automatically persisted in the Note.
 *
 * @author Baptiste Wicht
 * @see org.jtheque.persistence.able.Note
 */
@ThreadSafe
public interface DaoNotes {
    /**
     * Return all the notes of the dao.
     *
     * @return An array containing all the notes.
     */
    Note[] getNotes();

    /**
     * Return the default note.
     *
     * @return The default note.
     */
    Note getDefaultNote();

    /**
     * Return the image for the specified note. The image is still buffered.
     *
     * @param note The note to get the image for.
     *
     * @return The image for the note.
     *
     * @throws NullPointerException If the note is null
     */
    Image getImage(Note note);

    /**
     * Return the internationalized text of the note.
     *
     * @param note The note.
     *
     * @return The internationalized text of the note.
     *
     * @throws NullPointerException If the note is null
     */
    String getInternationalizedText(Note note);
}