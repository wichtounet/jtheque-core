package org.jtheque.messages;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.ICore;
import org.jtheque.io.XMLException;
import org.jtheque.logging.ILoggingManager;
import org.jtheque.modules.able.IModuleManager;
import org.jtheque.modules.able.Module;
import org.jtheque.utils.StringUtils;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.utils.bean.IntDate;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public final class MessageManager implements IMessageManager, BundleContextAware {
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

        for (Module module : OSGiUtils.getService(bundleContext, IModuleManager.class).getModules()) {
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
            OSGiUtils.getService(bundleContext, ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }
}