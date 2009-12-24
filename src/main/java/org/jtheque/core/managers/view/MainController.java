package org.jtheque.core.managers.view;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;

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
            Managers.getCore().getLifeCycleManager().setCurrentFunction("");
        } else {
            Managers.getCore().getLifeCycleManager().setCurrentFunction(tabbedPane.getTitleAt(index));
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("dialogs.confirm.exit",
                        Managers.getCore().getApplication().getName()),
                Managers.getManager(ILanguageManager.class).getMessage("dialogs.confirm.exit.title",
                        Managers.getCore().getApplication().getName()));

        if (yes) {
            Managers.getCore().getLifeCycleManager().exit();
        }
    }
}