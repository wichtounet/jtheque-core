package org.jtheque.collections;

import org.jtheque.persistence.able.Entity;

/**
 * A collection specification.
 *
 * @author Baptiste Wicht
 */
public interface Collection extends Entity {
	/**
	 * Set the title of the collection
	 *
	 * @param title The collection's title.
	 */
	void setTitle(String title);

	/**
	 * Return the title of the collection.
	 *
	 * @return The title of the collection.
	 */
	String getTitle();

	/**
	 * Indicate if the collection is password protected or not.
	 *
	 * @return true if the collection is password protected else false.
	 */
	boolean isProtection();

	/**
	 * Set a boolean flag indicating if the collection is password-protected or not.
	 *
	 * @param protection A boolean flag indicating if the collection is password-protected or not.
	 */
	void setProtection(boolean protection);

	/**
	 * Return the password of the collection encrypted with SHA-256.
	 *
	 * @return The encrypted password.
	 */
	String getPassword();

	/**
	 * Set the password of the collection.
	 *
	 * @param password The password of the collection.
	 */
	void setPassword(String password);
}
