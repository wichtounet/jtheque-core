package org.jtheque.collections;

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

import org.jtheque.utils.bean.Response;
import org.jtheque.utils.unit.db.AbstractDBUnitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "jtheque-collections-test.xml")
public class CollectionsServiceTest extends AbstractDBUnitTest {
    @Resource
    private CollectionsService collectionsService;

    @Resource
    private DaoCollections daoCollections;

    @Resource
    private DataSource dataSource;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);
    }

    @Test
    public void initOK() {
        assertNotNull(collectionsService);
    }

    public CollectionsServiceTest() {
        super("collections.xml");
    }

    @Test
    @DirtiesContext
    public void chooseCollectionSimple(){
        Response response = collectionsService.chooseCollection("Collection 1", "", false);

        assertTrue(response.isOk());

        assertEquals("Collection 1", daoCollections.getCurrentCollection().getTitle());
    }

    @Test
    @DirtiesContext
    public void chooseCollectionIncorrectPassword() {
        Response response = collectionsService.chooseCollection("Collection 2", "test", false);

        assertFalse(response.isOk());
    }

    @Test
    @DirtiesContext
    public void chooseCollectionTwice() {
        collectionsService.chooseCollection("Collection 1", "", false);

        Response response = collectionsService.chooseCollection("Collection 2", "", false);

        assertFalse(response.isOk());
    }

    @Test
    public void createExistingCollection() {
        Response response = collectionsService.chooseCollection("Collection 1", "", true);

        assertFalse(response.isOk());
    }

    @Test
    @DirtiesContext
    public void createCollection() {
        Response response = collectionsService.chooseCollection("Collection 4", "test", true);

        assertTrue(response.isOk());

        assertEquals(3, getTable("T_COLLECTIONS").getRowCount());

        assertEquals("Collection 4", daoCollections.getCurrentCollection().getTitle());
    }
}
