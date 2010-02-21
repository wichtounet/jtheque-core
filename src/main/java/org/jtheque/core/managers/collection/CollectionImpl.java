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

import org.jtheque.core.managers.persistence.AbstractEntity;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.HashCodeUtils;

/**
 * An implementation of a collection of film.
 *
 * @author Baptiste Wicht
 */
public final class CollectionImpl extends AbstractEntity implements Collection {
	private String title;
	private boolean protection;
	private String password;

	//Data methods

	@Override
	public void setTitle(String title){
		this.title = title;
	}

	@Override
	public String getTitle(){
		return title;
	}

	@Override
	public boolean isProtection(){
		return protection;
	}

	@Override
	public void setProtection(boolean protection){
		this.protection = protection;
	}

	@Override
	public String getPassword(){
		return password;
	}

	@Override
	public void setPassword(String password){
		this.password = password;
	}

	//Utility class

	@Override
	public String getDisplayableText(){
		return title;
	}

	@Override
	public String toString(){
		return title;
	}

	@Override
	public int hashCode(){
		return HashCodeUtils.hashCodeDirect(title, protection, password);
	}

	@Override
	public boolean equals(Object obj){
        if(obj == null){
            return false;
        }

		Collection other = (Collection) obj;

		return EqualsUtils.areEqualsDirect(this, obj,
				getId(), title, protection, password,
				other.getId(), other.getTitle(), other.isProtection(), other.getPassword());
	}
}