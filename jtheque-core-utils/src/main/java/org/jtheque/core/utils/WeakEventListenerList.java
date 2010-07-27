package org.jtheque.core.utils;

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

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * A class that holds a list of EventListeners.  A single instance can be used to hold all listeners (of all types) for
 * the instance using the list.  It is the responsibility of the class using the EventListenerList to provide type-safe
 * API (preferably conforming to the JavaBeans spec) and methods which dispatch event notification methods to
 * appropriate Event Listeners on the list.
 * <p/>
 * The main benefit that this class provides is that it releases garbage collected listeners (internally uses weak
 * references). <p>
 * <p/>
 * Usage example: Say one is defining a class that sends out FooEvents, and one wants to allow users of the class to
 * register FooListeners and receive notification when FooEvents occur.  The following should be added to the class
 * definition:
 * <pre>
 * EventListenerList listenerList = new EventListenerList();
 * FooEvent fooEvent = null;
 * <p/>
 * public void addFooListener(FooListener l) {
 *     listenerList.add(FooListener.class, l);
 * }
 * <p/>
 * public void removeFooListener(FooListener l) {
 *     listenerList.remove(FooListener.class, l);
 * }
 * <p/>
 * <p/>
 * // Notify all listeners that have registered interest for
 * // notification on this event type.  The event instance
 * // is lazily created using the parameters passed into
 * // the fire method.
 * <p/>
 * <p/>
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
 * foo should be changed to the appropriate name, and fireFooXxx to the appropriate method name.  One fire method should
 * exist for each notification method in the FooListener interface.
 * <p/>
 *
 * @author Georges Saab
 * @author Hans Muller
 * @author James Gosling
 * @version 1.37 11/17/05
 */
public final class WeakEventListenerList {
    private List<WeakReference<? extends EventListener>> weakReferences;
    private List<Class<? extends EventListener>> classes;

    /**
     * Returns a list of strongly referenced EventListeners. Removes internal weak references to garbage collected
     * listeners.
     *
     * @return All the strongly references listeners.
     */
    private <T extends EventListener> List<T> cleanReferences() {
        List<T> listeners = new ArrayList<T>(10);

        for (int i = getReferences().size() - 1; i >= 0; i--) {
            @SuppressWarnings("unchecked")
            T listener = (T) getReferences().get(i).get();

            if (listener == null) {
                getReferences().remove(i);
                getClasses().remove(i);
            } else {
                listeners.add(0, listener);
            }
        }

        return listeners;
    }

    /**
     * Return all the references.
     *
     * @return All the references.
     */
    private List<WeakReference<? extends EventListener>> getReferences() {
        if (weakReferences == null) {
            weakReferences = new ArrayList<WeakReference<? extends EventListener>>(10);
        }

        return weakReferences;
    }

    /**
     * Return all the classes.
     *
     * @return All the classes.
     */
    private List<Class<? extends EventListener>> getClasses() {
        if (classes == null) {
            classes = new ArrayList<Class<? extends EventListener>>(10);
        }

        return classes;
    }

    /**
     * Return an array of all the listeners of the given type. As a side-effect, cleans out any garbage collected
     * listeners before building the array.
     *
     * @param t the class to get the listeners from.
     *
     * @return all of the listeners of the specified type.
     *
     * @throws ClassCastException if the supplied class is not assignable to EventListener
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> t) {
        List<T> liveListeners = cleanReferences();
        List<T> listeners = new ArrayList<T>(5);

        for (int i = 0; i < liveListeners.size(); i++) {
            if (getClasses().get(i) == t) {
                listeners.add(liveListeners.get(i));
            }
        }

        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(t, listeners.size());
        
        return listeners.toArray(result);
    }

    /**
     * Adds the listener as a listener of the specified type. As a side-effect, cleans out any garbage collected
     * listeners before adding.
     *
     * @param t the type of the listener to be added
     * @param l the listener to be added
     */
    public <T extends EventListener> void add(Class<T> t, T l) {
        assert l != null;

        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l + " is not of type " + t);
        }

        cleanReferences();
        getReferences().add(new WeakReference<T>(l));
        getClasses().add(t);
    }

    /**
     * Removes the listener as a listener of the specified type.
     *
     * @param t the type of the listener to be removed
     * @param l the listener to be removed
     */
    public <T extends EventListener> void remove(Class<T> t, T l) {
        assert l != null;

        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l + " is not of type " + t);
        }

        for (int i = 0; i < getReferences().size(); i++) {
            if (l.equals(getReferences().get(i).get()) && t == getClasses().get(i)) {
                getReferences().remove(i);
                getClasses().remove(i);
                break;
            }
        }
    }

    /**
     * Remove all the listeners of type T.
     *
     * @param t   The class of listeners to remove.
     * @param <T> The type of listeners to remove.
     */
    public <T extends EventListener> void removeAll(Class<T> t) {
        for (int i = 0; i < getReferences().size(); i++) {
            if (t == getClasses().get(i)) {
                getReferences().remove(i);
                getClasses().remove(i);

                break;
            }
        }
    }
}