package org.jtheque.core.managers.schema;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

/**
 * An importer for HSQL. It make generic the process of import from HSQL File inserts with a little mapping.
 *
 * @author Baptiste Wicht
 */
public final class HSQLImporter {
    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    private final Map<String, HSQLMatcher> matchers = new HashMap<String, HSQLMatcher>(20);

    /**
     * Construct a new HSQLImporter.
     */
    public HSQLImporter() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    /**
     * Import the inserts.
     *
     * @param inserts The inserts to add
     */
    public void importInserts(Iterable<Insert> inserts) {
        for (Insert insert : inserts) {
            matchers.get(insert.getTable()).exec(insert);
        }
    }

    /**
     * Match the table to the specific request.
     *
     * @param table     The table in HSQL.
     * @param request   The request to use to insert to current database.
     * @param positions The positions to use to fill the request.
     */
    public void match(String table, String request, int... positions) {
        matchers.put(table, new HSQLMatcher(request, positions, null));
    }

    /**
     * Match the table to the specific request.
     *
     * @param table     The table in HSQL.
     * @param request   The request to use to insert to current database.
     * @param positions The positions to use to fill the request.
     * @param append    An object to append to the list of params.
     */
    public void match(String table, String request, Object append, int... positions) {
        matchers.put(table, new HSQLMatcher(request, positions, append));
    }

    /**
     * An object to match a request with the arguments of a HSQL insert.
     *
     * @author Baptiste Wicht
     */
    private final class HSQLMatcher {
        private final String request;
        private final int[] positions;
        private final Object append;

        /**
         * Construct a new <code>HSQLMatcher</code>.
         *
         * @param request   The request to insert in the current database.
         * @param positions The positions to get the informations from the HSQL insert.
         * @param append    The object to append to the end of the params.
         */
        private HSQLMatcher(String request, int[] positions, Object append) {
            super();

            this.request = request;
            this.positions = Arrays.copyOf(positions, positions.length);
            this.append = append;
        }

        /**
         * Exec the insert.
         *
         * @param insert The HSQL Insert to get the informations from.
         */
        public void exec(Insert insert) {
            Object[] parameters = new Object[append == null ? positions.length : positions.length + 1];

            for (int i = 0; i < positions.length; i++) {
                parameters[i] = insert.getString(positions[i]);
            }

            if (append == null) {
                parameters[parameters.length - 1] = append;
            }

            jdbcTemplate.update(request, parameters);
        }
    }
}
