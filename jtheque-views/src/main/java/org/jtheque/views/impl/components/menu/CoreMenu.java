package org.jtheque.views.impl.components.menu;

import org.jtheque.core.ICore;
import org.jtheque.features.Feature;
import org.jtheque.undo.IUndoRedoService;
import org.jtheque.views.impl.ViewsResources;
import org.jtheque.views.impl.actions.about.DisplayAboutViewAction;
import org.jtheque.views.impl.actions.author.AcInformOfABug;
import org.jtheque.views.impl.actions.author.AcOpenHelp;
import org.jtheque.views.impl.actions.author.AcProposeImprovement;
import org.jtheque.views.impl.actions.backup.AcBackup;
import org.jtheque.views.impl.actions.backup.AcRestore;
import org.jtheque.views.impl.actions.ExitAction;
import org.jtheque.views.impl.actions.undo.RedoAction;
import org.jtheque.views.impl.actions.undo.UndoAction;

import java.util.List;

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
 * The menu of the core.
 *
 * @author Baptiste Wicht
 */
public final class CoreMenu extends AbstractMenu {
    private final ICore core;
    private final IUndoRedoService undoRedoService;

    public CoreMenu(ICore core, IUndoRedoService undoRedoService) {
        super();

        this.core = core;
        this.undoRedoService = undoRedoService;
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
                createSeparatedSubFeature(500, createDisplayViewAction("config.actions.display", "configView"), ViewsResources.OPTIONS_ICON),
                createSeparatedSubFeature(750, createDisplayViewAction("modules.actions.manage", "moduleView"), ViewsResources.UPDATE_ICON)
        );
    }

    @Override
    protected List<Feature> getHelpMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(1, new AcOpenHelp(), ViewsResources.HELP_ICON),
                createSeparatedSubFeature(2, new AcInformOfABug(), ViewsResources.MAIL_ICON),
                createSeparatedSubFeature(4, new AcProposeImprovement(), ViewsResources.IDEA_ICON),
                createSeparatedSubFeature(6, createDisplayViewAction("messages.actions.display", "messageView")),
                createSeparatedSubFeature(25, createDisplayViewAction("log.view.actions.display", "logView")),
                createSeparatedSubFeature(150, new DisplayAboutViewAction(core), ViewsResources.MAIL_ICON)
        );
    }
}