package org.jtheque.errors;

import org.jtheque.errors.Error.Level;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

public class ErrorsTest {
    @Test
    public void title(){
        Error error = Errors.newError("Title 1");

        assertEquals("Title 1", error.getTitle(null));
        assertEquals(Level.ERROR, error.getLevel());
    }

    @Test
    public void titleLevel() {
        Error error = Errors.newError("Title 2", Level.WARNING);

        assertEquals("Title 2", error.getTitle(null));
        assertEquals(Level.WARNING, error.getLevel());
    }

    @Test
    public void titleDetails() {
        Error error = Errors.newError("Title 3", "Details 3");

        assertEquals("Title 3", error.getTitle(null));
        assertEquals("Details 3", error.getDetails(null));
        assertEquals(Level.ERROR, error.getLevel());
    }

    @Test
    public void throwable() {
        Throwable throwable = new RuntimeException("Simple message");

        Error error = Errors.newError(throwable);

        assertEquals("Simple message", error.getTitle(null));
        assertEquals(Level.ERROR, error.getLevel());
    }

    @Test
    public void throwableTitle() {
        Throwable throwable = new RuntimeException("Simple message");

        Error error = Errors.newError("Simple title", throwable);

        assertEquals("Simple title", error.getTitle(null));
        assertEquals(Level.ERROR, error.getLevel());
    }
}