package org.jtheque.ui.utils.models;

import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
 * A simple list model to store dynamic objects.
 *
 * @param <T> The type of objects stored in the model.
 *
 * @author Baptiste Wicht
 */
public class SimpleListModel<T> extends AbstractListModel implements ComboBoxModel {
    private final List<T> objects;

    private T selectedObject;

    /**
     * Construct a new SimpleListModel.
     */
    public SimpleListModel(){
        this(10);
    }

    /**
     * Construct a new SimpleListModel.
     *
     * @param capacity The initial capacity of the model.
     */
    public SimpleListModel(int capacity){
        super();

        objects = new ArrayList<T>(capacity);
    }

    /**
     * Construct a new SimpleListModel. 
     *
     * @param objects The objects to put in the model.
     */
    public SimpleListModel(Collection<T> objects){
        super();

        this.objects = CollectionUtils.copyOf(objects);
    }

    /**
     * Construct a new SimpleListModel.
     *
     * @param objects The objects to put in the model.
     */
    public SimpleListModel(T[] objects){
        super();

        this.objects = Arrays.asList(objects);
    }

    @Override
    public final Object getElementAt(int index){
        return objects.get(index);
    }

    @Override
    public final int getSize(){
        return objects.size();
    }

    /**
     * Add the element to the model.
     *
     * @param element The element to add.
     */
    public void addElement(T element){
        int index = objects.size();

        objects.add(element);

        fireIntervalAdded(this, index, getSize());
    }

    /**
     * Add the element to the model.
     *
     * @param element The element to add.
     */
    public void removeElement(T element){
        int index = objects.size();

        objects.remove(element);

        fireIntervalRemoved(this, index, getSize());
    }

    public T removeElement(int index) {
        T removed = objects.remove(index);

        fireIntervalRemoved(this, index, index);

        return removed;
    }

    /**
     * Add the objects to the model.
     *
     * @param elements The objects to add.
     */
    public void addElements(Iterable<T> elements){
        int index = objects.size();

        for (T category : elements){
            objects.add(category);
        }

        fireIntervalAdded(this, index, getSize());
    }

    /**
     * Add the objects to the model.
     *
     * @param elements The objects to add.
     */
    public void addElements(T...  elements){
        int index = objects.size();

        objects.addAll(Arrays.asList(elements));

        fireIntervalAdded(this, index, getSize());
    }

    /**
     * Set the elements contained on the model.
     *
     * @param elements The elements to set on the model.
     */
    public void setElements(Collection<T> elements){
        objects.clear();
        objects.addAll(elements);
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Set the elements contained on the model.
     *
     * @param elements The elements to set on the model.
     */
    public void setElements(T... elements){
        objects.clear();
        objects.addAll(Arrays.asList(elements));
        fireContentsChanged(this, 0, getSize());
    }

    public int getIndexOfElement(T element){
        return objects.indexOf(element);
    }

    /**
     * Return the objects.
     *
     * @return A List containing all the objects of the model.
     */
    public Collection<T> getObjects(){
        return objects;
    }

    @Override
    public void setSelectedItem(Object anObject) {
        if (selectedObject != null && !selectedObject.equals( anObject ) || selectedObject == null && anObject != null) {
            selectedObject = (T) anObject;
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public final T getSelectedItem(){
        return selectedObject;
    }

	/**
	 * Clear the model. 
	 */
    public void clear() {
        objects.clear();
        fireContentsChanged(this, 0, 0);
    }
}