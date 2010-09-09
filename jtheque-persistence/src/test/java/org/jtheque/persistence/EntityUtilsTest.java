package org.jtheque.persistence;

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

import org.jtheque.persistence.utils.AbstractEntity;
import org.jtheque.persistence.utils.EntityUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A JUnit test case to test the EntityUtils class.
 *
 * @author Baptiste Wicht
 */
public class EntityUtilsTest {
    @Test
    public void testContainsID() {
        Collection<SimpleEntity> entities = new ArrayList<SimpleEntity>(10);

        for (int i = 4; i < 12; i++) {
            entities.add(new SimpleEntity(i));
        }

        for (int i = 4; i < 12; i++) {
            assertTrue(EntityUtils.containsID(entities, i));
        }

        assertFalse(EntityUtils.containsID(entities, 0));
        assertFalse(EntityUtils.containsID(entities, 1));
        assertFalse(EntityUtils.containsID(entities, 14));
    }

    @Test
    public void getByTemporaryId(){
        List<SimpleEntity> entities = new ArrayList<SimpleEntity>(10);

        for (int i = 0; i < 10; i++) {
            entities.add(new SimpleEntity(i, i));
        }

        for (int i = 0; i < 10; i++) {
            SimpleEntity entity = entities.get(i);

            assertSame(entity, EntityUtils.getByTemporaryId(entities, i));
        }

        assertNull(EntityUtils.getByTemporaryId(entities, 10));
        assertNull(EntityUtils.getByTemporaryId(entities, -1));
        assertNull(EntityUtils.getByTemporaryId(entities, 100));
    }

    private static final class SimpleEntity extends AbstractEntity {
        private SimpleEntity(int id) {
            super();

            setId(id);
        }

        private SimpleEntity(int id, int temporaryId) {
            this(id);

            getTemporaryContext().setId(temporaryId);
        }

        @Override
        public String getDisplayableText() {
            return "Simple AbstractEntity Test " + getId();
        }

        @Override
        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }

            if (object == this) {
                return true;
            }

            if (!(object instanceof SimpleEntity)) {
                return false;
            }

            return getId() != ((Entity) object).getId();
        }

        @Override
        public int hashCode() {
            return 31 * getId();
        }
    }
}
