package org.jtheque.views.impl.panel;

import org.jtheque.ui.IModel;
import org.jtheque.ui.IView;

import javax.swing.JPanel;

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