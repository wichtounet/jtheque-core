package org.jtheque.persistence.able;

/**
 * A query specification.
 *
 * @author Baptiste Wicht
 */
public interface IQuery {
	/**
	 * Return the SQL Query.
	 *
	 * @return The SQL Query.
	 */
	String getSqlQuery();

	/**
	 * Return all the parameters of the query.
	 *
	 * @return The query parameters.
	 */
	Object[] getParameters();
}
