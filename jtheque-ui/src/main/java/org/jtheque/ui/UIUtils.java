package org.jtheque.ui;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.able.ViewDelegate;

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
    private final ILanguageService languageService;

    public UIUtils(ViewDelegate viewDelegate, ILanguageService languageService) {
        super();

        this.viewDelegate = viewDelegate;
        this.languageService = languageService;
    }

    @Override
    public boolean askI18nUserForConfirmation(String textKey, String titleKey) {
        return viewDelegate.askUserForConfirmation(languageService.getMessage(textKey), languageService.getMessage(titleKey));
    }

    @Override
    public boolean askI18nUserForConfirmation(String textKey, Object[] textReplaces, String titleKey, Object[] titleReplaces) {
        return viewDelegate.askUserForConfirmation(
                languageService.getMessage(textKey, textReplaces),
                languageService.getMessage(titleKey, titleReplaces));
    }

    @Override
    public void displayI18nText(String key) {
        viewDelegate.displayText(languageService.getMessage(key));
    }

    @Override
    public ViewDelegate getDelegate() {
        return viewDelegate;
    }
}