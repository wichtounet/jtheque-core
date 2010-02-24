package org.jtheque.core.managers.collection;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * The implementation of the collections service.
 *
 * @author Baptiste Wicht
 */
public final class CollectionsService implements ICollectionsService {
	@Resource
	private IDaoCollections daoCollections;

    @Override
    public boolean chooseCollection(String collection, String password, boolean create) {
        if (create) {
            if(daoCollections.exists(collection)){
                Managers.getManager(IViewManager.class).getCollectionView().setErrorMessage(
                        CoreUtils.getMessage("error.module.collection.exists"));
            } else {
                createCollection(collection, password);

                daoCollections.setCurrentCollection(daoCollections.getCollection(collection));

                Core.getInstance().getConfiguration().setLastCollection(collection);
            }
        } else if (!login(collection, password)) {
            Managers.getManager(IViewManager.class).getCollectionView().setErrorMessage(
                    CoreUtils.getMessage("error.module.collection"));

            return false;
        }

        return true;
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

        Core.getInstance().getConfiguration().setLastCollection(title);

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