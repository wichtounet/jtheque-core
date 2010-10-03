package org.jtheque.states;

import org.jtheque.states.utils.AbstractConcurrentState;
import org.jtheque.states.utils.AbstractState;
import org.jtheque.unit.AbstractJThequeTest;
import org.jtheque.utils.collections.CollectionUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "jtheque-states-test.xml")
public class StateServiceTest extends AbstractJThequeTest {
    @Resource
    private StateService stateService;

    @Test
    public void initOK() {
        assertNotNull(stateService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void stateWithoutAnnotation() {
        stateService.getState(new IncompleteState1());
    }

    @Test(expected = IllegalArgumentException.class)
    public void stateWithoutMethods() {
        stateService.getState(new IncompleteState2());
    }

    @Test(expected = IllegalArgumentException.class)
    public void stateWithoutSaveMethod() {
        stateService.getState(new IncompleteState3());
    }

    @Test(expected = IllegalArgumentException.class)
    public void stateWithoutLoadMethod() {
        stateService.getState(new IncompleteState4());
    }

    @Test
    public void defaultStatesValid() {
        stateService.getState(new ValidEmptyState());
        stateService.getState(new ValidEmptyConcurrentState());
    }

    private static final class IncompleteState1 {
    }

    @State(id = "jtheque-states-test-configuration")
    private static final class IncompleteState2 {
    }

    @State(id = "jtheque-states-test-configuration")
    private static final class IncompleteState3 {
        @Load
        public void setProperties(Map<String, String> properties) {
            //Normal empty implementation
        }
    }

    @State(id = "jtheque-states-test-configuration")
    private static final class IncompleteState4 {
        @Save
        public Map<String, String> getProperties() {
            return CollectionUtils.newHashMap();
        }
    }

    @State(id = "jtheque-states-test-configuration-1")
    private static final class ValidEmptyState extends AbstractState {

    }

    @State(id = "jtheque-states-test-configuration-2")
    private static final class ValidEmptyConcurrentState extends AbstractConcurrentState {

    }
}