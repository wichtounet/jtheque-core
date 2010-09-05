package org.jtheque.undo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import java.util.concurrent.atomic.AtomicInteger;

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
@ContextConfiguration(locations = "jtheque-undo-test.xml")
public class UndoServiceTest {
    @Resource
    private IUndoRedoService undoService;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    @Test
    public void initOK() {
        assertNotNull(undoService);
    }

    @Test
    @DirtiesContext
    public void addError() {
        final AtomicInteger undo = new AtomicInteger(0);
        final AtomicInteger redo = new AtomicInteger(0);

        UndoableEdit edit = new TestEdit(undo, redo);

        undoService.addEdit(edit);

        assertEquals(0, undo.intValue());
        assertEquals(0, redo.intValue());

        undoService.undo();

        assertEquals(1, undo.intValue());
        assertEquals(0, redo.intValue());

        undoService.redo();

        assertEquals(1, undo.intValue());
        assertEquals(1, redo.intValue());
    }

    @Test
    @DirtiesContext
    public void listenerCalled() {
        final AtomicInteger counter = new AtomicInteger(0);

        undoService.addStateListener(new StateListener(){
            @Override
            public void stateChanged(String undoName, boolean canUndo, String redoName, boolean canRedo) {
                counter.incrementAndGet();
            }
        });

        undoService.addEdit(new TestEdit(new AtomicInteger(0), new AtomicInteger(0)));

        assertEquals(1, counter.intValue());

        undoService.addEdit(new TestEdit(new AtomicInteger(0), new AtomicInteger(0)));

        assertEquals(2, counter.intValue());

        undoService.undo();

        assertEquals(3, counter.intValue());

        undoService.redo();

        assertEquals(4, counter.intValue());
    }

    @Test
    @DirtiesContext
    public void listenerRemoved() {
        final AtomicInteger counter = new AtomicInteger(0);

        StateListener listener = new StateListener() {
            @Override
            public void stateChanged(String undoName, boolean canUndo, String redoName, boolean canRedo) {
                counter.incrementAndGet();
            }
        };

        undoService.addStateListener(listener);

        undoService.addEdit(new TestEdit(new AtomicInteger(0), new AtomicInteger(0)));

        undoService.removeStateListener(listener);

        undoService.addEdit(new TestEdit(new AtomicInteger(0), new AtomicInteger(0)));

        assertEquals(1, counter.intValue());
    }

    private static class TestEdit extends AbstractUndoableEdit {
        private final AtomicInteger undo;
        private final AtomicInteger redo;

        private TestEdit(AtomicInteger undo, AtomicInteger redo) {
            this.undo = undo;
            this.redo = redo;
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            undo.incrementAndGet();
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            redo.incrementAndGet();
        }
    }
}