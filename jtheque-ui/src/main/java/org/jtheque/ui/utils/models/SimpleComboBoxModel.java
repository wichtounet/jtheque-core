package org.jtheque.ui.utils.models;

import javax.swing.ComboBoxModel;
import java.util.Collection;

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