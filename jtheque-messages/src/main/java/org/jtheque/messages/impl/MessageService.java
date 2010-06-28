package org.jtheque.messages.impl;

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

import org.jtheque.core.able.ICore;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.IEventService;
import org.jtheque.events.utils.Event;
import org.jtheque.messages.able.IMessage;
import org.jtheque.messages.able.IMessageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.io.WebUtils;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public final class MessageService implements IMessageService, ModuleListener {
    private final Collection<IMessage> messages = new ArrayList<IMessage>(10);

    private final ICore core;
    private final IErrorService errorService;
    private final IEventService eventService;

    /**
     * Construct a new MessageService.
     *
     * @param core          The core.
     * @param moduleService The module service.
     */
    public MessageService(ICore core, IModuleService moduleService, IErrorService errorService, IEventService eventService) {
        super();

        this.core = core;
        this.errorService = errorService;
        this.eventService = eventService;

        moduleService.addModuleListener("", this);
    }

    @Override
    public void loadMessages() {
        messages.clear();

        loadMessageFile(core.getCoreMessageFileURL(), null);
        loadApplicationMessages();
    }

    /**
     * Load the messages of the application.
     */
    private void loadApplicationMessages() {
        if (!StringUtils.isEmpty(core.getApplication().getMessageFileURL())) {
            loadMessageFile(core.getApplication().getMessageFileURL(), null);
        }
    }

    @Override
    public boolean isDisplayNeeded() {
        IntDate previousDate = core.getConfiguration().getMessagesLastRead();

        for (IMessage message : messages) {
            if (message.getDate().compareTo(previousDate) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IMessage getEmptyMessage() {
        IMessage defaultMessage = new Message();
        defaultMessage.setDate(IntDate.today());
        defaultMessage.setId(-1);
        defaultMessage.setSource("");
        defaultMessage.setMessage("");
        defaultMessage.setTitle("");

        return defaultMessage;
    }

    @Override
    public Collection<IMessage> getMessages() {
        return messages;
    }

    @Override
    public void moduleStopped(Module module) {
        messages.removeAll(ModuleResourceCache.getResource(module.getId(), IMessage.class));
    }

    @Override
    public void moduleStarted(Module module) {
        if (StringUtils.isNotEmpty(module.getMessagesUrl())) {
            loadMessageFile(module.getMessagesUrl(), module);
        }
    }

    @Override
    public void moduleInstalled(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleUninstalled(Module module) {
        //Nothing to do here
    }

    /**
     * Load a messages files.
     *
     * @param url    The URL of the messages file.
     * @param module The module to load the message file from.
     */
    private void loadMessageFile(String url, Module module) {
        if (WebUtils.isURLReachable(core.getCoreMessageFileURL())) {
            try {
                MessageFile file = MessageFileReader.readMessagesFile(url);

                for (IMessage message : file.getMessages()) {
                    messages.add(message);

                    ModuleResourceCache.addResource(module.getId(), IMessage.class, message);
                }
            } catch (XMLException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        } else {
            if (WebUtils.isInternetReachable()) {
                errorService.addInternationalizedError(
                        "messages.network.resource.title", ArrayUtils.EMPTY_ARRAY,
                        "messages.network.resource", new Object[]{url});
            } else {
                errorService.addInternationalizedError(
                        "messages.network.internet.title", ArrayUtils.EMPTY_ARRAY,
                        "messages.network.internet", new Object[]{url});
            }

            eventService.addEvent(IEventService.CORE_EVENT_LOG,
                    new Event(EventLevel.ERROR, "System", "events.messages.network"));
        }
    }
}