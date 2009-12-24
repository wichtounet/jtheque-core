package org.jtheque.core.utils.db;

import java.awt.Image;
import java.util.Arrays;

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
 * A data access object for the notes.
 *
 * @author Baptiste Wicht
 */
public final class DaoNotes {
    private Note[] notes;

    private static final DaoNotes INSTANCE = new DaoNotes();

    /**
     * Construct a new DaoNotes. This class isn't instanciable.
     */
    private DaoNotes() {
        super();
    }

    /**
     * Return the unique instance of the class.
     *
     * @return The singleton of DaoNotes.
     */
    public static DaoNotes getInstance() {
        return INSTANCE;
    }

    /**
     * Return all the notes of the dao.
     *
     * @return An array containing all the notes.
     */
    public Note[] getNotes() {
        if (notes == null) {
            loadNotes();
        }

        return Arrays.copyOf(notes, notes.length);
    }

    /**
     * Return the note with the specific value.
     *
     * @param value The value of the note we want
     * @return The note with this value or <code>null</code> if we doesn't it.
     */
    public Note getNote(NoteType value) {
        if (notes == null) {
            loadNotes();
        }

        Note note = null;

        for (Note n : notes) {
            if (n.getValue() == value) {
                note = n;
                break;
            }
        }

        return note;
    }

    /**
     * Return the image for the specified note. The image is still buffered.
     *
     * @param note The note to get the image for.
     * @return The image for the note.
     */
    public Image getImage(Note note) {
        return NoteImageManager.getImage(note);
    }

    /**
     * Load all the notes.
     */
    private void loadNotes() {
        if (notes == null) {
            notes = new NoteImpl[7];

            notes[0] = new NoteImpl(NoteType.NULL, "data.notes.null");
            notes[1] = new NoteImpl(NoteType.BAD, "data.notes.bad");
            notes[2] = new NoteImpl(NoteType.MIDDLE, "data.notes.middle");
            notes[3] = new NoteImpl(NoteType.GOOD, "data.notes.good");
            notes[4] = new NoteImpl(NoteType.VERYGOOD, "data.notes.verygood");
            notes[5] = new NoteImpl(NoteType.PERFECT, "data.notes.perfect");
            notes[6] = new NoteImpl(NoteType.UNDEFINED, "data.notes.undefined");
        }
    }

    /**
     * Return the default note.
     *
     * @return The default note.
     */
    public Note getDefaultNote() {
        return notes[6];
    }

    /**
     * A properties class for notes.
     *
     * @author Baptiste Wicht
     */
    public enum NoteType {
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
        public static NoteType getEnum(int e) {
            NoteType note = MIDDLE;

            for (NoteType n : values()) {
                if (n.ordinal() == e) {
                    note = n;
                    break;
                }
            }

            return note;
        }
    }
}