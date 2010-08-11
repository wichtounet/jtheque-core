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

import org.jtheque.core.ApplicationListener;
import org.jtheque.core.Core;
import org.jtheque.core.application.Application;
import org.jtheque.core.utils.WebHelper;
import org.jtheque.messages.Message;
import org.jtheque.modules.Module;
import org.jtheque.modules.ModuleListener;
import org.jtheque.modules.ModuleResourceCache;
import org.jtheque.modules.ModuleService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.util.Collection;

/**
 * A message service.
 *
 * @author Baptiste Wicht
 */
public final class MessageService implements org.jtheque.messages.MessageService, ModuleListener, ApplicationListener {
    private final Collection<Message> messages = CollectionUtils.newConcurrentList();

    @Resource
    private WebHelper webHelper;

    private final Core core;

    /**
     * Construct a new MessageService.
     *
     * @param core          The core.
     * @param moduleService The module service.
     */
    public MessageService(Core core, ModuleService moduleService) {
        super();

        this.core = core;

        moduleService.addModuleListener("", this);
        core.addApplicationListener(this);

        loadMessageFile(core.getCoreMessageFileURL(), null);
    }

    @Override
    public boolean isDisplayNeeded() {
        IntDate previousDate = core.getConfiguration().getMessagesLastRead();

        for (Message message : messages) {
            if (message.getDate().compareTo(previousDate) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Message getEmptyMessage() {
        return Messages.newEmptyTodayMessage(-1);
    }

    @Override
    public Collection<Message> getMessages() {
        return CollectionUtils.protect(messages);
    }

    @Override
    public void moduleStopped(Module module) {
        messages.removeAll(ModuleResourceCache.getResources(module.getId(), Message.class));
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
        if (webHelper.isNotReachable(url)) {
            return;
        }

        try {
            Collection<Message> readMessages = MessageFileReader.readMessagesFile(url);

            messages.addAll(readMessages);
            ModuleResourceCache.addAllResource(module.getId(), Message.class, readMessages);
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public void applicationLaunched(Application application) {
        if (!StringUtils.isEmpty(application.getMessageFileURL())) {
            loadMessageFile(application.getMessageFileURL(), null);
        }
    }
}