package org.jtheque.views.impl.components.panel;

import org.jtheque.ui.able.IView;

import javax.swing.SwingUtilities;

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
        SwingUtilities.invokeLater(new Runnable(){
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