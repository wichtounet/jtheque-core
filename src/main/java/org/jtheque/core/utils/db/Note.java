package org.jtheque.core.utils.db;

/**
 * A note specification.
 *
 * @author Baptiste Wicht
 */
public interface Note {
    /**
     * Return the internationalized text of the note.
     *
     * @return A text representation of the note.
     */
    String getInternationalizedText();

    /**
     * Return the element name of the note.
     *
     * @return The element name.
     */
    String getElementName();

    /**
     * Return the value of the note.
     *
     * @return The int value of note.
     */
    DaoNotes.NoteType getValue();
}
