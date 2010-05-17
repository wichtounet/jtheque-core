package org.jtheque.core.able;

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

import org.jtheque.utils.bean.IntDate;

public interface ICoreConfiguration {
	/**
	 * Indicate if we verify the updates at the application startup.
	 *
	 * @return <code>true</code> if verify updates on startup else <code>false</code>
	 */
	boolean verifyUpdateOnStartup();

	/**
	 * Set if we must verify the updates on startup.
	 *
	 * @param value A boolean tag indicating if we must verify updates on startup.
	 */
	void setVerifyUpdateOnStartup(boolean value);

	/**
	 * Indicate if we must retain the size of the windows.
	 *
	 * @return <code>true</code> if we must retain the size of the windows else <code>false</code>
	 */
	boolean retainSizeAndPositionOfWindow();

	/**
	 * Set if we must retain the size of the window.
	 *
	 * @param value A boolean tag indicating if we must retain the size of the window.
	 */
	void setRetainSizeAndPositionOfWindow(boolean value);

	/**
	 * Indicate if there is a proxy or not.
	 *
	 * @return <code>true</code> if there is a proxy else <code>false</code>
	 */
	boolean hasAProxy();

	/**
	 * Set if there is a proxy or not.
	 *
	 * @param value A boolean tag indicating if there is a proxy or not.
	 */
	void setHasAProxy(boolean value);

	/**
	 * Return the port number of the proxy.
	 *
	 * @param value The port number of the proxy.
	 */
	void setProxyPort(String value);

	/**
	 * Return the port number of the proxy.
	 *
	 * @return The port number of the proxy.
	 */
	String getProxyPort();

	/**
	 * Set the address of the proxy.
	 *
	 * @param value The address of the proxy.
	 */
	void setProxyAddress(String value);

	/**
	 * Return the address of the proxy.
	 *
	 * @return The address of the proxy.
	 */
	String getProxyAddress();

	/**
	 * Set if we must delete the logs or not.
	 *
	 * @param value A boolean tag indicating if we must delete the logs or not.
	 */
	void setMustDeleteLogs(boolean value);

	/**
	 * Indicate if we must delete the logs or not.
	 *
	 * @return <code>true</code> if we must delete the logs else <code>false</code>
	 */
	boolean mustDeleteLogs();

	/**
	 * Set the email of the user.
	 *
	 * @param value The new email to set
	 */
	void setUserEmail(String value);

	/**
	 * Return the email of the user.
	 *
	 * @return The email
	 */
	String getUserEmail();

	/**
	 * Set the SMTP host.
	 *
	 * @param value The new SMTP host.
	 */
	void setSmtpHost(String value);

	/**
	 * Return the SMTP host.
	 *
	 * @return The SMTP host.
	 */
	String getSmtpHost();

	void setLastCollection(String value);

	String getLastCollection();

	/**
	 * Return the date of the last reading of the messages.
	 *
	 * @return The date of the last reading of the messages.
	 */
	IntDate getMessagesLastRead();
}
