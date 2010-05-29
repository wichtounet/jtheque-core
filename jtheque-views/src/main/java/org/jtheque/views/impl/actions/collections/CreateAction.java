package org.jtheque.views.impl.actions.collections;

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

import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.core.utils.Response;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.panel.ICollectionView;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;

/**
 * An action to create the collection.
 *
 * @author Baptiste Wicht
 */
public final class CreateAction extends JThequeAction {
    @Resource
    private ICollectionView collectionView;

    @Resource
    private ICollectionsService collectionsService;

    @Resource
    private ILanguageService languageService;

    /**
     * Construct a new CreateAction.
     */
    public CreateAction() {
        super("collections.actions.create");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Response response = collectionsService.chooseCollection(collectionView.getCollection(), collectionView.getPassword(), true);

        if (!response.isOk()) {
            collectionView.setErrorMessage(languageService.getMessage(response.getKey(), response.getReplaces()));
        }
    }
}
