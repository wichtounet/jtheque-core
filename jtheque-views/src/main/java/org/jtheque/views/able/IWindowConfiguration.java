package org.jtheque.views.able;

/**
 * A window configuration specification.
 *
 * @author Baptiste Wicht
 */
public interface IWindowConfiguration {
	/**
	 * Set the width of the window.
	 *
	 * @param width The width of the window.
	 */
	void setWidth(int width);

	/**
	 * Set the height of the window.
	 *
	 * @param height The height of the window.
	 */
	void setHeight(int height);

	/**
	 * Set the X position of the window.
	 *
	 * @param positionX The X position of the window.
	 */
	void setPositionX(int positionX);

	/**
	 * Set the Y position of the window.
	 *
	 * @param positionY The Y position of the window.
	 */
	void setPositionY(int positionY);

	/**
	 * Return the width of the window.
	 *
	 * @return The width of the window.
	 */
	int getWidth();

	/**
	 * Return the height of the window.
	 *
	 * @return The height of the window.
	 */
	int getHeight();

	/**
	 * Return the Y position of the window.
	 *
	 * @return The Y position of the window.
	 */
	int getPositionY();

	/**
	 * Return the X position of the window.
	 *
	 * @return The X position of the window.
	 */
	int getPositionX();
}
