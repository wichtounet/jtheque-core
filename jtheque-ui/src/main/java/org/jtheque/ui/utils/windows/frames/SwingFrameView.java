package org.jtheque.ui.utils.windows.frames;

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

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IError;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.i18n.able.InternationalizableContainer;
import org.jtheque.images.able.IImageService;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.able.IWindowView;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.utils.actions.ControllerAction;
import org.jtheque.ui.utils.constraints.Constraint;
import org.jtheque.ui.utils.windows.BusyPainterUI;
import org.jtheque.ui.utils.windows.WindowHelper;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.jdesktop.jxlayer.JXLayer;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.osgi.context.BundleContextAware;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A swing frame view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingFrameView<T extends IModel> extends JFrame
        implements IWindowView, BundleContextAware, ApplicationContextAware, InternationalizableContainer {
    private String titleKey;
    private Object[] titleReplaces;

    private final Collection<Internationalizable> internationalizables = new ArrayList<Internationalizable>(10);
    private final Map<Object, Constraint> constraintCache = new HashMap<Object, Constraint>(5);

    private T model;

    private JXLayer<JComponent> content;
    private BusyPainterUI waitUI;

    private boolean builded;
    
    private ApplicationContext applicationContext;
    private BundleContext bundleContext;

    private IController controller;

    /**
     * Build the view.
     */
    @PostConstruct
    protected final void build() {
        setResizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setIconImage(getDefaultWindowIcon());

        getService(ILanguageService.class).addInternationalizable(this);

        content = new JXLayer<JComponent>();
        super.setContentPane(content);

        init();

        SwingUtils.centerFrame(this);

        builded = true;
    }

    public void setContentPane(JComponent contentPane) {
        content.setView(contentPane);
    }

    @Override
    public void setContentPane(Container contentPane) {
        throw new UnsupportedOperationException("Cannot set the content pane");
    }

    @Override
    public Container getContentPane() {
        return content.getView();
    }

    @Override
    public void setGlassPane(Component glassPane) {
        WindowHelper.applyGlassPane(glassPane, content);
        refresh();
    }

    /**
     * Init the view.
     */
    protected abstract void init();

    /**
     * Return the default icon for the window icon.
     *
     * @return The default icon.
     */
    protected Image getDefaultWindowIcon() {
        return getService(IImageService.class).getImage(ICore.WINDOW_ICON);
    }

    @Override
    public Component getGlassPane() {
        return content.getGlassPane();
    }

    @Override
    public void closeDown() {
        setVisible(false);
    }

    @Override
    public final void toFirstPlan() {
        toFront();
    }

    @Override
    public void sendMessage(String message, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void refresh() {
        SwingUtils.refresh(this);
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Set the model of the view.
     *
     * @param model The model of the view.
     */
    public final void setModel(T model) {
        this.model = model;
    }

    @Override
    public final T getModel() {
        return model;
    }

    @Override
    public final JFrame getImpl() {
        return this;
    }

    /**
     * Return the content pane.
     *
     * @return the content pane.
     */
    public JXLayer<JComponent> getContent() {
        return content;
    }

    @Override
    public void startWait() {
        installWaitUIIfNecessary();
        content.setUI(waitUI);
        waitUI.setLocked(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    @Override
    public void stopWait() {
        if (waitUI != null) {
            waitUI.setLocked(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            content.setUI(null);
        }
    }

    /**
     * Create the wait UI only if this has not been done before.
     */
    private void installWaitUIIfNecessary() {
        if (waitUI == null) {
            waitUI = new BusyPainterUI(content);
        }
    }

    @Override
    public boolean validateContent() {
        return true;
    }

    @Override
    public void display() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                if (!builded) {
                    build();
                }
            }
        });

        setVisible(true);
    }

    /**
     * Set the title key.
     *
     * @param key      The internationalization key.
     * @param replaces The replacements objects for the i18n methods.
     */
    protected final void setTitleKey(String key, Object... replaces) {
        titleKey = key;

        if (!ArrayUtils.isEmpty(replaces)) {
            titleReplaces = ArrayUtils.copyOf(replaces);
        }

        setTitle(getMessage(key, replaces));
    }

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        if (StringUtils.isNotEmpty(titleKey)) {
            setTitleKey(titleKey, titleReplaces);
        }

        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }
    }

    /**
     * Validate the view and save all the validation's errors in the list.
     *
     * @param errors The error's list.
     */
    protected void validate(Collection<IError> errors) {
        for (Map.Entry<Object, Constraint> constraint : constraintCache.entrySet()) {
            constraint.getValue().validate(constraint.getKey(), errors);
        }
    }

    /**
     * Add the constraint to the view. The filed will be configured by the constraint.
     *
     * @param field      The field to validate.
     * @param constraint The constraint to add.
     */
    protected void addConstraint(Object field, Constraint constraint) {
        constraintCache.put(field, constraint);

        constraint.configure(field);
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

    /**
     * Return the bean of the given class using the application context.
     *
     * @param classz The classz of the bean to get from application context.
     * @param <T>    The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
    protected <T> T getBean(Class<T> classz) {
        return applicationContext.getBean(classz);
    }

    /**
     * Return the bean of the given class using the application context. The bean will be retrieved in the EDT, so it
     * can be used for a Swing bean.
     *
     * @param classz The classz of the bean to get from application context.
     * @param <T>    The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
    protected <T> T getBeanFromEDT(Class<T> classz) {
        return new SwingSpringProxy<T>(classz, applicationContext).get();
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

    protected void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public Action getControllerAction(String key, String action) {
        return ActionFactory.createControllerAction(key, action, controller);
    }
}