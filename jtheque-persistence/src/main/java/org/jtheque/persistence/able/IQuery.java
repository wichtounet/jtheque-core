package org.jtheque.persistence.able;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: May 15, 2010
 * Time: 3:36:09 PM
 * To change this template use File | Settings | File Templates.
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
