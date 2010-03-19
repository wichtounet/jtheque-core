package org.jtheque.views.impl;

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
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.views.ViewsServices;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A controller for the main view.
 *
 * @author Baptiste Wicht
 */
public final class MainController extends WindowAdapter implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent event) {
        JTabbedPane tabbedPane = (JTabbedPane) event.getSource();

        int index = tabbedPane.getSelectedIndex();

        if (index == -1) {
            ViewsServices.get(ICore.class).getLifeCycle().setCurrentFunction("");
        } else {
            ViewsServices.get(ICore.class).getLifeCycle().setCurrentFunction(tabbedPane.getTitleAt(index));
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        boolean yes = ViewsServices.get(IUIUtils.class).getDelegate().askUserForConfirmation(
                ViewsServices.get(ILanguageService.class).getMessage("dialogs.confirm.exit",
                        ViewsServices.get(ICore.class).getApplication().getName()),
                ViewsServices.get(ILanguageService.class).getMessage("dialogs.confirm.exit.title",
                        ViewsServices.get(ICore.class).getApplication().getName()));

        if (yes) {
            ViewsServices.get(ICore.class).getLifeCycle().exit();
        }
    }
}