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

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IError;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.i18n.able.InternationalizableContainer;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.able.IWindowState;
import org.jtheque.ui.able.IWindowView;
import org.jtheque.ui.able.constraints.Constraint;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.utils.windows.ManagedWindow;
import org.jtheque.ui.utils.windows.WindowState;
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
import java.util.Collection;

/**
 * A swing frame view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingFrameView<T extends IModel> extends JFrame
        implements ManagedWindow, IWindowView, BundleContextAware, ApplicationContextAware, InternationalizableContainer {

    private T model;

    private final WindowState state = new WindowState(this);

    @PostConstruct
    @Override
    public final void build() {
        state.build();

        setResizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        getService(ILanguageService.class).addInternationalizable(this);

        super.setContentPane(state.getContent());

        init();

        SwingUtils.centerFrame(this);
    }

    /**
     * Set the content pane of the frame.
     *
     * @param contentPane The content pane of the frame.
     */
    public void setContentPane(JComponent contentPane) {
        state.getContent().setView(contentPane);
    }

    @Override
    public void setContentPane(Container contentPane) {
        throw new UnsupportedOperationException("Cannot set the content pane");
    }

    @Override
    public Container getContentPane() {
        return state.getContent().getView();
    }

    @Override
    public void setGlassPane(Component glassPane) {
        state.setGlassPane(glassPane);
        refresh();
    }

    /**
     * Init the view.
     */
    protected abstract void init();

    @Override
    public Component getGlassPane() {
        return state.getContent().getGlassPane();
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
        state.setBundleContext(bundleContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        state.setApplicationContext(applicationContext);
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
        return state.getContent();
    }

    @Override
    public boolean validateContent() {
        return true;
    }

    @Override
    public void display() {
        state.display();
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
    public void refreshText(ILanguageService languageService) {
        state.refreshText(languageService);
    }

    /**
     * Validate the view and save all the validation's errors in the list.
     *
     * @param errors The error's list.
     */
    @Override
    public void validate(Collection<IError> errors) {
        state.validate(errors); //Default validation using constraints
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
     * Set the controller of the frame.
     *
     * @param controller The controller of the view.
     */
    public void setController(IController controller) {
        state.setController(controller);
    }

    @Override
    public Action getControllerAction(String key) {
        return ActionFactory.createControllerAction(key, state.getController());
    }

    @Override
    public IWindowState getWindowState() {
        return state;
    }
}