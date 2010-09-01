package org.jtheque.persistence.impl;

import org.jtheque.i18n.LanguageService;
import org.jtheque.images.ImageService;
import org.jtheque.persistence.Note;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;

import org.springframework.core.io.ClassPathResource;

import java.awt.Image;
import java.awt.image.BufferedImage;

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
@ThreadSafe
public final class DaoNotes implements org.jtheque.persistence.DaoNotes {
    private final BufferedImage[] stars;
    
    @GuardedInternally
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
        return Note.values();
    }

    @Override
    public String getInternationalizedText(Note note) {
        if (note == null) {
            throw new NullPointerException("The note cannot be null");
        }

        return languageService.getMessage(note.getKey());
    }

    @Override
    public Image getImage(Note note) {
        if (note == null) {
            throw new NullPointerException("The note cannot be null");
        }

        return stars[note.intValue() - 1];
    }

    @Override
    public Note getDefaultNote() {
        return Note.UNDEFINED;
    }
}