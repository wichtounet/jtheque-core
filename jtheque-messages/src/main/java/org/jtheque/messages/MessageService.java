package org.jtheque.messages;

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

import org.jtheque.core.ICore;
import org.jtheque.io.XMLException;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.utils.StringUtils;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.utils.bean.IntDate;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public final class MessageService implements IMessageService, BundleContextAware {
    private final Collection<Message> messages = new ArrayList<Message>(10);
    
    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public void loadMessages() {
        messages.clear();

        loadMessageFile(OSGiUtils.getService(bundleContext, ICore.class).getCoreMessageFileURL());
        loadApplicationMessages();

        for (Module module : OSGiUtils.getService(bundleContext, IModuleService.class).getModules()) {
            loadModuleMessages(module);
        }
    }

    @Override
    public boolean isDisplayNeeded() {
        IntDate previousDate = OSGiUtils.getService(bundleContext, ICore.class).getConfiguration().getMessagesLastRead();

        for (Message message : messages) {
            if (message.getDate().compareTo(previousDate) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Collection<Message> getMessages() {
        return messages;
    }

    /**
     * Load all the messages of the module.
     *
     * @param module The module to load messages from.
     */
    private void loadModuleMessages(Module module) {
        if (hasMessageFile(module)) {
            loadMessageFile(module.getMessagesUrl());
        }
    }

    /**
     * Indicate if a module has a message file.
     *
     * @param module The module.
     * @return true if the module has a message file else false.
     */
    private static boolean hasMessageFile(Module module) {
        return StringUtils.isNotEmpty(module.getMessagesUrl());
    }

    /**
     * Load the messages of the application.
     */
    private void loadApplicationMessages() {
        if (!StringUtils.isEmpty(OSGiUtils.getService(bundleContext, ICore.class).getApplication().getMessageFileURL())) {
            loadMessageFile(OSGiUtils.getService(bundleContext, ICore.class).getApplication().getMessageFileURL());
        }
    }

    /**
     * Load a messages files.
     *
     * @param url The URL of the messages file.
     */
    private void loadMessageFile(String url) {
        try {
            MessageFile file = MessageFileReader.readMessagesFile(url);

            messages.addAll(file.getMessages());
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }
}