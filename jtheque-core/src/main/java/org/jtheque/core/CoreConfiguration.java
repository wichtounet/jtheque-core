package org.jtheque.core;

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

import org.jtheque.states.AbstractState;
import org.jtheque.utils.bean.IntDate;

/**
 * The configuration of the core.
 *
 * @author Baptiste Wicht
 */
public final class CoreConfiguration extends AbstractState {
    private static final String FALSE = "false";

    /**
     * Indicate if we verify the updates at the application startup.
     *
     * @return <code>true</code> if verify updates on startup else <code>false</code>
     */
    public boolean verifyUpdateOnStartup() {
        return Boolean.parseBoolean(getProperty("verifyOnStartup", FALSE));
    }

    /**
     * Set if we must verify the updates on startup.
     *
     * @param value A boolean tag indicating if we must verify updates on startup.
     */
    public void setVerifyUpdateOnStartup(boolean value) {
        setProperty("verifyOnStartup", String.valueOf(value));
    }

    /**
     * Indicate if we must retain the size of the windows.
     *
     * @return <code>true</code> if we must retain the size of the windows else <code>false</code>
     */
    public boolean retainSizeAndPositionOfWindow() {
        return Boolean.parseBoolean(getProperty("retainSizeAndPositionOfWindow", FALSE));
    }

    /**
     * Set if we must retain the size of the window.
     *
     * @param value A boolean tag indicating if we must retain the size of the window.
     */
    public void setRetainSizeAndPositionOfWindow(boolean value) {
        setProperty("retainSizeAndPositionOfWindow", Boolean.toString(value));
    }

    /**
     * Indicate if there is a proxy or not.
     *
     * @return <code>true</code> if there is a proxy else <code>false</code>
     */
    public boolean hasAProxy() {
        return Boolean.parseBoolean(getProperty("proxy", FALSE));
    }

    /**
     * Set if there is a proxy or not.
     *
     * @param value A boolean tag indicating if there is a proxy or not.
     */
    public void setHasAProxy(boolean value) {
        setProperty("proxy", Boolean.toString(value));
    }

    /**
     * Return the port number of the proxy.
     *
     * @param value The port number of the proxy.
     */
    public void setProxyPort(String value) {
        setProperty("proxyPort", value);
    }

    /**
     * Return the port number of the proxy.
     *
     * @return The port number of the proxy.
     */
    public String getProxyPort() {
        return getProperty("proxyPort");
    }

    /**
     * Set the address of the proxy.
     *
     * @param value The address of the proxy.
     */
    public void setProxyAddress(String value) {
        setProperty("proxyAddress", value);
    }

    /**
     * Return the address of the proxy.
     *
     * @return The address of the proxy.
     */
    public String getProxyAddress() {
        return getProperty("proxyAddress");
    }

    /**
     * Set if we must delete the logs or not.
     *
     * @param value A boolean tag indicating if we must delete the logs or not.
     */
    public void setMustDeleteLogs(boolean value) {
        setProperty("deleteLog", Boolean.toString(value));
    }

    /**
     * Indicate if we must delete the logs or not.
     *
     * @return <code>true</code> if we must delete the logs else <code>false</code>
     */
    public boolean mustDeleteLogs() {
        return Boolean.valueOf(getProperty("deleteLog", FALSE));
    }

    /**
     * Set the email of the user.
     *
     * @param value The new email to set
     */
    public void setUserEmail(String value) {
        setProperty("userEmail", value);
    }

    /**
     * Return the email of the user.
     *
     * @return The email
     */
    public String getUserEmail() {
        return getProperty("userEmail");
    }

    /**
     * Set the SMTP host.
     *
     * @param value The new SMTP host.
     */
    public void setSmtpHost(String value) {
        setProperty("smtpHost", value);
    }

    /**
     * Return the SMTP host.
     *
     * @return The SMTP host.
     */
    public String getSmtpHost() {
        return getProperty("smtpHost");
    }

    public void setLastCollection(String value) {
        setProperty("last-collection", value);
    }

    public String getLastCollection() {
        return getProperty("last-collection");
    }

    /**
     * Set the date of the last reading of the messages.
     *
     * @param value The date of the last reading of the messages.
     */
    void setMessagesLastRead(String value) {
        setProperty("messagesLastRead", value);
    }

    /**
     * Return the date of the last reading of the messages.
     *
     * @return The date of the last reading of the messages.
     */
    public IntDate getMessagesLastRead() {
        if (getProperty("messagesLastRead") == null) {
            setMessagesLastRead("20000101");
        }

        return new IntDate(Integer.parseInt(getProperty("messagesLastRead")));
    }
}