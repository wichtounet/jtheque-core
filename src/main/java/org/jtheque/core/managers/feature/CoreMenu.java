package org.jtheque.core.managers.feature;

import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.view.impl.actions.about.DisplayAboutViewAction;
import org.jtheque.core.managers.view.impl.actions.author.AcInformOfABug;
import org.jtheque.core.managers.view.impl.actions.author.AcOpenHelp;
import org.jtheque.core.managers.view.impl.actions.author.AcProposeImprovement;
import org.jtheque.core.managers.view.impl.actions.backup.AcBackup;
import org.jtheque.core.managers.view.impl.actions.backup.AcRestore;
import org.jtheque.core.managers.view.impl.actions.core.ExitAction;
import org.jtheque.core.managers.view.impl.actions.undo.RedoAction;
import org.jtheque.core.managers.view.impl.actions.undo.UndoAction;

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
    @Override
    protected List<Feature> getFileMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(200, new AcBackup()),
                createSubFeature(201, new AcRestore()),
                createSeparatedSubFeature(1000, new ExitAction())
        );
    }

    @Override
    protected List<Feature> getEditMenuSubFeatures(){
        return features(
                createSubFeature(1, new UndoAction()),
                createSubFeature(2, new RedoAction())
        );
    }

    @Override
    protected List<Feature> getAdvancedMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(500, createDisplayViewAction("config.actions.display", "configView"), Core.IMAGES_BASE_NAME, "options"),
                createSeparatedSubFeature(750, createDisplayViewAction("modules.actions.manage", "moduleView"), Core.IMAGES_BASE_NAME, "update")
        );
    }

    @Override
    protected List<Feature> getHelpMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(1, new AcOpenHelp(), Core.IMAGES_BASE_NAME, "help"),
                createSeparatedSubFeature(2, new AcInformOfABug(), Core.IMAGES_BASE_NAME, "mail"),
                createSeparatedSubFeature(4, new AcProposeImprovement(), Core.IMAGES_BASE_NAME, "idea"),
                createSeparatedSubFeature(6, createDisplayViewAction("messages.actions.display", "messageView")),
                createSeparatedSubFeature(25, createDisplayViewAction("log.view.actions.display", "logView")),
                createSeparatedSubFeature(150, new DisplayAboutViewAction(), Core.IMAGES_BASE_NAME, "about")
        );
    }
}