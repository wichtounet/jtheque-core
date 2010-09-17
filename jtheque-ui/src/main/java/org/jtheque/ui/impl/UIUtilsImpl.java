package org.jtheque.ui.impl;

import org.jtheque.core.Core;
import org.jtheque.i18n.LanguageService;
import org.jtheque.images.ImageService;
import org.jtheque.states.StateService;
import org.jtheque.ui.UIUtils;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.ui.SwingUtils;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;

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
@ThreadSafe
public final class UIUtilsImpl implements UIUtils {
    private static final String MAIN_VIEW_CACHE = "mainView";

    private final WindowsConfiguration configuration;

    @Resource
    private LanguageService languageService;

    /**
     * Create a new UIUtilsImpl.
     *
     * @param stateService The state service.
     * @param imageService The resource service.
     * @param core         The core
     */
    public UIUtilsImpl(StateService stateService, ImageService imageService, Core core) {
        super();

        configuration = stateService.getState(new WindowsConfiguration(core));

        imageService.registerResource(LIGHT_IMAGE, new ClassPathResource("org/jtheque/ui/light.png"));
    }

    @Override
    public void saveState(Window window, String name) {
        configuration.update(name, window);
    }

    @Override
    public void configureView(Window view, String name, int defaultWidth, int defaultHeight) {
        configuration.configure(name, view, defaultWidth, defaultHeight);
    }

    @Override
    public boolean askI18nUserForConfirmation(String textKey, String titleKey) {
        return askUserForConfirmation(languageService.getMessage(textKey), languageService.getMessage(titleKey));
    }

    @Override
    public boolean askI18nUserForConfirmation(String textKey, Object[] textReplaces, String titleKey, Object[] titleReplaces) {
        return askUserForConfirmation(
                languageService.getMessage(textKey, textReplaces),
                languageService.getMessage(titleKey, titleReplaces));
    }

    @Override
    public void displayI18nText(String key) {
        displayText(languageService.getMessage(key));
    }

    @Override
    public String askI18nText(String key) {
        return askText(languageService.getMessage(key));
    }

    @Override
    public boolean askUserForConfirmation(final String text, final String title) {
        boolean yes = false;

        Window parent = null;

        if (SimplePropertiesCache.get(MAIN_VIEW_CACHE, Window.class) != null) {
            parent = SimplePropertiesCache.get(MAIN_VIEW_CACHE, Window.class);
        }

        final Window p = parent;

        final int[] response = new int[1];

        if (SwingUtilities.isEventDispatchThread()) {
            response[0] = JOptionPane.showConfirmDialog(parent, text, title, JOptionPane.YES_NO_OPTION);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        response[0] = JOptionPane.showConfirmDialog(p, text, title, JOptionPane.YES_NO_OPTION);
                    }
                });
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }

        if (response[0] == JOptionPane.YES_OPTION) {
            yes = true;
        }

        return yes;
    }

    @Override
    public void displayText(String text) {
        run(new DisplayTextRunnable(text));
    }

    @Override
    public void run(Runnable runnable) {
        SwingUtils.inEdt(runnable);
    }

    @Override
    public String askText(String text) {
        Window parent = null;

        if (SimplePropertiesCache.get(MAIN_VIEW_CACHE, Component.class) != null) {
            parent = (Window) SimplePropertiesCache.get(MAIN_VIEW_CACHE, Component.class);
        }

        return JOptionPane.showInputDialog(parent, text);
    }

    /**
     * A Runnable to display a text.
     *
     * @author Baptiste Wicht
     */
    private static final class DisplayTextRunnable implements Runnable {
        private final String text;

        /**
         * The text to display.
         *
         * @param text The text to display.
         */
        DisplayTextRunnable(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            JOptionPane.showMessageDialog(SimplePropertiesCache.get(MAIN_VIEW_CACHE, Component.class), text);
        }
    }
}