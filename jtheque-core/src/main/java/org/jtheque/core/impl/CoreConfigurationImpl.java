package org.jtheque.core.impl;

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

import org.jtheque.core.CoreConfiguration;
import org.jtheque.states.State;
import org.jtheque.states.utils.AbstractConcurrentState;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.IntDate;

/**
 * The configuration of the core.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
@State(id = "jtheque-core-configuration")
public final class CoreConfigurationImpl extends AbstractConcurrentState implements CoreConfiguration {
    private static final String FALSE = "false";
    private static final String TRUE = "true";

    @Override
    public boolean verifyUpdateOnStartup() {
        return Boolean.parseBoolean(getProperty("verifyOnStartup", FALSE));
    }

    @Override
    public void setVerifyUpdateOnStartup(boolean value) {
        setProperty("verifyOnStartup", String.valueOf(value));
    }

    @Override
    public boolean retainSizeAndPositionOfWindow() {
        return Boolean.parseBoolean(getProperty("retainSizeAndPositionOfWindow", TRUE));
    }

    @Override
    public void setRetainSizeAndPositionOfWindow(boolean value) {
        setProperty("retainSizeAndPositionOfWindow", Boolean.toString(value));
    }

    @Override
    public boolean hasAProxy() {
        return Boolean.parseBoolean(getProperty("proxy", FALSE));
    }

    @Override
    public void setHasAProxy(boolean value) {
        setProperty("proxy", Boolean.toString(value));
    }

    @Override
    public void setProxyPort(String value) {
        setProperty("proxyPort", value);
    }

    @Override
    public String getProxyPort() {
        return getProperty("proxyPort");
    }

    @Override
    public void setProxyAddress(String value) {
        setProperty("proxyAddress", value);
    }

    @Override
    public String getProxyAddress() {
        return getProperty("proxyAddress");
    }

    @Override
    public void setUserEmail(String value) {
        setProperty("userEmail", value);
    }

    @Override
    public String getUserEmail() {
        return getProperty("userEmail");
    }

    @Override
    public void setSmtpHost(String value) {
        setProperty("smtpHost", value);
    }

    @Override
    public String getSmtpHost() {
        return getProperty("smtpHost");
    }

    @Override
    public void setLastCollection(String value) {
        setProperty("last-collection", value);
    }

    @Override
    public String getLastCollection() {
        return getProperty("last-collection");
    }

    @Override
    public IntDate getMessagesLastRead() {
        if (getProperty("messagesLastRead") == null) {
            setProperty("messagesLastRead", "20000101");
        }

        return new IntDate(Integer.parseInt(getProperty("messagesLastRead")));
    }
}