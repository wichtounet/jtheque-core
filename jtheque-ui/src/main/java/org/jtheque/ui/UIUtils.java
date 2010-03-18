package org.jtheque.ui;

import org.jtheque.i18n.ILanguageManager;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.able.ViewDelegate;
import org.jtheque.ui.utils.edt.SimpleTask;
import org.jtheque.ui.utils.edt.Task;

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

public class UIUtils implements IUIUtils {
    private final ViewDelegate viewDelegate;

    public UIUtils(ViewDelegate viewDelegate) {
        super();

        this.viewDelegate = viewDelegate;
    }

    @Override
    public boolean askI18nUserForConfirmation(String textKey, String titleKey) {
        return viewDelegate.askUserForConfirmation(
                ViewsUtilsServices.get(ILanguageManager.class).getMessage(textKey),
                ViewsUtilsServices.get(ILanguageManager.class).getMessage(titleKey));
    }

    @Override
    public void displayI18nText(String key) {
        viewDelegate.displayText(ViewsUtilsServices.get(ILanguageManager.class).getMessage(key));
    }

    @Override
    public ViewDelegate getDelegate() {
        return viewDelegate;
    }

    @Override
    public void execute(SimpleTask task) {
        viewDelegate.run(task.asRunnable());
    }

    @Override
    public <T> T execute(Task<T> task) {
        viewDelegate.run(task.asRunnable());

        return task.getResult();
    }
}