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
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.impl.actions.ActionFactory;
import org.jtheque.core.utils.ui.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.PanelBuilder;

import javax.swing.Action;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingBuildedDialogView<T extends IModel> extends SwingDialogView {
    private boolean filthy;

    /**
     * Construct a SwingDialogView modal to the main view.
     */
    protected SwingBuildedDialogView(){
        this((Frame) Managers.getManager(IViewManager.class).getViews().getMainView().getImpl(), false);
    }

    /**
     * Construct a SwingDialogView modal to the main view.
     */
    protected SwingBuildedDialogView(boolean filthy){
        this((Frame) Managers.getManager(IViewManager.class).getViews().getMainView().getImpl(), filthy);
    }

    /**
     * Construct a SwingDialogView.
     *
     * @param frame The parent frame.
     */
    protected SwingBuildedDialogView(Frame frame) {
        this(frame, false);
    }

    protected SwingBuildedDialogView(Frame frame, boolean filthy) {
        super(frame);

        setModal(true);
        setResizable(true);

        Managers.getManager(ILanguageManager.class).addInternationalizable(this);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setIconImage(getDefaultWindowIcon());

        this.filthy = filthy;
    }

    protected void build(){
        initView();

        setContentPane(buildContentPane());
        
        setLocationRelativeTo(getOwner());
    }

    private Container buildContentPane(){
        PanelBuilder builder = filthy ? new FilthyPanelBuilder() : new PanelBuilder();

        buildView(builder);

        return builder.getPanel();
    }

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

    public Action getCloseAction(String key){
        return ActionFactory.createCloseViewAction(key, this);
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
        //Default empty implementation
    }

    @Override
    public T getModel(){
        return (T) super.getModel();
    }

    protected abstract void initView();

    protected abstract void buildView(PanelBuilder builder);
}