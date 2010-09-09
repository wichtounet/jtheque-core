package org.jtheque.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"jtheque-persistence-test.xml"})
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
        assertEquals(1, Note.NULL.intValue());
        assertEquals(2, Note.BAD.intValue());
        assertEquals(3, Note.MIDDLE.intValue());
        assertEquals(4, Note.GOOD.intValue());
        assertEquals(5, Note.VERYGOOD.intValue());
        assertEquals(6, Note.PERFECT.intValue());
        assertEquals(7, Note.UNDEFINED.intValue());
    }

    /**
     * Test the fromIntValue method.
     */
    @Test
    public void testGetEnum() {
        assertEquals(Note.NULL, Note.fromIntValue(1));
        assertEquals(Note.BAD, Note.fromIntValue(2));
        assertEquals(Note.MIDDLE, Note.fromIntValue(3));
        assertEquals(Note.GOOD, Note.fromIntValue(4));
        assertEquals(Note.VERYGOOD, Note.fromIntValue(5));
        assertEquals(Note.PERFECT, Note.fromIntValue(6));
        assertEquals(Note.UNDEFINED, Note.fromIntValue(7));
    }
}