package org.jtheque.errors;

import org.jtheque.unit.AbstractJThequeTest;

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
@ContextConfiguration(locations = "jtheque-errors-test.xml")
public class ErrorServiceTest extends AbstractJThequeTest {
    @Resource
    private ErrorService errorService;

    @Test
    public void initOK() {
        assertNotNull(errorService);
    }

    @Test
    public void errorsEmptyByDefault() {
        assertTrue(errorService.getErrors().isEmpty());
    }

    @Test
    @DirtiesContext
    public void addError() {
        Error error = Errors.newError("Test");

        errorService.addError(error);

        assertEquals(1, errorService.getErrors().size());
        assertTrue(errorService.getErrors().contains(error));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unmodifiableCollection() {
        Error error = Errors.newError("Test");

        errorService.getErrors().remove(error);
    }

    @Test
    @DirtiesContext
    public void listenerCalled() {
        final Error error = Errors.newError("Test");

        final AtomicInteger integer = new AtomicInteger(0);

        errorService.addErrorListener(new ErrorListener() {
            @Override
            public void errorOccurred(Error occuredError) {
                integer.incrementAndGet();

                assertEquals(error, occuredError);
            }
        });

        errorService.addError(error);
        assertEquals(1, integer.intValue());
    }

    @Test
    @DirtiesContext
    public void listenerRemoved() {
        final AtomicInteger integer = new AtomicInteger(0);

        ErrorListener listener = new ErrorListener() {
            @Override
            public void errorOccurred(Error occuredError) {
                integer.incrementAndGet();
            }
        };

        errorService.addErrorListener(listener);

        errorService.addError(Errors.newError("Test1"));

        errorService.removeErrorListener(listener);

        errorService.addError(Errors.newError("Test2"));

        assertEquals(1, integer.intValue());
    }
}