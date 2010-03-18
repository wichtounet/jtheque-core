package org.jtheque.views.impl.actions.module;

import org.jtheque.errors.IErrorManager;
import org.jtheque.logging.ILoggingManager;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.frame.IUpdateView;

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
 * An abstract update action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractUpdateAction extends JThequeAction {
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
        IUpdateView updateView = ViewsServices.get(IViewManager.class).getViews().getUpdateView();

        try {
            if (isUpToDate()) {
                ViewsServices.get(IUIUtils.class).displayI18nText("message.update.no.version");
            } else {
                updateView.sendMessage(getMessage(), getSelectedObject());
                updateView.display();
            }
        } catch (HeadlessException e2) {
            ViewsServices.get(ILoggingManager.class).getLogger(getClass()).error(e2);
            ViewsServices.get(IErrorManager.class).addInternationalizedError("error.update.internet");
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
}