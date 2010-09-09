package org.jtheque.persistence;

import org.jtheque.persistence.utils.AbstractEntity;
import org.jtheque.persistence.utils.CachedJDBCDao;
import org.jtheque.persistence.utils.Query;
import org.jtheque.utils.bean.EqualsBuilder;
import org.jtheque.utils.bean.HashCodeUtils;
import org.jtheque.utils.bean.ReflectionUtils;
import org.jtheque.utils.unit.db.AbstractDBUnitTest;

import org.dbunit.dataset.DataSetException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

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
@ContextConfiguration(locations = "jtheque-persistence-test.xml")
public class CachedJDBCDaoTest extends AbstractDBUnitTest {
    private BasicDao dao;

    @Resource
    private DataSource dataSource;

    @Resource
    private DaoPersistenceContext daoPersistenceContext;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    public CachedJDBCDaoTest() {
        super("datas.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);
    }

    @Before
    public void before(){
        dao = new BasicDao();

        try {
            Field f = dao.getClass().getSuperclass().getSuperclass().getDeclaredField("persistenceContext");
            f.setAccessible(true);
            f.set(dao, daoPersistenceContext);
        } catch (NoSuchFieldException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }
    }

    @Test
    public void getAll(){
        assertEquals(5, dao.getAll().size());
    }

    @Test
    public void getId(){
        assertEquals("Test 1", dao.get(1).getTitle());
        assertEquals("Test 2", dao.get(2).getTitle());
        assertEquals("Unit 2", dao.get(3).getTitle());
        assertEquals("Unit 4", dao.get(4).getTitle());
        assertEquals("GOF 4", dao.get(5).getTitle());

        assertNull(dao.get(100));
        assertNull(dao.get(0));
    }

    @Test
    public void exists(){
        for(int i = 1; i < 6; i++){
            assertTrue(dao.exists(i));
        }

        assertFalse(dao.exists(100));
        assertFalse(dao.exists(13));
        assertFalse(dao.exists(0));
    }

    @Test
    public void deleteById(){
        dao.delete(5);

        assertEquals(4, dao.getAll().size());
        assertEquals(4, getTable("T_TEST").getRowCount());

        for (int i = 1; i < 5; i++) {
            assertTrue(dao.exists(i));
        }
    }

    @Test
    public void deleteEntity(){
        dao.delete(dao.get(4));

        assertEquals(4, dao.getAll().size());
        assertEquals(4, getTable("T_TEST").getRowCount());

        assertTrue(dao.exists(1));
        assertTrue(dao.exists(2));
        assertTrue(dao.exists(3));
        assertTrue(dao.exists(5));
    }

    @Test
    public void save() throws DataSetException {
        TestEntity entity = dao.get(3);

        entity.setTitle("Edited title");

        dao.save(entity);

        assertEquals(5, dao.getAll().size());
        assertEquals(5, getTable("T_TEST").getRowCount());
        assertEquals("Edited title", dao.get(3).getTitle());
        assertEquals("Edited title", getTable("T_TEST").getValue(2, "TITLE"));
    }

    @Test
    public void clearAll(){
        dao.clearAll();

        assertEquals(0, dao.getAll().size());
        assertEquals(0, getTable("T_TEST").getRowCount());
    }

    private static final class BasicDao extends CachedJDBCDao<TestEntity> {
        private static final String TABLE = "T_TEST";

        private final QueryMapper queryMapper = new TestEntityQueryMapper();
        private final RowMapper<TestEntity> rowMapper = new TestEntityRowMapper();

        private BasicDao() {
            super(TABLE);
        }

        @Override
        protected void loadCache() {
            defaultFillCache();
        }

        @Override
        protected QueryMapper getQueryMapper() {
            return queryMapper;
        }

        @Override
        protected RowMapper<TestEntity> getRowMapper() {
            return rowMapper;
        }

        @Override
        public TestEntity create() {
            return new TestEntity();
        }


        private final class TestEntityRowMapper implements ParameterizedRowMapper<TestEntity> {
            @Override
            public TestEntity mapRow(ResultSet rs, int i) throws SQLException {
                TestEntity entity = create();

                entity.setId(rs.getInt("ID"));
                entity.setTitle(rs.getString("TITLE"));
                entity.setTrigger(rs.getInt("TRIGGER"));

                return entity;
            }
        }

        private static final class TestEntityQueryMapper implements QueryMapper {
            @Override
            public Query constructInsertQuery(Entity entity) {
                String query = "INSERT INTO " + TABLE + " (TITLE, TRIGGER) VALUES(?, ?)";

                return new Query(query, fillArray((TestEntity) entity, false));
            }

            @Override
            public Query constructUpdateQuery(Entity entity) {
                String query = "UPDATE " + TABLE + " SET TITLE = ?, TRIGGER = ? WHERE ID = ?";

                return new Query(query, fillArray((TestEntity) entity, true));
            }

            /**
             * Fill the array with the informations of the collection.
             *
             * @param entity The collection to use to fill the array.
             * @param id         Indicate if we must add the id to the array.
             *
             * @return The filled array.
             */
            private static Object[] fillArray(TestEntity entity, boolean id) {
                Object[] values = new Object[2 + (id ? 1 : 0)];

                values[0] = entity.getTitle();
                values[1] = entity.getTrigger();

                if (id) {
                    values[2] = entity.getId();
                }

                return values;
            }
        }
    }

    private static class TestEntity extends AbstractEntity {
        private String title;
        private int trigger;

        @Override
        public boolean equals(Object object) {
            if(object instanceof TestEntity){
                TestEntity other = (TestEntity) object;

                return EqualsBuilder.newBuilder(this, other).
                        addField(getId(), other.getId()).
                        addField(title, other.title).
                        addField(trigger, other.trigger).
                        areEquals();
            }

            return false;
        }

        @Override
        public int hashCode() {
            return HashCodeUtils.hashCodeDirect(getId(), title, trigger);
        }

        @Override
        public String getDisplayableText() {
            return title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTrigger() {
            return trigger;
        }

        public void setTrigger(int trigger) {
            this.trigger = trigger;
        }
    }
}