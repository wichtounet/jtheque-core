package org.jtheque.views.impl.actions.module;

import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.windows.IUpdateView;

import java.awt.event.ActionEvent;

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

/**
 * An abstract org.jtheque.update action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractUpdateAction extends JThequeAction {
    private final IViews views;
    private final IUIUtils uiUtils;
    private final IUpdateService updateService;

    /**
     * Construct a new AbstractUpdateAction.
     *
     * @param key           The internationalization key.
     * @param updateService
     * @param uiUtils
     * @param views
     */
    AbstractUpdateAction(String key, IUpdateService updateService, IUIUtils uiUtils, IViews views) {
        super(key);

        this.updateService = updateService;
        this.uiUtils = uiUtils;
        this.views = views;
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        IUpdateView updateView = views.getUpdateView();

        if (isUpToDate()) {
            uiUtils.displayI18nText("message.update.no.version");
        } else {
            updateView.sendMessage(getMessage(), getSelectedObject());
            updateView.display();
        }
    }

    /**
     * Indicate if the selected object is up to date.
     *
     * @return true if the selected object is up to date.
     */
    abstract boolean isUpToDate();

    /**
     * Return the message to send.
     *
     * @return The message to send.
     */
    abstract String getMessage();

    /**
     * Return the selected object.
     *
     * @return The selected object.
     */
    abstract Object getSelectedObject();

    /**
     * Return the views service.
     *
     * @return The views.
     */
    IViews getViews() {
        return views;
    }

    /**
     * Return the update service.
     *
     * @return The update service.
     */
    IUpdateService getUpdateService() {
        return updateService;
    }
}