package org.jtheque.views.impl.models;

import javax.swing.ComboBoxModel;
import java.util.Collection;

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
 * A simple combo box model to store dynamic objects.
 *
 * @param <T> The type of objects.
 * 
 * @author Baptiste Wicht
 */
public class SimpleComboBoxModel<T> extends SimpleListModel<T> implements ComboBoxModel {
    private T selectedItem;

    /**
     * Construct a new SimpleComboBoxModel.
     */
    public SimpleComboBoxModel(){
        super();
    }

    /**
     * Construct a new SimpleComboBoxModel.
     *
     * @param capacity The initial capacity of the model.
     */
    public SimpleComboBoxModel(int capacity){
        super(capacity);
    }

    /**
     * Construct a new SimpleComboBoxModel.
     *
     * @param objects The objects to put in the model.
     */
    public SimpleComboBoxModel(Collection<T> objects){
        super(objects);
    }

    /**
     * Construct a new SimpleComboBoxModel.
     *
     * @param objects The objects to put in the model.
     */
    public SimpleComboBoxModel(T[] objects){
        super(objects);
    }

    @Override
    public void setSelectedItem(Object anObject) {
        if (selectedItem != null && !selectedItem.equals( anObject ) || selectedItem == null && anObject != null) {
            selectedItem = (T) anObject;
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public final T getSelectedItem(){
        return selectedItem;
    }
}