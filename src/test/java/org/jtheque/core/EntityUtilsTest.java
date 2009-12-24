package org.jtheque.core;

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

import org.jtheque.core.managers.persistence.AbstractEntity;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.utils.db.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test case to test the EntityUtils class.
 *
 * @author Baptiste Wicht
 */
public class EntityUtilsTest {
    @Test
    public void testContainsID() {
        Collection<SimpleEntity> entities = new ArrayList<SimpleEntity>(5);

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

    private static final class SimpleEntity extends AbstractEntity {
        private SimpleEntity(int id) {
            super();

            setId(id);
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
