package org.jtheque.core.managers.schema;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.properties.IPropertiesManager;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.utils.Constants;
import org.jtheque.utils.StringUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

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
 * An abstract schema. The only thing this abstract class do is implementing the compareTo() method to ensure for
 * good order in collections.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractSchema implements Schema {
    private SimpleJdbcTemplate jdbcTemplate;

    private static final String ALTER_TABLE = "ALTER TABLE ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String CREATE_TABLE = "CREATE TABLE ";

    /**
     * Return the jdbc template to execute JDBC request.
     *
     * @return The jdbc template.
     */
    protected SimpleJdbcTemplate getJdbcTemplate(){
        if(jdbcTemplate == null){
            jdbcTemplate = CoreUtils.getBean(SimpleJdbcTemplate.class);
        }

        return jdbcTemplate;
    }

    /**
     * Update the the database executing the specified request.
     *
     * @param request The request to execute.
     * @param args The args to give to the request. 
     */
    protected void update(String request, Object... args){
        getJdbcTemplate().update(request, args);
    }

    /**
     * Alter the specified table using the specified command and args. All the {} will be
     * replaced with the name of the table.
     *
     * @param table The table to alter.
     * @param command The command to alter the table with.
     * @param args The args to fill the command with.
     */
    protected void alterTable(String table, String command, Object... args){
        getJdbcTemplate().update((ALTER_TABLE + table + ' ' + command).replace("{}", table), args);
    }

    /**
     * Create the specified table.
     *
     * @param table The table to create.
     * @param columns The columns to create. The ( and ) are automatically added.
     */
    protected void createTable(String table, String columns){
        getJdbcTemplate().update(CREATE_TABLE + table + " (" + columns + ')');
    }

    /**
     * Update the database. All the {} will be replaced with the name of the specified table.
     *
     * @param table The table to update.
     * @param command The command to use to update the database with.
     * @param args The args to fill the command with.
     */
    protected void updateTable(CharSequence table, String command, Object... args){
        getJdbcTemplate().update(command.replace("{}", table), args);
    }

    /**
     * Create an insert request for the specified table.
     *
     * @param table The table to insert into.
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
        } else if(hasDependency && hasOtherDependency){
            for (String dependency : other.getDependencies()) {
                if (dependency.equals(getId())) {//The other depends on me
                    return -1;
                }
            }

            for (String dependency : getDependencies()) {
                if (dependency.equals(other.getId())) {//I depends on the other
                    return 1;
                }
            }
        }

        return 0;
    }

    @Override
    public int hashCode() {
        int hash = Constants.HASH_CODE_START;
        
        hash = Constants.HASH_CODE_PRIME * hash + getVersion().hashCode();
        hash = Constants.HASH_CODE_PRIME * hash + getId().hashCode();
        
        for(String dependency : getDependencies()){
            hash = Constants.HASH_CODE_PRIME * hash + dependency.hashCode();
        }
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return Managers.getManager(IPropertiesManager.class).areEquals(this, obj, "id", "version");
    }

    @Override
    public final String toString() {
        return getId() + ':' + getVersion();
    }
}
