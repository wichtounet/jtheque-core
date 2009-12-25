package org.jtheque.core.managers.core.application;

import org.jtheque.utils.bean.InternationalString;

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
 * Internationalizable properties directly configured in the application.xml file.
 *
 * @author Baptiste Wicht
 */
public final class DirectValuesApplicationProperties implements ApplicationProperties {
    private InternationalString author;
    private InternationalString name;
    private InternationalString site;
    private InternationalString email;
    private InternationalString copyright;

    /**
     * Set the author values.
     *
     * @param author The author values.
     */
    void setAuthor(InternationalString author){
        this.author = author;
    }

    /**
     * Set the name values.
     *
     * @param name The name values.
     */
    void setName(InternationalString name){
        this.name = name;
    }

    /**
     * Set the site values.
     *
     * @param site The site values.
     */
    void setSite(InternationalString site){
        this.site = site;
    }

    /**
     * Set the email values.
     *
     * @param email The email values.
     */
    void setEmail(InternationalString email){
        this.email = email;
    }

    /**
     * Set the copyright values.
     *
     * @param copyright The copyright values.
     */
    void setCopyright(InternationalString copyright){
        this.copyright = copyright;
    }

    @Override
    public String getName(){
        return name.toString();
    }

    @Override
    public String getAuthor(){
        return author.toString();
    }

    @Override
    public String getEmail(){
        return email.toString();
    }

    @Override
    public String getSite(){
        return site.toString();
    }

    @Override
    public String getCopyright(){
        return copyright.toString();
    }
}