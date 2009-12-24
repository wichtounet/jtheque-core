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
        assertEquals(DaoNotes.getInstance().getNotes().length, 7);
    }

    /**
     * Test the intValue method.
     */
    @Test
    public void testIntValue() {
        assertEquals(DaoNotes.NoteType.ERROR.intValue(), 0);
        assertEquals(DaoNotes.NoteType.NULL.intValue(), 1);
        assertEquals(DaoNotes.NoteType.BAD.intValue(), 2);
        assertEquals(DaoNotes.NoteType.MIDDLE.intValue(), 3);
        assertEquals(DaoNotes.NoteType.GOOD.intValue(), 4);
        assertEquals(DaoNotes.NoteType.VERYGOOD.intValue(), 5);
        assertEquals(DaoNotes.NoteType.PERFECT.intValue(), 6);
        assertEquals(DaoNotes.NoteType.UNDEFINED.intValue(), 7);
    }

    /**
     * Test the getEnum method.
     */
    @Test
    public void testGetEnum() {
        assertEquals(DaoNotes.NoteType.getEnum(0), DaoNotes.NoteType.ERROR);
        assertEquals(DaoNotes.NoteType.getEnum(1), DaoNotes.NoteType.NULL);
        assertEquals(DaoNotes.NoteType.getEnum(2), DaoNotes.NoteType.BAD);
        assertEquals(DaoNotes.NoteType.getEnum(3), DaoNotes.NoteType.MIDDLE);
        assertEquals(DaoNotes.NoteType.getEnum(4), DaoNotes.NoteType.GOOD);
        assertEquals(DaoNotes.NoteType.getEnum(5), DaoNotes.NoteType.VERYGOOD);
        assertEquals(DaoNotes.NoteType.getEnum(6), DaoNotes.NoteType.PERFECT);
        assertEquals(DaoNotes.NoteType.getEnum(7), DaoNotes.NoteType.UNDEFINED);
    }

    /**
     * Test the getNote method.
     */
    @Test
    public void testGetNote() {
        assertEquals(DaoNotes.getInstance().getNote(DaoNotes.NoteType.NULL).getValue(), DaoNotes.NoteType.NULL);
        assertEquals(DaoNotes.getInstance().getNote(DaoNotes.NoteType.BAD).getValue(), DaoNotes.NoteType.BAD);
        assertEquals(DaoNotes.getInstance().getNote(DaoNotes.NoteType.MIDDLE).getValue(), DaoNotes.NoteType.MIDDLE);
        assertEquals(DaoNotes.getInstance().getNote(DaoNotes.NoteType.GOOD).getValue(), DaoNotes.NoteType.GOOD);
        assertEquals(DaoNotes.getInstance().getNote(DaoNotes.NoteType.VERYGOOD).getValue(), DaoNotes.NoteType.VERYGOOD);
        assertEquals(DaoNotes.getInstance().getNote(DaoNotes.NoteType.PERFECT).getValue(), DaoNotes.NoteType.PERFECT);
        assertEquals(DaoNotes.getInstance().getNote(DaoNotes.NoteType.UNDEFINED).getValue(), DaoNotes.NoteType.UNDEFINED);
    }
}