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

import java.awt.Image;

public interface IDaoNotes {
    /**
     * Return all the notes of the dao.
     *
     * @return An array containing all the notes.
     */
    Note[] getNotes();

    /**
     * Return the note with the specific value.
     *
     * @param value The value of the note we want
     * @return The note with this value or <code>null</code> if we doesn't it.
     */
    Note getNote(NoteType value);

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
     * @return The image for the note.
     */
    Image getImage(Note note);

    String getInternationalizedText(Note note);

    /**
     * A properties class for notes.
     *
     * @author Baptiste Wicht
     */
    enum NoteType {
        ERROR(0),
        NULL(1),
        BAD(2),
        MIDDLE(3),
        GOOD(4),
        VERYGOOD(5),
        PERFECT(6),
        UNDEFINED(7);

        private final int note;

        /**
         * Construct a new NoteType.
         *
         * @param note The note value.
         */
        NoteType(int note) {
            this.note = note;
        }

        /**
         * Return the int value of the NoteType.
         *
         * @return The int value of the enum.
         */
        public int intValue() {
            return note;
        }

        /**
         * Return the enum with the enum int value.
         *
         * @param e The int value to search.
         * @return The NoteType corresponding to the int value to search.
         */
        public static IDaoNotes.NoteType getEnum(int e) {
            IDaoNotes.NoteType note = MIDDLE;

            for (IDaoNotes.NoteType n : values()) {
                if (n.ordinal() == e) {
                    note = n;
                    break;
                }
            }

            return note;
        }
    }
}
