package org.jtheque.ui;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.able.ViewDelegate;

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