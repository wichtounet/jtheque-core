package org.jtheque.collections;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.core.utils.Response;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.persistence.able.DataListener;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileUtils;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * The implementation of the collections service.
 *
 * @author Baptiste Wicht
 */
public final class CollectionsService implements ICollectionsService, BundleContextAware {
	@Resource
	private IDaoCollections daoCollections;

    private BundleContext bundleContext;

    private final WeakEventListenerList listeners = new WeakEventListenerList();

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

        return new Response(true);
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