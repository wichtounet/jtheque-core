package org.jtheque.core.managers.view.impl.frame.abstraction;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.impl.actions.ActionFactory;
import org.jtheque.core.managers.view.impl.components.ExtendedGlassPane;
import org.jtheque.core.managers.view.impl.components.InfiniteWaitFigure;
import org.jtheque.core.managers.view.impl.components.WaitFigure;
import org.jtheque.utils.collections.ArrayUtils;

import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingDialogView extends JDialog implements IWindowView, Internationalizable {
    private String titleKey;
    private Object[] titleReplaces;

    private IModel model;

    private boolean glassPaneInstalled;
    private boolean waitFigureInstalled;

    /**
     * Construct a SwingDialogView modal to the main view.
     */
    protected SwingDialogView(){
        this((Frame) Managers.getManager(IViewManager.class).getViews().getMainView().getImpl());
    }

    /**
     * Construct a SwingDialogView.
     *
     * @param frame The parent frame.
     */
    protected SwingDialogView(Frame frame) {
        super(frame);

        setModal(true);
        setResizable(true);

        Managers.getManager(ILanguageManager.class).addInternationalizable(this);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setIconImage(getDefaultWindowIcon());
    }

    /**
     * Install the default glass pane. If you need to install another glass pane, simply override
     * this method.
     */
    void installGlassPane() {
        Component glassPane = new ExtendedGlassPane(this);
        setGlassPane(glassPane);
        glassPane.setVisible(false);
    }

    /**
     * Install the default wait figure. If you need to use another wait figure, simply override
     * this method.
     */
    void installWaitFigure() {
        setWaitFigure(new InfiniteWaitFigure());
    }

    /**
     * Return The extendedGlassPane of this frame.
     *
     * @return The glass pane
     */
    final ExtendedGlassPane getExtendedGlassPane() {
        return (ExtendedGlassPane) getGlassPane();
    }

    @Override
    public Component getGlassPane() {
        installGlassPaneIfNecessary();

        return super.getGlassPane();
    }

    /**
     * Start the wait. The glass pane will start the wait animation.
     */
    public final void startWait() {
        installWaitFigureIfNecessary();

        getExtendedGlassPane().startWait();
    }

    /**
     * Stop the wait. The glass pane will stop the wait animation.
     */
    public final void stopWait() {
        installWaitFigureIfNecessary();

        getExtendedGlassPane().stopWait();
    }

    /**
     * Return the default window icon.
     *
     * @return The default window icon.
     */
    protected static Image getDefaultWindowIcon() {
        return Managers.getManager(IResourceManager.class).getImage(
                "file:" + Managers.getCore().getApplication().getWindowIcon(),
                Managers.getCore().getApplication().getWindowIconType());
    }

    /**
     * Set the wait figure of the dialog view. Must be called in the installWaitFigure() method.
     *
     * @param waitFigure The wait figure.
     */
    protected final void setWaitFigure(WaitFigure waitFigure) {
        getExtendedGlassPane().setWaitFigure(waitFigure);
    }

    /**
     * Return the view manager.
     *
     * @return The view manager.
     */
    public static IViewManager getManager(){
        return Managers.getManager(IViewManager.class);
    }

    /**
     * Return the bean with a specific name.
     *
     * @param name The name of the bean.
     * @return The bean.
     */
    public static <T> T getBean(String name){
        return Managers.getManager(IBeansManager.class).<T>getBean(name);
    }

    /**
     * Return an action to close this view.
     *
     * @param key The i18n key.
     *
     * @return An action to close this view.
     */
    public Action getCloseAction(String key){
        return ActionFactory.createCloseViewAction(key, this);
    }

    @Override
    public void closeDown() {
        setVisible(false);
    }

    @Override
    public void display() {
        setVisible(true);
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
        Managers.getManager(IViewManager.class).getDelegate().refresh(this);
    }

    /**
     * Set the title key.
     *
     * @param key The internationalization key.
     * @param replaces The replacements objects for the i18n methods. 
     */
    protected final void setTitleKey(String key, Object... replaces) {
        titleKey = key;

        if(!ArrayUtils.isEmpty(replaces)){
            titleReplaces = ArrayUtils.copyOf(replaces);
        }

        setTitle(getMessage(key, replaces));
    }

    @Override
    public void refreshText() {
        if (titleKey != null) {
            setTitleKey(titleKey, titleReplaces);
        }
    }

    /**
     * Set the model of the view.
     *
     * @param model The model of the view.
     */
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public IModel getModel() {
        return model;
    }

    @Override
    public final JDialog getImpl() {
        return this;
    }

    @Override
    public final boolean validateContent() {
        Collection<JThequeError> errors = new ArrayList<JThequeError>(5);

        validate(errors);

        for (JThequeError error : errors) {
            Managers.getManager(IErrorManager.class).addError(error);
        }

        return errors.isEmpty();
    }

    /**
     * Return the internationalized message.
     *
     * @param key The internationalization key.
     * @return The internationalized message.
     */
    protected static String getMessage(String key) {
        return Managers.getManager(ILanguageManager.class).getMessage(key);
    }

    /**
     * Return the internationalized message.
     *
     * @param key      The internationalization key.
     * @param replaces The replacement objects to use.
     * @return the internationalized message.
     */
    protected static String getMessage(String key, Object... replaces) {
        return Managers.getManager(ILanguageManager.class).getMessage(key, replaces);
    }

    /**
     * Validate the view and save all the validation's errors in the list.
     *
     * @param errors The error's list.
     */
    protected abstract void validate(Collection<JThequeError> errors);

    /**
     * Install the glass pane of the view if necessary.
     */
    private void installGlassPaneIfNecessary() {
        if (!glassPaneInstalled) {
            installGlassPane();
            glassPaneInstalled = true;
        }
    }

    /**
     * Install the wait figure if necessary. 
     */
    private void installWaitFigureIfNecessary() {
        installGlassPaneIfNecessary();

        if (!waitFigureInstalled) {
            installWaitFigure();
            waitFigureInstalled = true;
        }
    }
}