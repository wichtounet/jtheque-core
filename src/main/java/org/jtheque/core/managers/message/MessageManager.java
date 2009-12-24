package org.jtheque.core.managers.message;

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

import org.jtheque.core.managers.IManager;
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.file.ReadingException;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.IntDate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public final class MessageManager implements IMessageManager, IManager {
    private final Collection<Message> messages = new ArrayList<Message>(10);

    @Override
    public void preInit() {
        //Nothing to do
    }

    @Override
    public void init() throws ManagerException {
        //Nothing to do
    }

    @Override
    public void close() throws ManagerException {
        //Nothing to do
    }

    @Override
    public void loadMessages() {
        messages.clear();

        loadCoreMessages();
        loadApplicationMessages();

        for (ModuleContainer module : Managers.getManager(IModuleManager.class).getModules()) {
            loadModuleMessages(module);
        }
    }

    @Override
    public void displayIfNeeded() {
        IntDate previousDate = Managers.getCore().getConfiguration().getMessagesLastRead();

        for (Message message : messages) {
            if (message.getDate().compareTo(previousDate) > 0) {
                Managers.getManager(IViewManager.class).getViews().getMessagesView().display();

                break;
            }
        }
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
    private void loadModuleMessages(ModuleContainer module) {
        if (hasMessageFile(module)) {
            loadMessageFile(module.getInfos().messageFileURL());
        }
    }

    /**
     * Indicate if a module has a message file.
     *
     * @param module The module.
     * @return true if the module has a message file else false.
     */
    private static boolean hasMessageFile(ModuleContainer module) {
        return StringUtils.isNotEmpty(module.getInfos().messageFileURL());
    }

    /**
     * Load the messages of the application.
     */
    private void loadApplicationMessages() {
        if (!StringUtils.isEmpty(Managers.getCore().getApplication().getMessageFileURL())) {
            loadMessageFile(Managers.getCore().getApplication().getMessageFileURL());
        }
    }

    /**
     * Load the messages of the core.
     */
    private void loadCoreMessages() {
        loadMessageFile(Managers.getCore().getCoreMessageFileURL());
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
        } catch (ReadingException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }
}