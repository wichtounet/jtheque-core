package org.jtheque.schemas;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.persistence.DaoPersistenceContext;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.EqualsBuilder;
import org.jtheque.utils.bean.HashCodeUtils;
import org.jtheque.utils.collections.ArrayUtils;

import org.osgi.framework.BundleContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

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

/**
 * An abstract schema. The only thing this abstract class do is implementing the compareTo() method to ensure for good
 * order in collections.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractSchema implements Schema {
    private SimpleJdbcTemplate jdbcTemplate;

    private static final String ALTER_TABLE = "ALTER TABLE ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String CREATE_TABLE = "CREATE TABLE ";

    /**
     * Set the JDBC Template of the schema.
     *
     * @param jdbcTemplate The template to set.
     */
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Set the JDBC template of the schema using the bundle context to search it.
     *
     * @param bundleContext The bundle context to search for the JDBC template.
     */
    void setJdbcTemplate(BundleContext bundleContext) {
        DaoPersistenceContext daoPersistenceContext = OSGiUtils.getService(bundleContext, DaoPersistenceContext.class);

        jdbcTemplate = daoPersistenceContext.getTemplate();
    }

    /**
     * Return the jdbc template to execute JDBC request.
     *
     * @return The jdbc template.
     */
    protected SimpleJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Update the the database executing the specified request.
     *
     * @param request The request to execute.
     * @param args    The args to give to the request.
     */
    protected void update(String request, Object... args) {
        jdbcTemplate.update(request, args);
    }

    /**
     * Alter the specified table using the specified command and args. All the {} will be replaced with the name of the
     * table.
     *
     * @param table   The table to alter.
     * @param command The command to alter the table with.
     * @param args    The args to fill the command with.
     */
    protected void alterTable(String table, String command, Object... args) {
        jdbcTemplate.update((ALTER_TABLE + table + ' ' + command).replace("{}", table), args);
    }

    /**
     * Create the specified table.
     *
     * @param table   The table to create.
     * @param columns The columns to create. The ( and ) are automatically added.
     */
    protected void createTable(String table, String columns) {
        jdbcTemplate.update(CREATE_TABLE + table + " (" + columns + ')');
    }

    /**
     * Update the database. All the {} will be replaced with the name of the specified table.
     *
     * @param table   The table to org.jtheque.update.
     * @param command The command to use to org.jtheque.update the database with.
     * @param args    The args to fill the command with.
     */
    protected void updateTable(CharSequence table, String command, Object... args) {
        jdbcTemplate.update(command.replace("{}", table), args);
    }

    /**
     * Create an insert request for the specified table.
     *
     * @param table  The table to insert into.
     * @param values The values of the insert request.
     *
     * @return The insert into request.
     */
    protected static String insert(String table, String values) {
        return INSERT_INTO + table + ' ' + values;
    }

    @Override
    public final int compareTo(Schema other) {
        boolean hasDependency = StringUtils.isNotEmpty(getDependencies());
        boolean hasOtherDependency = StringUtils.isNotEmpty(other.getDependencies());

        if (hasDependency && !hasOtherDependency) {
            return 1;
        } else if (!hasDependency && hasOtherDependency) {
            return -1;
        } else {
            //The other depends on me
            if (ArrayUtils.contains(other.getDependencies(), getId())) {
                return -1;
            }

            //I depends on the other
            if (ArrayUtils.contains(getDependencies(), other.getId())) {
                return 1;
            }
        }

        return 0;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(getVersion(), getId(), getDependencies());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Schema) {
            Schema other = (Schema) o;

            return EqualsBuilder.newBuilder(this, other).
                    addField(getId(), other.getId()).
                    addField(getVersion(), other.getVersion()).
                    areEquals();
        }

        return false;
    }

    @Override
    public final String toString() {
        return getId() + ':' + getVersion();
    }
}
