package org.jtheque.resources.impl;

import org.jtheque.resources.able.IResource;
import org.jtheque.states.able.Load;
import org.jtheque.states.able.Save;
import org.jtheque.states.able.State;
import org.jtheque.utils.bean.Version;
import org.jtheque.xml.utils.Node;

import java.util.ArrayList;
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
 * The JTheque state to keep the installed resources in the application.
 *
 * @author Baptiste Wicht
 */
@State(id = "jtheque-resources", delegated = true)
public class ResourceState {
    private final Collection<IResource> resources = new ArrayList<IResource>(10);

    /**
     * Delegate the load of the state.
     *
     * @param nodes The nodes to load.
     */
    @Load
    public void delegateLoad(Iterable<Node> nodes) {
        for (Node node : nodes) {
            if ("resource".equals(node.getName())) {
                resources.add(convertToResource(node));
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
        Collection<Node> states = new ArrayList<Node>(25);

        for (IResource info : resources) {
            states.add(convertToNode(info));
        }

        return states;
    }

    /**
     * Add a resource to the state.
     *
     * @param resource The resource to add to the state.
     */
    public void addResource(IResource resource) {
        resources.add(resource);
    }

    /**
     * Return all the resources of the state.
     *
     * @return All the resources of the state.
     */
    public Collection<IResource> getResources() {
        return resources;
    }

    /**
     * Convert the given node to the IResource.
     *
     * @param node The node to convert.
     *
     * @return The corresponding IResource.
     */
    private static IResource convertToResource(Node node) {
        String id = node.getAttributeValue("id");
        String url = node.getAttributeValue("url");
        Version version = new Version(node.getAttributeValue("version"));

        Resource resource = new Resource(id);
        resource.setVersion(version);
        resource.setUrl(url);

        for (Node child : node.getChildrens()) {
            if ("file".equals(child.getName())) {
                resource.addFile(child.getText());
            } else if ("library".equals(child.getName())) {
                resource.addLibrary(new Library(child.getText()));
            }
        }

        return resource;
    }

    /**
     * Convert the resource to a XML node.
     *
     * @param resource The resource to convert.
     *
     * @return The Node corresponding to the resource.
     */
    private static Node convertToNode(IResource resource) {
        Node node = new Node("resource");

        node.setAttribute("id", resource.getId());
        node.setAttribute("url", resource.getUrl());
        node.setAttribute("version", resource.getVersion().getVersion());

        for (String file : resource.getFiles()) {
            node.addSimpleChildValue("file", file);
        }

        for (Library library : resource.getLibraries()) {
            node.addSimpleChildValue("library", library.getId());
        }

        return node;
    }
}