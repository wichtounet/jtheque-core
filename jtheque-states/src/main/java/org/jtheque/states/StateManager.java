package org.jtheque.states;

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
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.io.NodeLoader;
import org.jtheque.io.NodeSaver;
import org.jtheque.io.XMLException;
import org.jtheque.io.XMLReader;
import org.jtheque.io.XMLWriter;
import org.jtheque.logging.IJThequeLogger;
import org.jtheque.utils.io.FileUtils;

import javax.swing.JOptionPane;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A state manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class StateManager implements IStateManager {
    private final Map<Class<? extends IState>, IState> states = new HashMap<Class<? extends IState>, IState>(10);

    private IJThequeLogger logger;

    /**
     * Load the states.
     *
     * @throws StateException If an error occurs during the reading of the states.
     */
    public void loadStates() throws StateException{
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
            throw new StateException(e);
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

    public void close(){
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
        File configFile = new File(SystemProperty.USER_DIR.get(), "config.xml");

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
        return states.containsKey(c) ? (T) states.get(c) : null;
    }

    @Override
    public <T extends IState> T getOrCreateState(Class<T> c){
        return states.containsKey(c) ? (T) states.get(c) : createState(c);
    }

    @Override
    public <T extends IState> T createState(Class<T> c){
        try {
            T state = c.newInstance();

            state.setDefaults();

            states.put(c, state);

            return state;
        } catch (InstantiationException e) {
            logger.error(e);
            JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException e) {
            logger.error(e);
            JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    public void registerState(IState state) {
        states.put(state.getClass(), state);
    }

    public void setLogger(IJThequeLogger logger) {
        this.logger = logger;
    }
}