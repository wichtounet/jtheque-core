package org.jtheque.views.able;

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
 * A splash manager specification.
 *
 * @author Baptiste Wicht
 */
public interface ISplashService {
    /**
     * Display the splash screen.
     */
    void displaySplashScreen();

    /**
     * Close the splash screen.
     */
    void closeSplashScreen();

    /**
     * Fill the main view.
     */
    void fillMainView();

    /**
     * Init the views. 
     */
    void initViews();
}