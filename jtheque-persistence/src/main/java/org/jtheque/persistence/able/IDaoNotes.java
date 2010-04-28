package org.jtheque.persistence.able;

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
