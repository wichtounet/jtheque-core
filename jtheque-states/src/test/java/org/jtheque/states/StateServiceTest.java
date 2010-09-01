package org.jtheque.states;

import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.File;
import java.util.Map;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "jtheque-states-test.xml")
public class StateServiceTest {
    @Resource
    private StateService stateService;

    private static String userDir;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }
    
    @BeforeClass
    public static void before(){
        userDir = SystemProperty.USER_DIR.get();

        File folder = new File(SystemProperty.JAVA_IO_TMP_DIR.get(), "jtheque");
        folder.mkdirs();

        SystemProperty.USER_DIR.set(folder.getAbsolutePath());
    }

    @AfterClass
    public static void after() {
        FileUtils.delete(new File(SystemProperty.JAVA_IO_TMP_DIR.get(), "jtheque"));

        SystemProperty.USER_DIR.set(userDir);
    }

    @Test
    public void initOK() {
        assertNotNull(stateService);
    }

    @Test
    public void configCreated() {
        File folder = new File(SystemProperty.JAVA_IO_TMP_DIR.get(), "jtheque");

        assertTrue(new File(folder, "config.xml").exists());
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

    private static final class IncompleteState1 {}

    @State(id="jtheque-states-test-configuration")
    private static final class IncompleteState2 {}

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
}