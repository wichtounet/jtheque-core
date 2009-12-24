package org.jtheque.core.managers.persistence;

import org.jtheque.core.managers.Managers;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

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
 * This class is a simple data source who place the database to the current folder specified
 * in the application.
 *
 * @author Baptiste Wicht
 */
public final class JThequeDataSource extends SingleConnectionDataSource {
    @Override
    public void setUrl(String s) {
        String url = s.replace("{current}", Managers.getCore().getApplication().getFolderPath());

        super.setUrl(url);
    }
}