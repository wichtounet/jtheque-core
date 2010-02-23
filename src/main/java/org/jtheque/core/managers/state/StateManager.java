package org.jtheque.core.managers.state;

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

import org.jdom.Element;
import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.file.XMLException;
import org.jtheque.core.utils.file.XMLReader;
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.core.utils.file.nodes.NodeLoader;
import org.jtheque.core.utils.file.nodes.NodeSaver;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A state manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class StateManager extends AbstractManager implements IStateManager {
    private final Map<Class<? extends IState>, IState> states = new HashMap<Class<? extends IState>, IState>(10);

    @Override
    public void preInit() {
        try {
            loadStates();
        } catch (ManagerException e1) {
            Managers.getManager(IErrorManager.class).addError(new JThequeError(e1));
        }
    }

    /**
     * Load the states.
     *
     * @throws ManagerException If an error occurs during the reading of the states.
     */
    @Override
    public void loadStates() throws ManagerException {
        XMLReader reader = new XMLReader();

        try {
            reader.openFile(getConfigFile());

            for (Object currentNode : reader.getNodes("state", reader.getRootElement())) {

                Class<?> stateClass = Class.forName(reader.readString("@class", currentNode), true, getClass().getClassLoader());

                if (IState.class.isAssignableFrom(stateClass)) {
                    IState state = (IState) stateClass.newInstance();

                    loadState(reader, currentNode, state);

                    states.put(state.getClass(), state);
                }
            }
        } catch (Exception e) {
            throw new ConfigException(e);
        } finally {
            FileUtils.close(reader);
        }
    }

    /**
     * Load the state from the XML file.
     *
     * @param reader      The XML reader.
     * @param currentNode The node to read from.
     * @param state       The state to fill.
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private static void loadState(XMLReader reader, Object currentNode, IState state) throws XMLException {
        if (state.isDelegated()) {
            Collection<Element> nodes = reader.getNodes("*", currentNode);

            state.delegateLoad(NodeLoader.resolveNodeStates(nodes));
        } else {
            for (Object propertyNode : reader.getNodes("properties/property", currentNode)) {
                state.setProperty(reader.readString("@key", propertyNode), reader.readString("@value", propertyNode));
            }
        }
    }

    /**
     * Init the config file.
     *
     * @param file The config file.
     */
    private static void initConfigFile(File file) {
        XMLWriter writer = new XMLWriter("states");

        writer.write(file.getAbsolutePath());
    }

    @Override
    public void close(){
        saveStates();
    }

    /**
     * Save the states.
     */
    private void saveStates() {
        XMLWriter writer = new XMLWriter("states");

        for (IState state : states.values()) {
            writer.add("state");
            writer.addAttribute("class", state.getClass().getCanonicalName());

            if (state.isDelegated()) {
                NodeSaver.writeNodes(writer, state.delegateSave());
            } else {
                save(state, writer);
            }

            writer.switchToParent();
        }

        writer.write(getConfigFile().getAbsolutePath());
    }

    /**
     * Return the config file.
     *
     * @return The config file.
     */
    private static File getConfigFile() {
        File configFile = new File(Managers.getCore().getFolders().getApplicationFolder(), "/core/config.xml");

        if (!configFile.exists()) {
            FileUtils.createEmptyFile(configFile.getAbsolutePath());

            initConfigFile(configFile);
        }

        return configFile;
    }

    /**
     * Save the state to the writer.
     *
     * @param state  The state to save.
     * @param writer The writer.
     */
    private static void save(IState state, XMLWriter writer) {
        writer.add("properties");

        for (String key : state.getProperties()) {
            if (state.getProperty(key) != null) {
                writer.add("property");
                writer.addAttribute("key", key);
                writer.addAttribute("value", state.getProperty(key));

                writer.switchToParent();
            }
        }

        writer.switchToParent();
    }

    @Override
    public <T extends IState> T getState(Class<T> c) {
        T state = null;

        if (states.containsKey(c)) {
            state = (T) states.get(c);
        }

        return state;
    }

    @Override
    public <T extends IState> T getOrCreateState(Class<T> c) throws StateException {
        return states.containsKey(c) ? (T) states.get(c) : createState(c);
    }

    @Override
    public <T extends IState> T createState(Class<T> c) throws StateException {
        T state;
        try {
            Object o = c.newInstance();

            state = (T) o;
        } catch (InstantiationException e) {
            throw new StateException(e);
        } catch (IllegalAccessException e) {
            throw new StateException(e);
        }

        states.put(c, state);

        return state;
    }

    @Override
    public void registerState(IState state) {
        states.put(state.getClass(), state);
    }
}