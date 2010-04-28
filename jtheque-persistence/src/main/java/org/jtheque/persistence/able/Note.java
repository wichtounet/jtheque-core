package org.jtheque.persistence.able;

import org.jtheque.persistence.able.IDaoNotes;

/**
 * A note specification.
 *
 * @author Baptiste Wicht
 */
public interface Note {
    String getI18nKey();

    /**
     * Return the value of the note.
     *
     * @return The int value of note.
     */
    IDaoNotes.NoteType getValue();
}
