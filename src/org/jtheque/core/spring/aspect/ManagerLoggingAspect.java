package org.jtheque.core.spring.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

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
 * An AspectJ aspect to logging the managers' init, preInit and close methods.
 *
 * @author Baptiste Wicht
 */
@Aspect
public final class ManagerLoggingAspect {
    /**
     * Log the init method of the managers.
     *
     * @param joinPoint The join point of the advised method.
     * @return null
     * @throws Throwable Throw if the init method throws an exception.
     */
    @Around("execution(* org.jtheque.core.managers.*Manager.init())")
    public Object logInit(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger.getLogger(getClass()).trace(joinPoint.getSourceLocation().getWithinType().getSimpleName() + " init started");

        Object retVal = joinPoint.proceed();

        Logger.getLogger(getClass()).trace(joinPoint.getSourceLocation().getWithinType().getSimpleName() + " init finished");

        return retVal;
    }

    /**
     * Log the preInit method of the managers.
     *
     * @param joinPoint The join point of the advised method.
     * @return null
     * @throws Throwable Throw if the preInit method throws an exception.
     */
    @Around("execution(* org.jtheque.core.managers.*Manager.preInit())")
    public Object logPreInit(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger.getLogger(getClass()).trace(joinPoint.getSourceLocation().getWithinType().getSimpleName() + " pre-init started");

        Object retVal = joinPoint.proceed();

        Logger.getLogger(getClass()).trace(joinPoint.getSourceLocation().getWithinType().getSimpleName() + " pre-init finished");

        return retVal;
    }

    /**
     * Log the close method of the managers.
     *
     * @param joinPoint The join point of the advised method.
     * @return null
     * @throws Throwable Throw if the close method throws an exception.
     */
    @Around("execution(* org.jtheque.core.managers.*Manager.close())")
    public Object logClose(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger.getLogger(getClass()).trace(joinPoint.getSourceLocation().getWithinType().getSimpleName() + " close started");

        Object retVal = joinPoint.proceed();

        Logger.getLogger(getClass()).trace(joinPoint.getSourceLocation().getWithinType().getSimpleName() + " close finished");

        return retVal;
    }
}