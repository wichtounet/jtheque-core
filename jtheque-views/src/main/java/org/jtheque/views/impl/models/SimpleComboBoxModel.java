package org.jtheque.views.impl.models;

import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
public class SimpleComboBoxModel<T> extends DefaultComboBoxModel {
    private final List<T> objects;

    /**
     * Construct a new SimpleComboBoxModel.
     */
    public SimpleComboBoxModel(){
        this(10);
    }

    /**
     * Construct a new SimpleComboBoxModel.
     *
     * @param capacity The initial capacity of the model.
     */
    public SimpleComboBoxModel(int capacity){
        super();

        objects = new ArrayList<T>(capacity);
    }

    /**
     * Construct a new SimpleComboBoxModel.
     *
     * @param objects The objects to put in the model.
     */
    public SimpleComboBoxModel(Collection<T> objects){
        super();

        this.objects = CollectionUtils.copyOf(objects);
    }

    /**
     * Construct a new SimpleComboBoxModel.
     *
     * @param objects The objects to put in the model.
     */
    public SimpleComboBoxModel(T[] objects){
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
    public final void removeElement(Object obj){
        T category = (T) obj;

        int index = objects.indexOf(category);

        objects.remove(category);

        fireIntervalRemoved(this, index, index);
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
    public Iterable<T> getObjects(){
        return objects;
    }

    @Override
    public final T getSelectedItem(){
        return (T)super.getSelectedItem();
    }
}