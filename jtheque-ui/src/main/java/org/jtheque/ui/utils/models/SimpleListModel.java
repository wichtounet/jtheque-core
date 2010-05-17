package org.jtheque.ui.utils.models;

import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultListModel;
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
 * @param <T> The type of objects.
 *
 * @author Baptiste Wicht
 */
public class SimpleListModel<T> extends DefaultListModel {
    private final List<T> objects;

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
    public final Object get(int index){
        return objects.get(index);
    }

    @Override
    public final int getSize(){
        return objects.size();
    }

    @Override
    public final Object remove(int index){
        T category = objects.remove(index);
        fireIntervalRemoved(this, index, index);
        return category;
    }

    @Override
    public final void addElement(Object obj){
        objects.add((T) obj);
        fireIntervalAdded(this, getSize(), getSize());
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

    @Override
    public final void clear(){
        objects.clear();
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public final boolean removeElement(Object obj){
        T category = (T) obj;

        int index = objects.indexOf(category);
        boolean remove = objects.remove(category);
        fireIntervalRemoved(this, index, index);
        return remove;
    }

    @Override
    public final void removeAllElements(){
        objects.clear();
        fireContentsChanged(this, 0, getSize());
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
    public void setElements(T[] elements){
        objects.clear();
        objects.addAll(Arrays.asList(elements));
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Return the objects.
     *
     * @return A List containing all the objects of the model.
     */
    public Collection<T> getObjects(){
        return objects;
    }
}