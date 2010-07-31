package org.jtheque.ui.utils.windows;

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.images.able.ImageService;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IWindowState;
import org.jtheque.ui.able.constraints.Constraint;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Window;
import java.util.Collection;
import java.util.Map;

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
 * A Window state implementation. This state manage the state of Dialogs and Frames.
 *
 * @author Baptiste Wicht
 */
public final class WindowState implements IWindowState {
    private String titleKey;
    private Object[] titleReplaces;

    private final Collection<Internationalizable> internationalizables = CollectionUtils.newList();
    private final Map<Object, Constraint> constraintCache = CollectionUtils.newHashMap(5);

    private final Window window;
    private JXLayer<JComponent> content;

    private IController<?> controller;
    private LockableUI waitUI;

    private BundleContext bundleContext;
    private ApplicationContext applicationContext;

    private boolean builded;

    /**
     * Construct a new WindowState.
     *
     * @param window The window to manage.
     */
    public WindowState(Window window) {
        super();

        this.window = window;
    }

    /**
     * Build the state.
     */
    public void build() {
        content = new JXLayer<JComponent>();

        window.setIconImage(getDefaultWindowIcon());

        builded = true;
    }

    /**
     * Return the default icon for the window icon.
     *
     * @return The default icon.
     */
    protected Image getDefaultWindowIcon() {
        return getService(ImageService.class).getImage(ICore.WINDOW_ICON);
    }

    /**
     * Return the service of the given class.
     *
     * @param classz The class to get the service.
     * @param <T>    The type of service.
     *
     * @return The service of the given class if it's exists otherwise null.
     */
    protected <T> T getService(Class<T> classz) {
        return OSGiUtils.getService(bundleContext, classz);
    }

    @Override
    public void startWait() {
        installWaitUIIfNecessary();
        content.setUI(waitUI);
        waitUI.setLocked(true);
        window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    @Override
    public void stopWait() {
        if (waitUI != null) {
            waitUI.setLocked(false);
            window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            content.setUI(null);
        }
    }

    /**
     * Create the wait UI only if this has not been done before.
     */
    private void installWaitUIIfNecessary() {
        if (waitUI == null) {
            waitUI = new BusyPainterUI(window);
        }
    }

    /**
     * Return the content pane of the view.
     *
     * @return The content pane of the view.
     */
    public JXLayer<JComponent> getContent() {
        return content;
    }

    /**
     * Set a glass pane over the view.
     *
     * @param glassPane The glass pane to set over the view.
     */
    public void setGlassPane(Component glassPane) {
        SwingUtils.inEdt(new GlassPaneSetter(glassPane));
    }

    /**
     * Display the view.
     */
    public void display() {
        if (!builded) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    ((ManagedWindow) window).build();
                }
            });
        }

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                window.setVisible(true);
            }
        });
    }

    /**
     * Return the controller of the view.
     *
     * @return The controller of the view.
     */
    public IController<?> getController() {
        return controller;
    }

    /**
     * Set the controller of the view.
     *
     * @param controller The controller of the view.
     */
    public void setController(IController<?> controller) {
        this.controller = controller;
    }

    /**
     * Return the bundle context.
     *
     * @return The bundle context of the view.
     */
    public BundleContext getBundleContext() {
        return bundleContext;
    }

    /**
     * Return the bundle context.
     *
     * @param bundleContext The bundle context.
     */
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    /**
     * Return the application context.
     *
     * @return The application context.
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Return the application context.
     *
     * @param applicationContext The application context.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Add an internationalizable to the container.
     *
     * @param internationalizable The internationalizable to add.
     */
    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    /**
     * Refresh the title of the window.
     *
     * @param languageService The language service to use for i18n.
     */
    public void refreshText(LanguageService languageService) {
        if (StringUtils.isNotEmpty(titleKey)) {
            setTitleKey(titleKey, titleReplaces);
        }

        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }
    }

    /**
     * Set the title key of the window.
     *
     * @param key      The key of the title.
     * @param replaces The replaces of the title.
     */
    public void setTitleKey(String key, Object... replaces) {
        titleKey = key;

        if (!ArrayUtils.isEmpty(replaces)) {
            titleReplaces = ArrayUtils.copyOf(replaces);
        }

        ((ManagedWindow) window).setTitle(getMessage(key, replaces));
    }

    /**
     * Return the internationalized message.
     *
     * @param key The internationalization key.
     *
     * @return The internationalized message.
     */
    protected String getMessage(String key) {
        return getService(LanguageService.class).getMessage(key);
    }

    /**
     * Return the internationalized message.
     *
     * @param key      The internationalization key.
     * @param replaces The replacement objects to use.
     *
     * @return the internationalized message.
     */
    protected String getMessage(String key, Object... replaces) {
        return getService(LanguageService.class).getMessage(key, replaces);
    }

    /**
     * Add a constraint to the view.
     *
     * @param field      The field to validate.
     * @param constraint The constraint to add to the view.
     */
    public void addConstraint(Object field, Constraint constraint) {
        constraintCache.put(field, constraint);

        constraint.configure(field);
    }

    /**
     * Validate the content of the view.
     *
     * @return true if the view content is valid else false.
     */
    public boolean validateContent() {
        Collection<IError> errors = CollectionUtils.newList(5);

        ((ManagedWindow) window).validate(errors);

        IErrorService errorService = getService(IErrorService.class);

        for (IError error : errors) {
            errorService.addError(error);
        }

        return errors.isEmpty();
    }

    /**
     * Validate the view using the setted constraints.
     *
     * @param errors The errors collection to fill.
     */
    public void validate(Collection<IError> errors) {
        for (Map.Entry<Object, Constraint> constraint : constraintCache.entrySet()) {
            constraint.getValue().validate(constraint.getKey(), errors);
        }
    }

    /**
     * A simple runnable to apply a glass pane to the view associated with this state.
     *
     * @author Baptiste Wicht
     */
    private final class GlassPaneSetter implements Runnable {
        private final Component glassPane;

        /**
         * Construct a new GlassPaneSetter for the given glass pane.
         * @param glassPane The glass pane to set on the view. 
         */
        private GlassPaneSetter(Component glassPane) {
            this.glassPane = glassPane;
        }

        @Override
        public void run() {
            if (glassPane == null) {
                JPanel glasspane = content.createGlassPane();

                glasspane.setVisible(false);
                glasspane.setOpaque(false);

                content.setGlassPane(glasspane);
            } else {
                content.setGlassPane((JPanel) glassPane);

                glassPane.setVisible(true);
                glassPane.repaint();

                SwingUtilities.updateComponentTreeUI(glassPane);
            }

            SwingUtilities.updateComponentTreeUI(content);
        }
    }
}