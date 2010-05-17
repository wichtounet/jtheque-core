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
import org.jtheque.messages.able.IMessage;
import org.jtheque.messages.able.IMessageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.IntDate;
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

	public MessageService(ICore core, IModuleService moduleService) {
		super();

		this.core = core;

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
	public void moduleStateChanged(Module module, ModuleState newState, ModuleState oldState) {
		if(oldState == null){
			if(StringUtils.isNotEmpty(module.getMessagesUrl())){
				loadMessageFile(module.getMessagesUrl(), module);
			}
		} else if(oldState == ModuleState.LOADED && (newState == ModuleState.INSTALLED ||
                newState == ModuleState.DISABLED || newState == ModuleState.UNINSTALLED)){
			messages.removeAll(ModuleResourceCache.getResource(module.getId(), IMessage.class));
		}
	}

    /**
     * Load a messages files.
     *
     * @param url The URL of the messages file.
     */
    private void loadMessageFile(String url, Module module) {
        try {
            MessageFile file = MessageFileReader.readMessagesFile(url);

	        for(IMessage message : file.getMessages()){
		        messages.add(message);

		        ModuleResourceCache.addResource(module.getId(), IMessage.class, message);
	        }
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }
}