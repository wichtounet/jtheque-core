package org.jtheque.ui.utils.windows.dialogs;

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
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
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
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.osgi.context.BundleContextAware;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingDialogView<T extends IModel> extends JDialog
        implements IWindowView, InternationalizableContainer, BundleContextAware, ApplicationContextAware {
    private String titleKey;
    private Object[] titleReplaces;

    private T model;

    private boolean builded;

    private final Collection<Internationalizable> internationalizables = new ArrayList<Internationalizable>(10);
    private final Map<Object, Constraint> constraintCache = new HashMap<Object, Constraint>(5);

    private BundleContext bundleContext;
    private ApplicationContext applicationContext;

    private JXLayer<JComponent> content;
    private LockableUI busyPainterUI;

    private IController controller;

    /**
     * Construct a SwingDialogView modal to the main view.
     */
    protected SwingDialogView() {
        super(SimplePropertiesCache.<Frame>get("mainView"));
    }

    /**
     * Build the view.
     */
    @PostConstruct
    protected final void build() {
        setModal(true);
        setResizable(true);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setIconImage(getDefaultWindowIcon());

        getService(ILanguageService.class).addInternationalizable(this);

        content = new JXLayer<JComponent>();
        super.setContentPane(content);

        init();

        builded = true;
    }

    public void setContentPane(JComponent contentPane) {
        content.setView(contentPane);
    }

    @Override
    public Container getContentPane() {
        return content.getView();
    }

    @Override
    public void setContentPane(Container contentPane) {
        throw new UnsupportedOperationException("Cannot set the content pane");
    }

    @Override
    public void setGlassPane(final Component glassPane) {
        WindowHelper.applyGlassPane(glassPane, content);
        refresh();
    }

    /**
     * Init the view.
     */
    protected abstract void init();

    @Override
    public Component getGlassPane() {
        return content.getGlassPane();
    }

    @Override
    public void startWait() {
        installWaitUIIfNecessary();
        content.setUI(busyPainterUI);
        busyPainterUI.setLocked(true);
    }

    @Override
    public void stopWait() {
        if (busyPainterUI != null) {
            busyPainterUI.setLocked(false);
            content.setUI(null);
        }
    }

    /**
     * Create the wait UI only if this has not been done before.
     */
    private void installWaitUIIfNecessary() {
        if (busyPainterUI == null) {
            busyPainterUI = new BusyPainterUI(this);
        }
    }

    /**
     * Return the default window icon.
     *
     * @return The default window icon.
     */
    protected Image getDefaultWindowIcon() {
        return getService(IImageService.class).getImage(ICore.WINDOW_ICON);
    }

    /**
     * Return an action to close this view.
     *
     * @param key The i18n key.
     *
     * @return An action to close this view.
     */
    public Action getCloseAction(String key) {
        return ActionFactory.createCloseViewAction(key, this);
    }

    @Override
    public Action getControllerAction(String key, String action){
        return new ControllerAction(key, action, controller);
    }

    @Override
    public void closeDown() {
        setVisible(false);
    }

    @Override
    public void display() {
        if (!builded) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    build();
                }
            });
        }
        
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
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
        if (titleKey != null) {
            setTitleKey(titleKey, titleReplaces);
        }

        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }
    }

    /**
     * Set the model of the view.
     *
     * @param model The model of the view.
     */
    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public T getModel() {
        return model;
    }

    @Override
    public final JDialog getImpl() {
        return this;
    }

    @Override
    public final boolean validateContent() {
        Collection<IError> errors = new ArrayList<IError>(5);

        validate(errors);

        IErrorService errorService = getService(IErrorService.class);

        for (IError error : errors) {
            errorService.addError(error);
        }

        return errors.isEmpty();
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

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
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

    protected void setController(IController controller) {
        this.controller = controller;
    }
}