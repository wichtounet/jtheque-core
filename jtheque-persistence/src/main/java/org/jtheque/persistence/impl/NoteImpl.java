package org.jtheque.persistence.impl;

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

import org.jtheque.persistence.able.IDaoNotes;
import org.jtheque.persistence.able.Note;
import org.jtheque.utils.Constants;
import org.jtheque.utils.bean.EqualsUtils;

/**
 * A note.
 *
 * @author Baptiste Wicht
 */
public final class NoteImpl implements Note {
    private final String key;
    private final IDaoNotes.NoteType value;

    /**
     * Construct a new NoteImpl with a specific value and a text key.
     *
     * @param value The value of the note.
     * @param key   The text key.
     */
    public NoteImpl(IDaoNotes.NoteType value, String key) {
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
    public int hashCode() {
        int result = Constants.HASH_CODE_START;

        result = Constants.HASH_CODE_PRIME * result + value.intValue();
        result = Constants.HASH_CODE_PRIME * result + (key == null ? 0 : key.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (EqualsUtils.areObjectIncompatible(this, obj)) {
            return false;
        }

        final NoteImpl other = (NoteImpl) obj;

        if (value != other.value) {
            return false;
        }

        return !EqualsUtils.areNotEquals(key, other.key);

    }

    @Override
    public IDaoNotes.NoteType getValue() {
        return value;
    }
}