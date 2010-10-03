package org.jtheque.core;

import org.jtheque.core.CoreTest.TestApplication;
import org.jtheque.core.lifecycle.FunctionListener;
import org.jtheque.core.lifecycle.LifeCycle;
import org.jtheque.core.lifecycle.TitleListener;
import org.jtheque.unit.AbstractJThequeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.concurrent.atomic.AtomicInteger;

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
@ContextConfiguration(locations = "jtheque-core-test.xml")
public class LifeCycleTest extends AbstractJThequeTest {
    @Resource
    private LifeCycle lifeCycle;

    @Resource
    private Core core;

    @Before
    public void launchApplication() {
        core.launchApplication(new TestApplication());

        lifeCycle.refreshTitle();
    }

    @Test
    @DirtiesContext
    public void initOK() {
        assertNotNull(lifeCycle);
    }

    @Test
    @DirtiesContext
    public void titleValidity(){
        assertTrue(lifeCycle.getTitle().contains("test-application-name"));
        assertTrue(lifeCycle.getTitle().contains("1.0.1"));
    }

    @Test
    @DirtiesContext
    public void function() {
        lifeCycle.setCurrentFunction("xyzzy");

        assertEquals("xyzzy", lifeCycle.getCurrentFunction());

        assertTrue(lifeCycle.getTitle().contains("xyzzy"));
        assertTrue(lifeCycle.getTitle().contains("test-application-name"));
        assertTrue(lifeCycle.getTitle().contains("1.0.1"));
    }

    @Test
    @DirtiesContext
    public void functionListener(){
        final AtomicInteger functionCounter = new AtomicInteger(0);

        lifeCycle.addFunctionListener(new FunctionListener(){
            @Override
            public void functionUpdated(String function) {
                functionCounter.incrementAndGet();
            }
        });

        lifeCycle.setCurrentFunction("new function");

        assertEquals(1, functionCounter.intValue());
    }

    @Test
    @DirtiesContext
    public void titleListener() {
        final AtomicInteger titleCounter = new AtomicInteger(0);

        lifeCycle.addTitleListener(new TitleListener() {
            @Override
            public void titleUpdated(String title) {
                titleCounter.incrementAndGet();
            }
        });

        lifeCycle.refreshTitle();

        assertEquals(1, titleCounter.intValue());
    }


}