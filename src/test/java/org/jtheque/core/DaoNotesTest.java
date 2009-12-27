package org.jtheque.core;

import org.jtheque.core.utils.db.DaoNotes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
 * A TestCase to test the DaoNotes class.
 *
 * @author Baptiste Wicht
 */
public class DaoNotesTest {
    /**
     * Test the getNotes method.
     */
    @Test
    public void testGetNotes() {
        assertEquals(7, DaoNotes.getInstance().getNotes().length);
    }

    /**
     * Test the intValue method.
     */
    @Test
    public void testIntValue() {
        assertEquals(0, DaoNotes.NoteType.ERROR.intValue());
        assertEquals(1, DaoNotes.NoteType.NULL.intValue());
        assertEquals(2, DaoNotes.NoteType.BAD.intValue());
        assertEquals(3, DaoNotes.NoteType.MIDDLE.intValue());
        assertEquals(4, DaoNotes.NoteType.GOOD.intValue());
        assertEquals(5, DaoNotes.NoteType.VERYGOOD.intValue());
        assertEquals(6, DaoNotes.NoteType.PERFECT.intValue());
        assertEquals(7, DaoNotes.NoteType.UNDEFINED.intValue());
    }

    /**
     * Test the getEnum method.
     */
    @Test
    public void testGetEnum() {
        assertEquals(DaoNotes.NoteType.ERROR, DaoNotes.NoteType.getEnum(0));
        assertEquals(DaoNotes.NoteType.NULL, DaoNotes.NoteType.getEnum(1));
        assertEquals(DaoNotes.NoteType.BAD, DaoNotes.NoteType.getEnum(2));
        assertEquals(DaoNotes.NoteType.MIDDLE, DaoNotes.NoteType.getEnum(3));
        assertEquals(DaoNotes.NoteType.GOOD, DaoNotes.NoteType.getEnum(4));
        assertEquals(DaoNotes.NoteType.VERYGOOD, DaoNotes.NoteType.getEnum(5));
        assertEquals(DaoNotes.NoteType.PERFECT, DaoNotes.NoteType.getEnum(6));
        assertEquals(DaoNotes.NoteType.UNDEFINED, DaoNotes.NoteType.getEnum(7));
    }

    /**
     * Test the getNote method.
     */
    @Test
    public void testGetNote() {
        assertEquals(DaoNotes.NoteType.NULL, DaoNotes.getInstance().getNote(DaoNotes.NoteType.NULL).getValue());
        assertEquals(DaoNotes.NoteType.BAD, DaoNotes.getInstance().getNote(DaoNotes.NoteType.BAD).getValue());
        assertEquals(DaoNotes.NoteType.MIDDLE, DaoNotes.getInstance().getNote(DaoNotes.NoteType.MIDDLE).getValue());
        assertEquals(DaoNotes.NoteType.GOOD, DaoNotes.getInstance().getNote(DaoNotes.NoteType.GOOD).getValue());
        assertEquals(DaoNotes.NoteType.VERYGOOD, DaoNotes.getInstance().getNote(DaoNotes.NoteType.VERYGOOD).getValue());
        assertEquals(DaoNotes.NoteType.PERFECT, DaoNotes.getInstance().getNote(DaoNotes.NoteType.PERFECT).getValue());
        assertEquals(DaoNotes.NoteType.UNDEFINED, DaoNotes.getInstance().getNote(DaoNotes.NoteType.UNDEFINED).getValue());
    }
}