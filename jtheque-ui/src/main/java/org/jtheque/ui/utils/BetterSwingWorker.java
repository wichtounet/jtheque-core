package org.jtheque.ui.utils;

import org.jtheque.utils.ui.SwingUtils;

import javax.swing.SwingWorker;

import java.util.concurrent.ExecutionException;

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
 * A better swing worker without exception swallowing. 
 *
 * Thanks to Jonathan Giles for the idea.
 *
 * @author Baptiste Wicht
 */
public abstract class BetterSwingWorker {
    private final SwingWorker<Void, Void> worker = new SimpleSwingWorker();

    /**
     * Execute the SwingWorker.
     */
    public void execute() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                before();
            }
        });

        worker.execute();
    }

    /**
     * Will be executed in the EDT at the start of the execute.
     */
    protected void before(){
        //Nothing by default
    }

    /**
     * Action make in background.
     *
     * @throws Exception If something happens during the operation.
     */
    protected abstract void doInBackground() throws Exception;

    /**
     * Called in the EDT after the completion of
     */
    protected abstract void done();

    /**
     * A simple swing worker that doesn't swallow exceptions.
     *
     * @author Baptiste Wicht
     * @author Jonathan Giles
     */
    private class SimpleSwingWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            BetterSwingWorker.this.doInBackground();

            return null;
        }

        @Override
        protected void done() {
            try {
                get();
            } catch (final InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (final ExecutionException ex) {
                throw new RuntimeException(ex.getCause());
            }

            BetterSwingWorker.this.done();
        }
    }
}