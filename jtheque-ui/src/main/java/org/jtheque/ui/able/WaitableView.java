package org.jtheque.ui.able;

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
 * A view that can wait. It seems display a wait animation and block the view during a certain amount of time.
 *
 * @author Baptiste Wicht
 */
public interface WaitableView {
    /**
     * Start the wait. The glass pane will start the wait animation.
     */
    void startWait();

    /**
     * Stop the wait. The glass pane will stop the wait animation.
     */
    void stopWait();
}
