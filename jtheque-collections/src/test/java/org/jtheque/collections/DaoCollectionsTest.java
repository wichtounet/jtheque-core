package org.jtheque.collections;

import org.jtheque.utils.unit.db.AbstractDBUnitTest;

import org.dbunit.dataset.DataSetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.junit.Assert.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "jtheque-collections-test.xml")
public class DaoCollectionsTest extends AbstractDBUnitTest {
    @Resource
    private DaoCollections daoCollections;

    @Resource
    private DataSource dataSource;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    public DaoCollectionsTest() {
        super("collections.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);
    }

    @Test
    public void initOK() {
        assertNotNull(daoCollections);
    }

    @Test
    public void getMovies() {
        assertEquals(2, daoCollections.getCollections().size());
    }

    @Test
    public void getCollectionByID(){
        assertEquals("Collection 1", daoCollections.getCollection(1).getTitle());
        assertEquals("Collection 2", daoCollections.getCollection(2).getTitle());
        
        assertNull(daoCollections.getCollection(3));
    }

    @Test
    public void exists(){
        assertTrue(daoCollections.exists(1));
        assertTrue(daoCollections.exists(2));
        assertFalse(daoCollections.exists(3));

        assertTrue(daoCollections.exists("Collection 1"));
        assertTrue(daoCollections.exists("Collection 2"));
        assertFalse(daoCollections.exists("Collection 3"));
    }

    @Test
    public void getCollectionByName(){
        DataCollection collection = daoCollections.getCollection("Collection 1");

        assertEquals(1, collection.getId());
        assertEquals("Collection 1", collection.getTitle());
        assertFalse(collection.isProtection());

        collection = daoCollections.getCollection("Collection 2");

        assertEquals(2, collection.getId());
        assertEquals("Collection 2", collection.getTitle());
        assertEquals("salut", collection.getPassword());
        assertTrue(collection.isProtection());
    }

    @Test
    public void getCurrentCollection(){
        assertNull(daoCollections.getCurrentCollection());

        DataCollection collection = daoCollections.getCollection(1);

        daoCollections.setCurrentCollection(collection);

        assertSame(collection, daoCollections.getCurrentCollection());
    }

    @Test
    public void createCollection(){
        DataCollection collection = daoCollections.create();

        collection.setPassword("test");
        collection.setProtection(true);
        collection.setTitle("Collection 3");

        daoCollections.save(collection);

        assertEquals(3, getTable("T_COLLECTIONS").getRowCount());

        DataCollection collection2 = daoCollections.getCollection("Collection 3");

        assertSame(collection, collection2);
    }

    @Test
    public void updateCollection() throws DataSetException {
        DataCollection collection = daoCollections.getCollection("Collection 1");

        collection.setTitle("Collection 9");

        daoCollections.save(collection);

        assertEquals("Collection 9", getTable("T_COLLECTIONS").getValue(0, "TITLE"));
    }
}