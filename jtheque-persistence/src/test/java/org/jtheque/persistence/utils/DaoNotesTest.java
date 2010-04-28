package org.jtheque.persistence.utils;

import org.jtheque.persistence.able.IDaoNotes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/META-INF/spring/jtheque-persistence.xml"})
public class DaoNotesTest {
    @Resource
    private IDaoNotes daoNotes;

    /**
     * Test the getNotes method.
     */
    @Test
    public void testGetNotes() {
        assertEquals(7, daoNotes.getNotes().length);
    }

    /**
     * Test the intValue method.
     */
    @Test
    public void testIntValue() {
        assertEquals(0, IDaoNotes.NoteType.ERROR.intValue());
        assertEquals(1, IDaoNotes.NoteType.NULL.intValue());
        assertEquals(2, IDaoNotes.NoteType.BAD.intValue());
        assertEquals(3, IDaoNotes.NoteType.MIDDLE.intValue());
        assertEquals(4, IDaoNotes.NoteType.GOOD.intValue());
        assertEquals(5, IDaoNotes.NoteType.VERYGOOD.intValue());
        assertEquals(6, IDaoNotes.NoteType.PERFECT.intValue());
        assertEquals(7, IDaoNotes.NoteType.UNDEFINED.intValue());
    }

    /**
     * Test the getEnum method.
     */
    @Test
    public void testGetEnum() {
        assertEquals(IDaoNotes.NoteType.ERROR, IDaoNotes.NoteType.getEnum(0));
        assertEquals(IDaoNotes.NoteType.NULL, IDaoNotes.NoteType.getEnum(1));
        assertEquals(IDaoNotes.NoteType.BAD, IDaoNotes.NoteType.getEnum(2));
        assertEquals(IDaoNotes.NoteType.MIDDLE, IDaoNotes.NoteType.getEnum(3));
        assertEquals(IDaoNotes.NoteType.GOOD, IDaoNotes.NoteType.getEnum(4));
        assertEquals(IDaoNotes.NoteType.VERYGOOD, IDaoNotes.NoteType.getEnum(5));
        assertEquals(IDaoNotes.NoteType.PERFECT, IDaoNotes.NoteType.getEnum(6));
        assertEquals(IDaoNotes.NoteType.UNDEFINED, IDaoNotes.NoteType.getEnum(7));
    }

    /**
     * Test the getNote method.
     */
    @Test
    public void testGetNote() {
        assertEquals(IDaoNotes.NoteType.NULL, daoNotes.getNote(IDaoNotes.NoteType.NULL).getValue());
        assertEquals(IDaoNotes.NoteType.BAD, daoNotes.getNote(IDaoNotes.NoteType.BAD).getValue());
        assertEquals(IDaoNotes.NoteType.MIDDLE, daoNotes.getNote(IDaoNotes.NoteType.MIDDLE).getValue());
        assertEquals(IDaoNotes.NoteType.GOOD, daoNotes.getNote(IDaoNotes.NoteType.GOOD).getValue());
        assertEquals(IDaoNotes.NoteType.VERYGOOD, daoNotes.getNote(IDaoNotes.NoteType.VERYGOOD).getValue());
        assertEquals(IDaoNotes.NoteType.PERFECT, daoNotes.getNote(IDaoNotes.NoteType.PERFECT).getValue());
        assertEquals(IDaoNotes.NoteType.UNDEFINED, daoNotes.getNote(IDaoNotes.NoteType.UNDEFINED).getValue());
    }
}