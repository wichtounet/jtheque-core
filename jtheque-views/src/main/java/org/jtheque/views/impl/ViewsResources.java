package org.jtheque.views.impl;

import org.jtheque.resources.IResourceService;
import org.springframework.core.io.ClassPathResource;

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

public class ViewsResources {
    public static final String EXIT_ICON = "jtheque-views-exit-icon";
    public static final String XML_ICON = "jtheque-views-xml-icon";
    public static final String UNDO_ICON = "jtheque-views-undo-icon";
    public static final String REDO_ICON = "jtheque-views-redo-icon";
    public static final String OPTIONS_ICON = "jtheque-views-options-icon";
    public static final String UPDATE_ICON = "jtheque-views-update-icon";
    public static final String HELP_ICON = "jtheque-views-help-icon";
    public static final String MAIL_ICON = "jtheque-views-mail-icon";
    public static final String IDEA_ICON = "jtheque-views-idea-icon";
    public static final String ABOUT_ICON = "jtheque-views-about-icon";

    private ViewsResources() {
        super();
    }

    public static void registerResources(IResourceService service) {
        register(service, EXIT_ICON, "org/jtheque/views/images/exit.png");
        register(service, XML_ICON, "org/jtheque/views/images/xml.png");
        register(service, UNDO_ICON, "org/jtheque/views/images/xml.png");
        register(service, REDO_ICON, "org/jtheque/views/images/undo.png");
        register(service, OPTIONS_ICON, "org/jtheque/views/images/options.png");
        register(service, UPDATE_ICON, "org/jtheque/views/images/update.png");
        register(service, HELP_ICON, "org/jtheque/views/images/help.png");
        register(service, MAIL_ICON, "org/jtheque/views/images/mail.png");
        register(service, IDEA_ICON, "org/jtheque/views/images/idea.png");
        register(service, ABOUT_ICON, "org/jtheque/views/images/about.png");
    }

    private static void register(IResourceService service, String resourceId, String resourcePath) {
        service.registerResource(resourceId, new ClassPathResource(resourcePath));
    }
}