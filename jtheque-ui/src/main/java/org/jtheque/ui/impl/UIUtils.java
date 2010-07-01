package org.jtheque.ui.impl;

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.images.able.IImageService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.able.ViewDelegate;

import org.springframework.core.io.ClassPathResource;

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
 * An UI Utils implementation.
 *
 * @author Baptiste Wicht
 */
public class UIUtils implements IUIUtils {
    private final ViewDelegate viewDelegate;
    private final ILanguageService languageService;

    /**
     * Create a new UIUtils.
     *
     * @param viewDelegate    The view delegate.
     * @param languageService The language service.
     * @param imageService    The resource service.
     */
    public UIUtils(ViewDelegate viewDelegate, ILanguageService languageService, IImageService imageService) {
        super();

        this.viewDelegate = viewDelegate;
        this.languageService = languageService;

        imageService.registerResource(LIGHT_IMAGE, new ClassPathResource("org/jtheque/ui/light.png"));
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
    public String askI18nText(String key) {
        return viewDelegate.askText(languageService.getMessage(key));
    }

    @Override
    public ViewDelegate getDelegate() {
        return viewDelegate;
    }
}