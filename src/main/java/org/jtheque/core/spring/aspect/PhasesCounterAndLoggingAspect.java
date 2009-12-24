package org.jtheque.core.spring.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.lifecycle.JThequeCoreTimer;
import org.jtheque.core.managers.log.ILoggingManager;

import java.util.HashMap;
import java.util.Map;

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
 * An AspectJ aspect to log the phases and calc the time needed.
 *
 * @author Baptiste Wicht
 */
@Aspect
public final class PhasesCounterAndLoggingAspect {
    private final Map<String, Long> startTimes = new HashMap<String, Long>(3);

    /**
     * A point cut to the run method of the Phase.
     */
    @Pointcut("execution(* org.jtheque.core.managers.lifecycle.phases.*Phase.run())")
    public void run() { /* Point cut */}

    /**
     * Start the counter before the run method.
     *
     * @param joinPoint The join point of the advised method.
     */
    @Before("run()")
    public void startCounter(JoinPoint joinPoint) {
        String phase = getPhase(joinPoint);

        Logger.getLogger(getClass()).trace("Phase " + phase + " started");

        startTimes.put(phase, System.currentTimeMillis());
    }

    /**
     * Stop the counter after the run method.
     *
     * @param joinPoint The join point of the advised method.
     */
    @After("run()")
    public void stopCounter(JoinPoint joinPoint) {
        String phase = getPhase(joinPoint);

        long time = System.currentTimeMillis() - startTimes.get(phase);

        Managers.getManager(ILoggingManager.class).getLogger(getClass()).trace("Phase {} finished in {}ms", phase, time);

        if ("Third".equals(phase)) {
            JThequeCoreTimer.stop();
        }
    }

    /**
     * Return the phase
     *
     * @param joinPoint The join point of the advised method.
     * @return The phase of the join point.
     */
    private static String getPhase(JoinPoint joinPoint) {
        return joinPoint.getSourceLocation().getWithinType().getSimpleName().replace("Phase", "");
    }
}