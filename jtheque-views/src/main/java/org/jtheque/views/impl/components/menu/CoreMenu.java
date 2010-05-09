package org.jtheque.views.impl.components.menu;

import org.jtheque.core.ICore;
import org.jtheque.features.Feature;
import org.jtheque.undo.IUndoRedoService;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;
import org.jtheque.views.impl.ViewsResources;
import org.jtheque.views.impl.actions.about.DisplayAboutViewAction;
import org.jtheque.views.impl.actions.author.AcInformOfABug;
import org.jtheque.views.impl.actions.author.AcOpenHelp;
import org.jtheque.views.impl.actions.author.AcProposeImprovement;
import org.jtheque.views.impl.actions.backup.AcBackup;
import org.jtheque.views.impl.actions.backup.AcRestore;
import org.jtheque.views.impl.actions.ExitAction;
import org.jtheque.views.impl.actions.config.DisplayConfigViewAction;
import org.jtheque.views.impl.actions.event.DisplayLogsViewAction;
import org.jtheque.views.impl.actions.messages.DisplayMessagesViewAction;
import org.jtheque.views.impl.actions.module.DisplayModuleViewAction;
import org.jtheque.views.impl.actions.undo.RedoAction;
import org.jtheque.views.impl.actions.undo.UndoAction;

import java.util.List;

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
 * The menu of the core.
 *
 * @author Baptiste Wicht
 */
public final class CoreMenu extends AbstractMenu {
    private final ICore core;
    private final IUndoRedoService undoRedoService;
    private final IViews views;
    private final IViewService viewService;

    public CoreMenu(ICore core, IUndoRedoService undoRedoService, IViews views, IViewService viewService) {
        super();

        this.core = core;
        this.undoRedoService = undoRedoService;
        this.views = views;
        this.viewService = viewService;
    }

    @Override
    protected List<Feature> getFileMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(200, new AcBackup(), ViewsResources.XML_ICON),
                createSubFeature(201, new AcRestore(), ViewsResources.XML_ICON),
                createSeparatedSubFeature(1000, new ExitAction(core), ViewsResources.EXIT_ICON)
        );
    }

    @Override
    protected List<Feature> getEditMenuSubFeatures(){
        return features(
                createSubFeature(1, new UndoAction(undoRedoService), ViewsResources.UNDO_ICON),
                createSubFeature(2, new RedoAction(undoRedoService), ViewsResources.REDO_ICON)
        );
    }

    @Override
    protected List<Feature> getAdvancedMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(500, new DisplayConfigViewAction(views), ViewsResources.OPTIONS_ICON),
                createSeparatedSubFeature(750, new DisplayModuleViewAction(views), ViewsResources.UPDATE_ICON)
        );
    }

    @Override
    protected List<Feature> getHelpMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(1, new AcOpenHelp(), ViewsResources.HELP_ICON),
                createSeparatedSubFeature(2, new AcInformOfABug(), ViewsResources.MAIL_ICON),
                createSeparatedSubFeature(4, new AcProposeImprovement(), ViewsResources.IDEA_ICON),
                createSeparatedSubFeature(6, new DisplayMessagesViewAction(views)),
                createSeparatedSubFeature(25, new DisplayLogsViewAction(views)),
                createSeparatedSubFeature(150, new DisplayAboutViewAction(core, viewService), ViewsResources.MAIL_ICON)
        );
    }
}