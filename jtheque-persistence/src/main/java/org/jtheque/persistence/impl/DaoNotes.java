package org.jtheque.persistence.impl;

import org.jtheque.i18n.able.LanguageService;
import org.jtheque.images.able.ImageService;
import org.jtheque.persistence.able.Note;

import org.springframework.core.io.ClassPathResource;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;

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
 * A data access object for the notes.
 *
 * @author Baptiste Wicht
 */
public final class DaoNotes implements org.jtheque.persistence.able.DaoNotes {
    private Note[] notes;

    private final BufferedImage[] stars;

    private final LanguageService languageService;

    /**
     * Construct a new DaoNotes.
     *
     * @param imageService    The resources.
     * @param languageService The language service.
     */
    public DaoNotes(ImageService imageService, LanguageService languageService) {
        super();

        this.languageService = languageService;

        stars = new BufferedImage[7];

        for (int i = 0; i < 7; ++i) {
            String starName = "Star" + (i + 1);
            
            imageService.registerResource(starName, new ClassPathResource("org/jtheque/persistence/" + starName + ".png"));

            stars[i] = imageService.getImage(starName);
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
            return stars[6];
        }

        return note.getValue() == org.jtheque.persistence.able.DaoNotes.NoteType.ERROR ? stars[org.jtheque.persistence.able.DaoNotes.NoteType.UNDEFINED.intValue() - 1] : stars[note.getValue().intValue() - 1];
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