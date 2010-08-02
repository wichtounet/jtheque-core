package org.jtheque.views.impl.controllers;

import org.jtheque.collections.able.CollectionsService;
import org.jtheque.core.able.Core;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.utils.bean.Response;
import org.jtheque.ui.able.Action;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.panel.CollectionView;

import javax.annotation.Resource;

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
 * A controller for the collection view.
 *
 * @author Baptiste Wicht
 */
public class CollectionController extends AbstractController<CollectionView> {
    @Resource
    private CollectionsService collectionsService;

    @Resource
    private LanguageService languageService;

    @Resource
    private Core core;

    /**
     * Construct a new Collection controller. 
     */
    public CollectionController() {
        super(CollectionView.class);
    }

    /**
     * Cancel the current operation.
     */
    @Action("collections.actions.cancel")
    public void cancel(){
        core.getLifeCycle().exit();
    }

    /**
     * Choose the collection.
     */
    @Action("collections.actions.choose")
    public void choose() {
        Response response = collectionsService.chooseCollection(getView().getCollection(), getView().getPassword(), false);

        displayResponse(response);
    }

    /**
     * Create a collection.
     */
    @Action("collections.actions.create")
    public void create() {
        Response response = collectionsService.chooseCollection(getView().getCollection(), getView().getPassword(), true);

        displayResponse(response);
    }

    /**
     * Display the response if necessary.
     *
     * @param response The response to display. 
     */
    private void displayResponse(Response response) {
        if (!response.isOk()) {
            getView().setErrorMessage(languageService.getMessage(response.getKey(), (Object[]) response.getReplaces()));
        }
    }
}