package org.jtheque.resources.impl;

import org.jtheque.resources.Resource;
import org.jtheque.states.Load;
import org.jtheque.states.Save;
import org.jtheque.states.State;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.xml.utils.Node;

import java.util.Collection;
import java.util.Set;

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
 * The JTheque state to keep the installed resources in the application.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
@State(id = "jtheque-resources", delegated = true)
public final class ResourceState {
    private final Collection<Resource> resources = CollectionUtils.newConcurrentList();

    /**
     * Delegate the load of the state.
     *
     * @param nodes The nodes to load.
     */
    @Load
    public void delegateLoad(Iterable<Node> nodes) {
        for (Node node : nodes) {
            if ("set".equals(node.getName())) {
                resources.add(convertToResourceSet(node));
            }
        }
    }

    /**
     * Delegate the save of the state.
     *
     * @return The nodes of the state.
     */
    @Save
    public Collection<Node> delegateSave() {
        Collection<Node> states = CollectionUtils.newList();

        for (Resource resource : resources) {
            states.add(convertToNode(resource));
        }

        return states;
    }

    /**
     * Add a resource to the state.
     *
     * @param resource The resource to add to the state.
     */
    public void addResource(Resource resource) {
        resources.add(resource);
    }

    /**
     * Return all the resources of the state.
     *
     * @return All the resources of the state.
     */
    public Collection<Resource> getResourceSets() {
        return resources;
    }

    /**
     * Convert the given node to the Resource.
     *
     * @param node The node to convert.
     *
     * @return The corresponding Resource.
     */
    private static Resource convertToResourceSet(Node node) {
        Set<String> files = CollectionUtils.newSet();
        Set<String> libraries = CollectionUtils.newSet();
        
        for (Node child : node.getChildrens()) {
            if ("file".equals(child.getName())) {
                files.add(child.getText());
            } else if ("library".equals(child.getName())) {
                libraries.add(child.getText());
            }
        }

        return new ResourceImpl(
                node.getAttributeValue("id"),
                Version.get(node.getAttributeValue("version")),
                node.getAttributeValue("url"),
                node.getAttributeValue("file"),
                Boolean.valueOf(node.getAttributeValue("url")));
    }

    /**
     * Convert the resource set to a XML node.
     *
     * @param resourceSet The resource set to convert.
     *
     * @return The Node corresponding to the resource.
     */
    private static Node convertToNode(Resource resourceSet) {
        Node node = new Node("set");

        node.setAttribute("id", resourceSet.getId());
        node.setAttribute("url", resourceSet.getUrl());
        node.setAttribute("version", resourceSet.getVersion().getVersion());
        node.setAttribute("file", resourceSet.getFile());
        node.setAttribute("library", Boolean.toString(resourceSet.isLibrary()));

        return node;
    }
}