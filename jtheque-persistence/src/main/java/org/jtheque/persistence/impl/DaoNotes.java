package org.jtheque.persistence.impl;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.persistence.able.IDaoNotes;
import org.jtheque.persistence.able.Note;
import org.jtheque.resources.IResourceService;
import org.springframework.core.io.ClassPathResource;

import java.awt.Image;
import java.awt.image.BufferedImage;
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
public final class DaoNotes implements IDaoNotes {
    private Note[] notes;

    private final BufferedImage[] STARS;

    private final ILanguageService languageService;

    public DaoNotes(IResourceService resourceService, ILanguageService languageService) {
        super();

        this.languageService = languageService;

        STARS = new BufferedImage[7];

        for (int i = 0; i < 7; ++i) {
            resourceService.registerResource("Star" + (i + 1), new ClassPathResource("org/jtheque/persistence/" + "Star" + (i + 1) + ".png"));
        }

        for (int i = 0; i < 7; ++i) {
            STARS[i] = resourceService.getImage("Star" + (i + 1));
        }
    }

    @Override
    public Note[] getNotes() {
        if (notes == null) {
            loadNotes();
        }

        return Arrays.copyOf(notes, notes.length);
    }

    @Override
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

    @Override
    public String getInternationalizedText(Note note) {
        return languageService.getMessage(note.getI18nKey());
    }

    @Override
    public Image getImage(Note note) {
        if (note == null) {
            return STARS[6];
        }

        return note.getValue() == IDaoNotes.NoteType.ERROR ? STARS[IDaoNotes.NoteType.UNDEFINED.intValue() - 1] : STARS[note.getValue().intValue() - 1];
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

    @Override
    public Note getDefaultNote() {
        return notes[6];
    }
}