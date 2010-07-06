package org.jtheque.ui.utils.windows;

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.images.able.IImageService;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IWindowState;
import org.jtheque.ui.utils.constraints.Constraint;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.ArrayUtils;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
 * Created by IntelliJ IDEA. User: wichtounet Date: Jul 6, 2010 Time: 10:49:45 AM To change this template use File |
 * Settings | File Templates.
 */
public class WindowState implements IWindowState {
    private String titleKey;
    private Object[] titleReplaces;

    private final Collection<Internationalizable> internationalizables = new ArrayList<Internationalizable>(10);
    private final Map<Object, Constraint> constraintCache = new HashMap<Object, Constraint>(5);

    private final Window window;
    private JXLayer<JComponent> content;

    private IController controller;
    private LockableUI waitUI;

    private BundleContext bundleContext;
    private ApplicationContext applicationContext;

    private boolean builded;

    public WindowState(Window window) {
        super();

        this.window = window;
    }

    public void build(){
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
        return getService(IImageService.class).getImage(ICore.WINDOW_ICON);
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

    public JXLayer<JComponent> getContent() {
        return content;
    }

    public void setGlassPane(final Component glassPane) {
        SwingUtils.inEdt(new Runnable() {
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
        });
    }
    
    public void display() {
        if (!builded) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    ((ManagedWindow)window).build();
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

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    public void refreshText(ILanguageService languageService) {
        if (StringUtils.isNotEmpty(titleKey)) {
            setTitleKey(titleKey, titleReplaces);
        }

        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }
    }

    public void setTitleKey(String key, Object... replaces) {
        titleKey = key;

        if (!ArrayUtils.isEmpty(replaces)) {
            titleReplaces = ArrayUtils.copyOf(replaces);
        }

        ((ManagedWindow)window).setTitle(getMessage(key, replaces));
    }

    /**
     * Return the internationalized message.
     *
     * @param key The internationalization key.
     *
     * @return The internationalized message.
     */
    protected String getMessage(String key) {
        return getService(ILanguageService.class).getMessage(key);
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
        return getService(ILanguageService.class).getMessage(key, replaces);
    }

    public void addConstraint(Object field, Constraint constraint) {
        constraintCache.put(field, constraint);

        constraint.configure(field);
    }

    public boolean validateContent() {
        Collection<IError> errors = new ArrayList<IError>(5);

        ((ManagedWindow)window).validate(errors);

        IErrorService errorService = getService(IErrorService.class);

        for (IError error : errors) {
            errorService.addError(error);
        }

        return errors.isEmpty();
    }

    public void validate(Collection<IError> errors) {
        for (Map.Entry<Object, Constraint> constraint : constraintCache.entrySet()) {
            constraint.getValue().validate(constraint.getKey(), errors);
        }
    }
}