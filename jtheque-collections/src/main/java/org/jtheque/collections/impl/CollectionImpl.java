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

import org.jtheque.collections.able.Collection;
import org.jtheque.persistence.utils.AbstractEntity;
import org.jtheque.utils.bean.EqualsBuilder;
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
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isProtection() {
        return protection;
    }

    @Override
    public void setProtection(boolean protection) {
        this.protection = protection;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    //Utility class

    @Override
    public String getDisplayableText() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(title, protection, password);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CollectionImpl) {
            CollectionImpl other = (CollectionImpl) o;

            return EqualsBuilder.newBuilder(this, other).
                    addField(getId(), other.getId()).
                    addField(title, other.title).
                    addField(protection, other.protection).
                    addField(password, other.password).
                    areEquals();
        }

        return false;
    }
}