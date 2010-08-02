package org.jtheque.persistence.impl;

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

import org.jtheque.persistence.able.DaoNotes;
import org.jtheque.persistence.able.Note;
import org.jtheque.utils.bean.EqualsBuilder;
import org.jtheque.utils.bean.HashCodeUtils;

/**
 * A note.
 *
 * @author Baptiste Wicht
 */
public final class NoteImpl implements Note {
    private final String key;
    private final DaoNotes.NoteType value;

    /**
     * Construct a new NoteImpl with a specific value and a text key.
     *
     * @param value The value of the note.
     * @param key   The text key.
     */
    public NoteImpl(DaoNotes.NoteType value, String key) {
        super();

        this.value = value;
        this.key = key;
    }

    @Override
    public String getI18nKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }

    @Override
    public DaoNotes.NoteType getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(value, key);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NoteImpl) {
            NoteImpl other = (NoteImpl) o;

            return EqualsBuilder.newBuilder(this, other).
                    addField(value, other.value).
                    addField(key, other.key).
                    areEquals();
        }

        return false;

    }
}