package org.jtheque.persistence.utils;

import org.jtheque.persistence.able.IDaoNotes;
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