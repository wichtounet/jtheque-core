package org.jtheque.core.utils;

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

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * A class that holds a list of EventListeners.  A single instance
 * can be used to hold all listeners (of all types) for the instance
 * using the list.  It is the responsibility of the class using the
 * EventListenerList to provide type-safe API (preferably conforming
 * to the JavaBeans spec) and methods which dispatch event notification
 * methods to appropriate Event Listeners on the list.
 *
 * The main benefit that this class provides is that it releases
 * garbage collected listeners (internally uses weak references). <p>
 * 
 * Usage example:
 *    Say one is defining a class that sends out FooEvents, and one wants
 * to allow users of the class to register FooListeners and receive
 * notification when FooEvents occur.  The following should be added
 * to the class definition:
 * <pre>
 * EventListenerList listenerList = new EventListenerList();
 * FooEvent fooEvent = null;
 *
 * public void addFooListener(FooListener l) {
 *     listenerList.add(FooListener.class, l);
 * }
 *
 * public void removeFooListener(FooListener l) {
 *     listenerList.remove(FooListener.class, l);
 * }
 *
 *
 * // Notify all listeners that have registered interest for
 * // notification on this event type.  The event instance
 * // is lazily created using the parameters passed into
 * // the fire method.
 *
 *
 * protected void fireFooXXX() {
 *     // Guaranteed to return a non-null array
 *     FooListener[] listeners = listenerList.getListeners(FooListener.class);
 *     // Process the listeners last to first, notifying
 *     // those that are interested in this event
 *     for (FooListener listener: listeners) {
 *             // Lazily create the event:
 *             if (fooEvent == null)
 *                 fooEvent = new FooEvent(this);
 *             listener.fooXXX(fooEvent);
 *         }
 *     }
 * }
 * </pre>
 * foo should be changed to the appropriate name, and fireFooXxx to the
 * appropriate method name.  One fire method should exist for each
 * notification method in the FooListener interface.
 * <p>
 *
 * @version 1.37 11/17/05
 * @author Georges Saab
 * @author Hans Muller
 * @author James Gosling
 */
public class WeakEventListenerList implements Serializable {
    private transient List<WeakReference<? extends EventListener>> weakReferences;
    private transient List<Class<? extends EventListener>> classes;

    /**
     * Returns a list of strongly referenced EventListeners. Removes
     * internal weak references to garbage collected listeners.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private synchronized <T extends EventListener> List<T> cleanReferences() {
        List<T> listeners = new ArrayList<T>(10);
        for (int i = getReferences().size() - 1; i >= 0; i--) {

            Object listener = getReferences().get(i).get();
            if (listener == null) {
                getReferences().remove(i);
                getClasses().remove(i);
            } else {
                listeners.add(0, (T) listener);
            }
        }
        return listeners;
    }

    private List<WeakReference<? extends EventListener>> getReferences() {
        if (weakReferences == null) {
            weakReferences = new ArrayList<WeakReference<? extends EventListener>>(10);
        }
        return weakReferences;
    }

    private List<Class<? extends EventListener>> getClasses() {
        if (classes == null) {
            classes = new ArrayList<Class<? extends EventListener>>(10);

        }
        return classes;
    }
    /**
     * Return an array of all the listeners of the given type.
     * As a side-effect, cleans out any
     * garbage collected listeners before building the array.
     * @return all of the listeners of the specified type.
     * @exception  ClassCastException if the supplied class
     *          is not assignable to EventListener
     *
     * @since 1.3
     */
    @SuppressWarnings("unchecked")
    public <T extends EventListener> T[] getListeners(Class<T> t) {
        List<T> liveListeners = cleanReferences();
        List<T> listeners = new ArrayList<T>();
        for (int i = 0; i < liveListeners.size(); i++) {
            if (getClasses().get(i) == t) {
                listeners.add(liveListeners.get(i));
            }
        }
        T[] result = (T[])Array.newInstance(t, listeners.size());
        return listeners.toArray(result);
    }

    /**
     * Adds the listener as a listener of the specified type.
      * As a side-effect, cleans out any garbage collected
     * listeners before adding.
    * @param t the type of the listener to be added
     * @param l the listener to be added
     */
    public synchronized <T extends EventListener> void add(Class<T> t, T l) {
        if (l==null) {
            // In an ideal world, we would do an assertion here
            // to help developers know they are probably doing
            // something wrong
            return;
        }

        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l +
                                         " is not of type " + t);
        }

        cleanReferences();
        getReferences().add(new WeakReference<T>(l));
        getClasses().add(t);
    }

    /**
     * Removes the listener as a listener of the specified type.
     * @param t the type of the listener to be removed
     * @param l the listener to be removed
     */
    public synchronized <T extends EventListener> void remove(Class<T> t, T l) {
        if (l ==null) {
            // In an ideal world, we would do an assertion here
            // to help developers know they are probably doing
            // something wrong
            return;
        }

        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l +
                                         " is not of type " + t);
        }

        for (int i = 0; i < getReferences().size(); i++) {
           if (l.equals(getReferences().get(i).get()) &&
                   t == getClasses().get(i)) {
               getReferences().remove(i);
               getClasses().remove(i);
               break;
           }
        }
    }
}
