package org.jtheque.core.managers.collection;

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

import org.jtheque.core.managers.collection.IDaoCollections;
import org.jtheque.core.managers.persistence.CachedJDBCDao;
import org.jtheque.core.managers.persistence.Query;
import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * A Data Access Object implementation for collections.
 *
 * @author Baptiste Wicht
 */
public final class DaoCollections extends CachedJDBCDao<Collection> implements IDaoCollections {
	private final RowMapper<Collection> rowMapper = new CollectionRowMapper();
	private final QueryMapper queryMapper = new CollectionQueryMapper();

	@Resource
	private IDaoPersistenceContext daoPersistenceContext;

	@Resource
	private SimpleJdbcTemplate jdbcTemplate;

	/**
	 * The current collection.
	 */
	private Collection currentCollection;

	/**
	 * Construct a new DaoCollections.
	 */
	public DaoCollections(){
		super(TABLE);
	}

	@Override
	public void create(Collection entity){
		super.create(entity);
	}

	@Override
	public java.util.Collection<Collection> getCollections(){
		return getAll();
	}

	@Override
	public Collection getCollection(int id){
		return get(id);
	}

    @Override
    public boolean exists(Collection entity) {
        return getCollection(entity.getTitle()) != null;
    }

    @Override
	public Collection getCollection(String name){
		List<Collection> collections = jdbcTemplate.query("SELECT * FROM " + TABLE + " WHERE TITLE = ?", rowMapper, name);

		if (collections.isEmpty()){
			return null;
		}

		Collection collection = collections.get(0);

		if (isNotInCache(collection.getId())){
			getCache().put(collection.getId(), collection);
		}

		return getCache().get(collection.getId());
	}

	@Override
	protected RowMapper<Collection> getRowMapper(){
		return rowMapper;
	}

	@Override
	protected QueryMapper getQueryMapper(){
		return queryMapper;
	}

	@Override
	protected void loadCache(){
		java.util.Collection<Collection> collections = daoPersistenceContext.getSortedList(TABLE, rowMapper);

		for (Collection collection : collections){
			getCache().put(collection.getId(), collection);
		}

		setCacheEntirelyLoaded();
	}

	@Override
	protected void load(int i){
		Collection collection = daoPersistenceContext.getDataByID(TABLE, i, rowMapper);

		getCache().put(i, collection);
	}

	@Override
	public Collection getCurrentCollection(){
		return currentCollection;
	}

	@Override
	public void setCurrentCollection(Collection collection){
		currentCollection = collection;
	}

	@Override
	public Collection create(){
		return new CollectionImpl();
	}

	/**
	 * A row mapper to map resultset to collection.
	 *
	 * @author Baptiste Wicht
	 */
	private final class CollectionRowMapper implements ParameterizedRowMapper<Collection> {
		@Override
		public Collection mapRow(ResultSet rs, int i) throws SQLException{
			Collection collection = create();

			collection.setId(rs.getInt("ID"));
			collection.setTitle(rs.getString("TITLE"));
			collection.setPassword(rs.getString("PASSWORD"));
			collection.setProtection(rs.getBoolean("PROTECTED"));

			return collection;
		}
	}

	/**
	 * A query mapper to map collection to sql query.
	 *
	 * @author Baptiste Wicht
	 */
	private static final class CollectionQueryMapper implements QueryMapper {
		@Override
		public Query constructInsertQuery(Entity entity){
			String query = "INSERT INTO " + TABLE + " (TITLE, PASSWORD, PROTECTED) VALUES(?, ?, ?)";

			return new Query(query, fillArray((Collection) entity, false));
		}

		@Override
		public Query constructUpdateQuery(Entity entity){
			String query = "UPDATE " + TABLE + " SET TITLE = ?, PASSWORD = ?, PROTECTED = ? WHERE ID = ?";

			return new Query(query, fillArray((Collection) entity, true));
		}

		/**
		 * Fill the array with the informations of the collection.
		 *
		 * @param collection The collection to use to fill the array.
		 * @param id Indicate if we must add the id to the array.
		 *
		 * @return The filled array.
		 */
		private static Object[] fillArray(Collection collection, boolean id){
			Object[] values = new Object[3 + (id ? 1 : 0)];

			values[0] = collection.getTitle();
			values[1] = collection.getPassword();
			values[2] = collection.isProtection();

			if (id){
				values[3] = collection.getId();
			}

			return values;
		}
	}
}