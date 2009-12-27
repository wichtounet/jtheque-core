package org.jtheque.core.managers.view.impl.components.model;

import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
    public Object getElementAt(int index){
        return objects.get(index);
    }

    @Override
    public int getSize(){
        return objects.size();
    }

    @Override
    public void addElement(Object obj){
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
    public void removeElement(Object obj){
        T category = (T) obj;

        int index = objects.indexOf(category);

        objects.remove(category);

        fireIntervalRemoved(this, index, index);
    }

    @Override
    public void removeAllElements(){
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

    @Override
    public T getSelectedItem(){
        return (T)super.getSelectedItem();
    }
}