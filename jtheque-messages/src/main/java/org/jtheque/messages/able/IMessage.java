package org.jtheque.messages.able;

import org.jtheque.utils.bean.IntDate;

public interface IMessage extends Comparable<IMessage> {
	/**
	 * Return the Id of the message.
	 *
	 * @return The id of the message.
	 */
	int getId();

	/**
	 * Set the Id of the message.
	 *
	 * @param id The Id of the message.
	 */
	void setId(int id);

	/**
	 * Return the title of the message.
	 *
	 * @return The title of the message.
	 */
	String getTitle();

	/**
	 * Set the title of the message.
	 *
	 * @param title The title of the message.
	 */
	void setTitle(String title);

	/**
	 * Return the text of the message.
	 *
	 * @return The text of the message.
	 */
	String getMessage();

	/**
	 * Set the text of the message.
	 *
	 * @param message The text of the message.
	 */
	void setMessage(String message);

	/**
	 * Return the date of the message.
	 *
	 * @return The date of the message.
	 */
	IntDate getDate();

	/**
	 * Set the date of the message.
	 *
	 * @param date The date of the message.
	 */
	void setDate(IntDate date);

	/**
	 * Return the source of the message.
	 *
	 * @return The source of the message.
	 */
	String getSource();

	/**
	 * Set the source of the message.
	 *
	 * @param source The source of the message.
	 */
	void setSource(String source);
}
