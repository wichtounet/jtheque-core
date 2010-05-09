package org.jtheque.views.impl.components.panel;

import org.jtheque.ui.able.IModel;
import org.jtheque.ui.able.IView;

import javax.swing.JPanel;

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
 * An abstract view made of a panel.
 *
 * @param <T> The type of model.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractPanelView<T extends IModel> extends JPanel implements IView {
    private T model;

    @Override
    public final void display(){
        //Nothing by default
    }

    @Override
    public final void closeDown(){
        //Nothing by default
    }

    @Override
    public final void toFirstPlan(){
        //Nothing by default
    }

    @Override
    public final void sendMessage(String message, Object value){
        //Nothing by default
    }

    @Override
    public final void refresh(){
        //Nothing by default
    }

    @Override
    public final Object getImpl(){
        return this;
    }

    @Override
    public final T getModel(){
        return model;
    }

    @Override
    public boolean validateContent(){
        return true;
    }

    /**
     * Set the model.
     *
     * @param model The model of the view.
     */
    protected void setModel(T model){
        this.model = model;
    }
}