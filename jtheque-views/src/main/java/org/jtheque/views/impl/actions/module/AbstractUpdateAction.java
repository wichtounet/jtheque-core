package org.jtheque.views.impl.actions.module;

import org.jtheque.errors.IErrorService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.update.IUpdateService;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.windows.IUpdateView;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

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

/**
 * An abstract org.jtheque.update action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractUpdateAction extends JThequeAction {
    @Resource
    private IViewService viewService;

    @Resource
    private IViews views;

    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IErrorService errorService;

    @Resource
    private IUpdateService updateService;

    /**
     * Construct a new AbstractUpdateAction.
     *
     * @param key The internationalization key.
     */
    AbstractUpdateAction(String key) {
        super(key);
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        IUpdateView updateView = views.getUpdateView();

        try {
            if (isUpToDate()) {
                uiUtils.displayI18nText("message.update.no.version");
            } else {
                updateView.sendMessage(getMessage(), getSelectedObject());
                updateView.display();
            }
        } catch (HeadlessException e2) {
            LoggerFactory.getLogger(getClass()).error(e2.getMessage(), e2);
            errorService.addInternationalizedError("error.update.internet");
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

    IViews getViews() {
        return views;
    }

    IUpdateService getUpdateService() {
        return updateService;
    }
}