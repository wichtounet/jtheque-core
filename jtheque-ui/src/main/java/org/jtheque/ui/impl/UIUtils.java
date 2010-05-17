package org.jtheque.ui.impl;

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.able.ViewDelegate;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.pushingpixels.trident.TridentConfig;
import org.pushingpixels.trident.interpolator.CorePropertyInterpolators;
import org.pushingpixels.trident.swing.AWTPropertyInterpolators;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

    public UIUtils(ViewDelegate viewDelegate, ILanguageService languageService, IResourceService resourceService) {
        super();

        this.viewDelegate = viewDelegate;
        this.languageService = languageService;

	    resourceService.registerResource(LIGHT_IMAGE, new ClassPathResource("org/jtheque/ui/light.png"));
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