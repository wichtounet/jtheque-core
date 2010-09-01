package org.jtheque.collections.impl;

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

import org.jtheque.collections.CollectionListener;
import org.jtheque.collections.DaoCollections;
import org.jtheque.collections.DataCollection;
import org.jtheque.collections.CollectionsService;
import org.jtheque.core.Core;
import org.jtheque.file.FileService;
import org.jtheque.persistence.DataListener;
import org.jtheque.schemas.Schema;
import org.jtheque.schemas.SchemaService;
import org.jtheque.utils.CryptoUtils;
import org.jtheque.utils.Hasher;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.annotations.GuardedBy;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.Response;
import org.jtheque.utils.collections.WeakEventListenerList;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * The implementation of the collections service.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class CollectionsServiceImpl implements CollectionsService {
    @GuardedInternally
    private final DaoCollections daoCollections;
    
    @GuardedInternally
    private final Core core;

    @GuardedInternally
    private final WeakEventListenerList<CollectionListener> listeners = WeakEventListenerList.create();

    @GuardedBy("this")
    private boolean collectionChosen;

    /**
     * Construct a new CollectionsServiceImpl.
     *
     * @param daoCollections The dao collections.
     * @param fileService    The file service.
     * @param core           The core.
     * @param schemaService  The schema service.
     * @param schema         The schema of the collections.
     */
    public CollectionsServiceImpl(DaoCollections daoCollections, FileService fileService, Core core,
                                  SchemaService schemaService, Schema schema) {
        super();

        this.daoCollections = daoCollections;
        this.core = core;

        fileService.registerBackuper("jtheque-collections", new CollectionsBackuper(daoCollections));

        schemaService.registerSchema("", schema);

        SimplePropertiesCache.put("collectionChosen", false);
    }

    @Override
    public Response chooseCollection(String collection, String password, boolean create) {
        synchronized (this) {
            if (collectionChosen) {
                return new Response(false);
            }

            if (create) {
                if (daoCollections.exists(collection)) {
                    return new Response(false, "error.module.collection.exists");
                } else {
                    createCollection(collection, password);

                    daoCollections.setCurrentCollection(daoCollections.getCollection(collection));

                    core.getConfiguration().setLastCollection(collection);
                }
            } else if (!login(collection, password)) {
                return new Response(false, "error.module.collection");
            }

            SimplePropertiesCache.put("collectionChosen", true);

            fireCollectionChosen();

            collectionChosen = true;

            return new Response(true);
        }
    }

    /**
     * Login using the specified collection and password.
     *
     * @param title    The collection title.
     * @param password The password.
     *
     * @return <code>true</code> if the login is correct else <code>false</code>.
     */
    private boolean login(String title, String password) {
        if (isLoginIncorrect(title, password)) {
            return false;
        }

        daoCollections.setCurrentCollection(daoCollections.getCollection(title));

        core.getConfiguration().setLastCollection(title);

        return true;
    }

    /**
     * Indicate if a login is correct or not
     *
     * @param title    The title of the collection.
     * @param password The password to login to the collection.
     *
     * @return true if the login is correct else false.
     */
    private boolean isLoginIncorrect(String title, String password) {
        DataCollection collection = daoCollections.getCollection(title);

        if (collection == null) {
            return true;
        }

        if (collection.isProtection()) {
            String encrypted = CryptoUtils.hashMessage(password, Hasher.SHA256);

            if (!encrypted.equals(collection.getPassword())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Fire a collection chosen event.
     */
    private void fireCollectionChosen() {
        for (CollectionListener listener : listeners) {
            listener.collectionChosen();
        }
    }

    @Override
    public void addCollectionListener(CollectionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeCollectionListener(CollectionListener listener) {
        listeners.remove(listener);
    }

    /**
     * Create a collection.
     *
     * @param title    The title of the collection.
     * @param password The password of the collection.
     */
    @Transactional
    private void createCollection(String title, String password) {
        DataCollection collection = daoCollections.create();

        collection.setTitle(title);

        if (StringUtils.isEmpty(password)) {
            collection.setProtection(false);
            collection.setPassword("");
        } else {
            collection.setProtection(true);
            collection.setPassword(CryptoUtils.hashMessage(password, Hasher.SHA256));
        }

        daoCollections.save(collection);
    }

    @Override
    public Collection<DataCollection> getDatas() {
        return daoCollections.getCollections();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoCollections.addDataListener(listener);
    }

    @Override
    @Transactional
    public void clearAll() {
        daoCollections.clearAll();
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }
}