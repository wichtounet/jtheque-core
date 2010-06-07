package org.jtheque.resources.impl;

import org.jtheque.modules.impl.ModuleInfo;
import org.jtheque.states.able.Load;
import org.jtheque.states.able.Save;
import org.jtheque.states.able.State;
import org.jtheque.xml.utils.Node;

import java.util.ArrayList;
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

@State(id = "jtheque-resources", delegated = true)
public class ResourceState {
    private final List<ResourceInfo> resources;

    public ResourceState() {
        super();

        resources = new ArrayList<ResourceInfo>(10);
    }

    @Load
    public void delegateLoad(Iterable<Node> nodes) {
        for (Node node : nodes) {
            if ("resource".equals(node.getName())) {
                resources.add(convertToModuleInfo(node));
            }
        }
    }

    private ResourceInfo convertToModuleInfo(Node node) {
        return null;
    }

    @Save
    public Collection<Node> delegateSave() {
        Collection<Node> states = new ArrayList<Node>(25);

        for (ResourceInfo info : resources) {
            states.add(convertToNodeState(info));
        }

        return states;
    }

    private Node convertToNodeState(ResourceInfo info) {
        return null;
    }
}