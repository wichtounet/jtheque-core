package org.jtheque.core.managers.view.impl.actions.module;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.update.IUpdateView;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

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
 * An abstract update action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractUpdateAction extends JThequeAction {
    @Resource
    private IUpdateView updateView;

    /**
     * Construct a new AbstractUpdateAction.
     *
     * @param key The internationalization key.
     */
    AbstractUpdateAction(String key) {
        super(key);

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        try {
            if (isUpToDate()) {
                Managers.getManager(IViewManager.class).displayI18nText("message.update.no.version");
            } else {
                updateView.sendMessage(getMessage(), getSelectedObject());
                updateView.display();
            }
        } catch (HeadlessException e2) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e2);
            Managers.getManager(IErrorManager.class).addInternationalizedError("error.update.internet");
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