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

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.i18n.InternationalizableContainer;
import org.jtheque.i18n.LanguageService;
import org.jtheque.ui.Controller;
import org.jtheque.ui.Model;
import org.jtheque.ui.WindowState;
import org.jtheque.ui.WindowView;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.constraints.Constraint;
import org.jtheque.ui.utils.windows.ManagedWindow;
import org.jtheque.utils.ui.SwingUtils;

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
import java.util.Collection;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingDialogView<T extends Model> extends JDialog
        implements ManagedWindow, WindowView, InternationalizableContainer, BundleContextAware, ApplicationContextAware {
    private T model;

    private final org.jtheque.ui.utils.windows.WindowState state = new org.jtheque.ui.utils.windows.WindowState(this);

    /**
     * Construct a SwingDialogView modal to the main view.
     */
    protected SwingDialogView() {
        super(SimplePropertiesCache.<Frame>get("mainView", Frame.class));
    }

    @PostConstruct
    @Override
    public final void build() {
        setModal(true);

        state.build();

        setResizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        getService(LanguageService.class).addInternationalizable(this);

        super.setContentPane(state.getContent());

        init();
    }

    /**
     * Init the view.
     */
    protected abstract void init();

    /**
     * Set the content pane of the dialog.
     *
     * @param contentPane The content pane to set.
     */
    public void setContentPane(JComponent contentPane) {
        state.getContent().setView(contentPane);
    }

    @Override
    public Container getContentPane() {
        return state.getContent().getView();
    }

    @Override
    public void setContentPane(Container contentPane) {
        throw new UnsupportedOperationException("Cannot set the content pane");
    }

    @Override
    public void setGlassPane(Component glassPane) {
        state.setGlassPane(glassPane);
        refresh();
    }

    @Override
    public Component getGlassPane() {
        return state.getContent().getGlassPane();
    }

    @Override
    public void closeDown() {
        setVisible(false);
    }

    @Override
    public void display() {
        state.display();
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
        state.setTitleKey(key, replaces);
    }

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        state.addInternationalizable(internationalizable);
    }

    @Override
    public void refreshText(LanguageService languageService) {
        state.refreshText(languageService);
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
        return state.validateContent();
    }

    @Override
    public void validate(Collection<org.jtheque.errors.Error> errors) {
        state.validate(errors); //Default validation using constraint
    }

    /**
     * Add the constraint to the view. The filed will be configured by the constraint.
     *
     * @param field      The field to validate.
     * @param constraint The constraint to add.
     */
    protected void addConstraint(Object field, Constraint constraint) {
        state.addConstraint(field, constraint);
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        state.setBundleContext(bundleContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        state.setApplicationContext(applicationContext);
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
     * Return the service of the given class.
     *
     * @param classz The class to get the service.
     * @param <T>    The type of service.
     *
     * @return The service of the given class if it's exists otherwise null.
     */
    protected <T> T getService(Class<T> classz) {
        return OSGiUtils.getService(state.getBundleContext(), classz);
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
        return state.getApplicationContext().getBean(classz);
    }

    /**
     * Set the controller of the view.
     *
     * @param controller The controller of the view.
     */
    public void setController(Controller<?> controller) {
        state.setController(controller);
    }

    @Override
    public Action getAction(String key) {
        return ActionFactory.createAction(key, state.getController());
    }

    @Override
    public WindowState getWindowState() {
        return state;
    }
}