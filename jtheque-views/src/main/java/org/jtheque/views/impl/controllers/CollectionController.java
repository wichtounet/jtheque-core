package org.jtheque.views.impl.controllers;

import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.Response;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.panel.ICollectionView;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

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

public class CollectionController extends AbstractController {
    @Resource
    private ICollectionView collectionView;

    @Resource
    private ICollectionsService collectionsService;

    @Resource
    private ILanguageService languageService;

    @Resource
    private ICore core;

    private void cancel(){
        core.getLifeCycle().exit();
    }

    private void choose() {
        Response response = collectionsService.chooseCollection(collectionView.getCollection(), collectionView.getPassword(), false);

        displayResponse(response);
    }

    private void create() {
        Response response = collectionsService.chooseCollection(collectionView.getCollection(), collectionView.getPassword(), true);

        displayResponse(response);
    }

    private void displayResponse(Response response) {
        if (!response.isOk()) {
            collectionView.setErrorMessage(languageService.getMessage(response.getKey(), (Object[]) response.getReplaces()));
        }
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("collections.actions.choose", "choose");
        translations.put("collections.actions.cancel", "cancel");
        translations.put("collections.actions.create", "create");

        return translations;
    }
}