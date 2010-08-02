package org.jtheque.persistence.utils;

import org.jtheque.persistence.able.DaoNotes;

import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

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
 * A TestCase to test the DaoNotes class.
 *
 * @author Baptiste Wicht
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/META-INF/spring/jtheque-persistence.xml"})
public class DaoNotesTest {
    @Resource
    private DaoNotes daoNotes;

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
        assertEquals(DaoNotes.NoteType.NULL, daoNotes.getNote(DaoNotes.NoteType.NULL).getValue());
        assertEquals(DaoNotes.NoteType.BAD, daoNotes.getNote(DaoNotes.NoteType.BAD).getValue());
        assertEquals(DaoNotes.NoteType.MIDDLE, daoNotes.getNote(DaoNotes.NoteType.MIDDLE).getValue());
        assertEquals(DaoNotes.NoteType.GOOD, daoNotes.getNote(DaoNotes.NoteType.GOOD).getValue());
        assertEquals(DaoNotes.NoteType.VERYGOOD, daoNotes.getNote(DaoNotes.NoteType.VERYGOOD).getValue());
        assertEquals(DaoNotes.NoteType.PERFECT, daoNotes.getNote(DaoNotes.NoteType.PERFECT).getValue());
        assertEquals(DaoNotes.NoteType.UNDEFINED, daoNotes.getNote(DaoNotes.NoteType.UNDEFINED).getValue());
    }
}