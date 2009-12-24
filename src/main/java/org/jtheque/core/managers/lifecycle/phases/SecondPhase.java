package org.jtheque.core.managers.lifecycle.phases;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.message.IMessageManager;
import org.jtheque.core.managers.update.IUpdateManager;
import org.jtheque.core.managers.view.able.IViewManager;

/**
 * The second phase of the JTheque life cycle.
 *
 * @author Baptiste Wicht
 */
public final class SecondPhase implements LifeCyclePhase {
    @Override
    public void run() {
        Managers.getCore().getLifeCycleManager().initTitle();

        Managers.getManager(IViewManager.class).getSplashManager().closeSplashScreen();
        Managers.getManager(IViewManager.class).getSplashManager().fillMainView();

        Managers.getManager(IErrorManager.class).displayErrors();

        Managers.getManager(IMessageManager.class).loadMessages();
        Managers.getManager(IMessageManager.class).displayIfNeeded();

        Managers.getManager(IUpdateManager.class).verifyingUpdate();
    }
}
