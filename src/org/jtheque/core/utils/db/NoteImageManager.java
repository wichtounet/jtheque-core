package org.jtheque.core.utils.db;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.core.ICore;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;

import java.awt.image.BufferedImage;

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
 * A manager for the images of the notes.
 *
 * @author Baptiste Wicht
 */
public final class NoteImageManager {
    private static final BufferedImage[] STARS;

    /**
     * Utility class, not instanciable.
     */
    private NoteImageManager() {
        super();
    }

    static {
        STARS = new BufferedImage[7];

        for (int i = 0; i < 7; ++i) {
            STARS[i] = Managers.getManager(IResourceManager.class).getImage(ICore.IMAGES_BASE_NAME, "Star" + (i + 1), ImageType.PNG);
        }
    }

    /**
     * Return the image of the specified note.
     *
     * @param note The image of the note.
     * @return The <code>BufferedImage</code>.
     */
    public static BufferedImage getImage(Note note) {
        if (note == null) {
            return STARS[6];
        }

        return note.getValue() == DaoNotes.NoteType.ERROR ? STARS[DaoNotes.NoteType.UNDEFINED.intValue() - 1] : STARS[note.getValue().intValue() - 1];
    }
}
