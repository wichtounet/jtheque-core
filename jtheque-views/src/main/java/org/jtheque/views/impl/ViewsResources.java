package org.jtheque.views.impl;

import org.jtheque.resources.IResourceService;
import org.springframework.core.io.ClassPathResource;

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

public final class ViewsResources {
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