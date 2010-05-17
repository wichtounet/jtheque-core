package org.jtheque.persistence.able;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: May 15, 2010
 * Time: 3:32:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITemporaryContext {
	/**
	 * Return the id of the context.
	 *
	 * @return The id.
	 */
	int getId();

	/**
	 * Set the id of the context.
	 *
	 * @param id The new id to set.
	 */
	void setId(int id);
}
