package org.jtheque.collections;

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

import org.jtheque.core.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.core.utils.Response;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.file.IFileService;
import org.jtheque.persistence.able.DataListener;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileUtils;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.transaction.annotation.Transactional;

/**
 * The implementation of the collections service.
 *
 * @author Baptiste Wicht
 */
public final class CollectionsService implements ICollectionsService, BundleContextAware {
	private final IDaoCollections daoCollections;

    private BundleContext bundleContext;

    private final WeakEventListenerList listeners = new WeakEventListenerList();

    public CollectionsService(IDaoCollections daoCollections, IFileService fileService) {
        super();

        this.daoCollections = daoCollections;

        fileService.registerBackuper("jtheque-collections", new CoreBackuper(daoCollections));
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public Response chooseCollection(String collection, String password, boolean create) {
        if (create) {
            if(daoCollections.exists(collection)){
                return new Response(false, "error.module.collection.exists");
            } else {
                createCollection(collection, password);

                daoCollections.setCurrentCollection(daoCollections.getCollection(collection));

                OSGiUtils.getService(bundleContext, ICore.class).getConfiguration().setLastCollection(collection);
            }
        } else if (!login(collection, password)) {
            return new Response(false, "error.module.collection");
        }

        fireCollectionChoosed();

        return new Response(true);
    }

    private void fireCollectionChoosed() {
        for(CollectionListener listener : listeners.getListeners(CollectionListener.class)){
            listener.collectionChoosed();
        }
    }

    @Override
    public void addCollectionListener(CollectionListener listener) {
        listeners.add(CollectionListener.class, listener);
    }

    @Override
    public void removeCollectionListener(CollectionListener listener) {
        listeners.remove(CollectionListener.class, listener);
    }

    /**
	 * Create a collection.
	 *
	 * @param title The title of the collection.
	 * @param password The password of the collection.
	 */
	@Transactional
	private void createCollection(String title, String password){
        Collection collection = daoCollections.create();

        collection.setTitle(title);

		if (StringUtils.isEmpty(password)){
			collection.setProtection(false);
            collection.setPassword("");
		} else {
			collection.setProtection(true);
			collection.setPassword(FileUtils.encryptKey(password));
		}

		daoCollections.create(collection);
	}

	/**
	 * Login using the specified collection and password.
	 *
	 * @param title The collection title.
	 * @param password The password.
	 *
	 * @return <code>true</code> if the login is correct else <code>false</code>.
	 */
	public boolean login(String title, String password){
		if (isLoginIncorrect(title, password)){
			return false;
		}

		daoCollections.setCurrentCollection(daoCollections.getCollection(title));

        OSGiUtils.getService(bundleContext, ICore.class).getConfiguration().setLastCollection(title);

        return true;
	}

	/**
	 * Indicate if a login is correct or not
	 *
	 * @param title The title of the collection.
	 * @param password The password to login to the collection.
	 *
	 * @return true if the login is correct else false.
	 */
	private boolean isLoginIncorrect(String title, String password){
		Collection collection = daoCollections.getCollection(title);

		if (collection == null){
			return true;
		}

		if (collection.isProtection()){
			String encrypted = FileUtils.encryptKey(password);

			if (!encrypted.equals(collection.getPassword())){
				return true;
			}
		}

		return false;
	}

    @Override
	public java.util.Collection<Collection> getDatas(){
		return daoCollections.getCollections();
	}

	@Override
	public void addDataListener(DataListener listener){
		daoCollections.addDataListener(listener);
	}

	@Override
	@Transactional
	public void clearAll(){
		daoCollections.clearAll();
	}

	@Override
	public String getDataType(){
		return DATA_TYPE;
	}
}