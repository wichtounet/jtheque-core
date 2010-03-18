package org.jtheque.views.impl.components.panel;

import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.able.IView;
import org.jtheque.ui.utils.edt.SimpleTask;
import org.jtheque.views.ViewsServices;

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

/**
 * An abstract delegated view. It seems a view who delegates all the view's operations to an other view.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractDelegatedView<T extends IView> implements IView {
    private T delegate;

    /**
     * Return the implementation of the view.
     *
     * @return The implementation of the view.
     */
    public final T getImplementationView(){
        return delegate;
    }

    /**
     * Set the delegate view.
     *
     * @param view The delegate view. 
     */
    public final void setDelegate(T view){
        delegate = view;
    }

    /**
     * Build the delegated view in EDT. This method only call the buildDelegatedView() method in the EDT.
     */
    public final void buildInEDT() {
        ViewsServices.get(IUIUtils.class).execute(new SimpleTask() {
            @Override
            public void run() {
                buildDelegatedView();
            }
        });
    }

    /**
     * Build the delegated view. This method must only be called from the buildInEDT() method. With that contract respected,
     * this method is always called in the EDT.
     */
    protected abstract void buildDelegatedView();

    @Override
    public final void display() {
        delegate.display();
    }

    @Override
    public final Object getImpl() {
        return delegate.getImpl();
    }

    @Override
    public final void closeDown() {
        delegate.closeDown();
    }

    @Override
    public final void toFirstPlan() {
        delegate.toFirstPlan();
    }

    @Override
    public final void setEnabled(boolean enabled) {
        delegate.setEnabled(enabled);
    }

    @Override
    public final boolean isEnabled() {
        return delegate.isEnabled();
    }

    @Override
    public final void sendMessage(String message, Object value) {
        delegate.sendMessage(message, value);
    }

    @Override
    public final void refresh() {
        delegate.refresh();
    }

    @Override
    public final boolean validateContent() {
        return delegate.validateContent();
    }
}