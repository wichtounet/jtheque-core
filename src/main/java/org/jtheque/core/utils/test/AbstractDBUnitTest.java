package org.jtheque.core.utils.test;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.fail;

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
 * An abstract class for unit testing with DBUnit.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractDBUnitTest {
    private IDatabaseConnection connection;

    private final String datasetPath;

    /**
     * Construct a new AbstractDBUnitTest.
     *
     * @param datasetPath The path to the dataset.
     */
    protected AbstractDBUnitTest(String datasetPath) {
        super();

        this.datasetPath = datasetPath;
    }

    /**
     * Init the DB with the specific data source.
     *
     * @param dataSource The data source to use to populate the DB.
     */
    protected void initDB(DataSource dataSource) {
        try {
            connection = new DatabaseDataSourceConnection(dataSource);

            IDataSet dataSet = new FlatXmlDataSet(getClass().getResource(datasetPath));

            createHsqldbTables(dataSet, connection.getConnection());
        } catch (SQLException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (DataSetException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Set up the test environment.
     */
    @Before
    public void setUp() {
        try {
            IDataSet dataSet = new FlatXmlDataSet(getClass().getResource(datasetPath));

            DatabaseOperation.CLEAN_INSERT.execute(
                    connection,
                    dataSet);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Return the table with the specified name.
     *
     * @param s The name of the table.
     * @return The table if found else null.
     */
    protected final ITable getTable(String s) {
        try {
            return connection.createDataSet().getTable(s);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        return null;
    }

    /**
     * Return the value in the specified table, at the specified column and at the row specified by the primary key.
     *
     * @param table  The table.
     * @param pk     The primary key.
     * @param column The column.
     * @return The value.
     */
    protected Object getValue(String table, int pk, String column) {
        try {
            return getTable(table).getValue(pk, column);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        return null;
    }

    /**
     * Return the row count in the specified table.
     *
     * @param table The table to count the row.
     * @return The row count of the table.
     */
    protected int getRowCount(String table) {
        return getTable(table).getRowCount();
    }

    /**
     * Return the database connection.
     *
     * @return The database connection.
     */
    protected final IDatabaseConnection getConnection() {
        return connection;
    }

    /**
     * Create the HSQL tables.
     *
     * @param dataSet    The dataset.
     * @param connection The database connection.
     * @throws DataSetException If an error occurs during dataset reading.
     * @throws SQLException     If an error occurs during populating database.
     */
    protected final void createHsqldbTables(IDataSet dataSet, Connection connection) throws DataSetException, SQLException {
        String[] tableNames = dataSet.getTableNames();

        StringBuilder sql = new StringBuilder("");

        for (String tableName : tableNames) {
            ITable table = dataSet.getTable(tableName);
            ITableMetaData metadata = table.getTableMetaData();
            Column[] columns = metadata.getColumns();

            sql.append("create table if not exists ").append(tableName).append("( ");

            boolean first = true;
            for (Column column : columns) {
                if (!first) {
                    sql.append(", ");
                }

                String columnName = column.getColumnName();
                String type = resolveType((String) table.getValue(0, columnName));
                sql.append(columnName).append(" ").append(type);

                if (first) {
                    if (!columnName.contains("FK")) {
                        sql.append(" IDENTITY PRIMARY KEY");
                    }

                    first = false;
                }
            }
            sql.append("); ");
        }

        connection.prepareStatement(sql.toString()).executeUpdate();
    }

    /**
     * Resolve the DB type from the string.
     *
     * @param str The string value.
     * @return The DB type.
     */
    private static String resolveType(String str) {
        try {
            if (new Integer(str).toString().equals(str)) {
                return "int";
            }
        } catch (Exception e) {
            //Nothing to do here
        }

        return "varchar";
    }
}