package org.jtheque.core.managers.lifecycle.phases;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.event.EventLevel;
import org.jtheque.core.managers.event.EventLog;
import org.jtheque.core.managers.event.IEventManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;

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
 * The first phase of the JTheque life cycle.
 *
 * @author Baptiste Wicht
 */
public final class FirstPhase implements LifeCyclePhase {
    @Override
    public void run() {
        Managers.preInitManagers();

        Managers.getManager(ILanguageManager.class).addInternationalizable((Internationalizable) Managers.getCore().getLifeCycleManager());

        Managers.getManager(IViewManager.class).getSplashManager().displaySplashScreen();

        Managers.getManager(IEventManager.class).addEventLog("JTheque Core", new EventLog(EventLevel.INFO, "User", "events.start"));

        Managers.getManager(IModuleManager.class).prePlugModules();

        Managers.initManagers();

        Managers.getManager(IModuleManager.class).plugModules();

        if (Managers.getManager(IModuleManager.class).isCollectionModule()) {
            Managers.getManager(IViewManager.class).execute(new DisplayCollectionViewTask());
        } else {
            Managers.getCore().getLifeCycleManager().launchNextPhase();
        }
    }

    private static final class DisplayCollectionViewTask extends SimpleTask {
        @Override
        public void run() {
            Managers.getManager(IViewManager.class).getSplashManager().closeSplashScreen();
            Managers.getManager(IViewManager.class).displayCollectionView();
        }
    }
}
