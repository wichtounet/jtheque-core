package org.jtheque.persistence.utils;

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

import org.jtheque.persistence.able.IDaoNotes;
import org.jtheque.persistence.impl.NoteImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A TestCase to test the Note class.
 *
 * @author Baptiste Wicht
 */
public class NoteTest {
    /**
     * Test the hashCode method.
     */
    @Test
    public void testHashCode() {
        Object note1 = new NoteImpl(IDaoNotes.NoteType.PERFECT, "test");
        Object note2 = new NoteImpl(IDaoNotes.NoteType.PERFECT, "test");
        Object note3 = new NoteImpl(IDaoNotes.NoteType.GOOD, "test");
        Object note4 = new NoteImpl(IDaoNotes.NoteType.PERFECT, "test2");
        Object note5 = new NoteImpl(IDaoNotes.NoteType.GOOD, "test");

        assertEquals(note1.hashCode(), note1.hashCode());
        assertEquals(note1.hashCode(), note2.hashCode());
        assertEquals(note3.hashCode(), note5.hashCode());

        assertFalse(note2.hashCode() == note3.hashCode());
        assertFalse(note2.hashCode() == note4.hashCode());
        assertFalse(note2.hashCode() == note5.hashCode());

        assertFalse(note3.hashCode() == note1.hashCode());
        assertFalse(note3.hashCode() == note4.hashCode());
        assertEquals(note3.hashCode(), note5.hashCode());

        assertFalse(note4.hashCode() == note5.hashCode());
    }

    /**
     * Test the equals method.
     */
    @Test
    public void testEquals() {
        Object note1 = new NoteImpl(IDaoNotes.NoteType.PERFECT, "test");
        Object note2 = new NoteImpl(IDaoNotes.NoteType.PERFECT, "test");
        Object note3 = new NoteImpl(IDaoNotes.NoteType.GOOD, "test");
        Object note4 = new NoteImpl(IDaoNotes.NoteType.PERFECT, "test2");
        Object note5 = new NoteImpl(IDaoNotes.NoteType.GOOD, "test");

        assertEquals(note1, note1);
        assertEquals(note1, note2);
        assertTrue(!note1.equals(note3));
        assertTrue(!note1.equals(note4));
        assertTrue(!note1.equals(note5));

        assertEquals(note2, note2);
        assertTrue(!note2.equals(note3));
        assertTrue(!note2.equals(note4));
        assertTrue(!note2.equals(note5));

        assertEquals(note3, note3);
        assertTrue(!note3.equals(note4));
        assertEquals(note3, note5);

        assertEquals(note4, note4);
        assertTrue(!note4.equals(note5));

        assertEquals(note5, note5);
    }
}
