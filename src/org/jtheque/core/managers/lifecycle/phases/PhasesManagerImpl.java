package org.jtheque.core.managers.lifecycle.phases;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

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
 * A phases manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class PhasesManagerImpl implements IPhasesManager {
    private final Queue<LifeCyclePhase> phases;

    /**
     * Construct a new PhasesManagerImpl.
     *
     * @param phases The phases to manage.
     */
    public PhasesManagerImpl(Collection<LifeCyclePhase> phases) {
        super();

        this.phases = new LinkedBlockingQueue<LifeCyclePhase>(phases);
    }

    @Override
    public void launchNextPhase() {
        if (phases.isEmpty()) {
            throw new IllegalStateException("All the phases are launched !");
        }

        phases.poll().run();
    }

    @Override
    public boolean isDone() {
        return phases.isEmpty();
    }
}
