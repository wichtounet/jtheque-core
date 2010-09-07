package org.jtheque.states;

import org.jtheque.states.utils.AbstractState;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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

public class AbstractStateTest extends AbstractState {
    @Test
    public void setProperties(){
        Map<String, String> defaults = new HashMap<String, String>(2);
        defaults.put("key1", "value1");
        defaults.put("key2", "value2");

        setProperties(defaults);

        assertEquals(defaults, getProperties());
    }

    public void getProperty(){
        setProperty("superkey1", "supervalue1");
        setProperty("superkey2", "supervalue2");

        assertEquals("supervalue1", getProperty("superkey1"));
    }
}