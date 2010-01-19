package org.jtheque.core.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;

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
	 *
	 * @return The return value of the joint point method.
	 *
	 * @throws Throwable Throw if the init method throws an exception.
	 */
	@Around("execution(* org.jtheque.core.managers.*Manager.init())")
	public Object logInit(ProceedingJoinPoint joinPoint) throws Throwable{
		return trace(joinPoint, "init");
	}

	/**
	 * Log the preInit method of the managers.
	 *
	 * @param joinPoint The join point of the advised method.
	 *
	 * @return The return value of the joint point method.
	 *
	 * @throws Throwable Throw if the preInit method throws an exception.
	 */
	@Around("execution(* org.jtheque.core.managers.*Manager.preInit())")
	public Object logPreInit(ProceedingJoinPoint joinPoint) throws Throwable{
		return trace(joinPoint, "pre-init");
	}

	/**
	 * Log the close method of the managers.
	 *
	 * @param joinPoint The join point of the advised method.
	 *
	 * @return The return value of the joint point method.
	 *
	 * @throws Throwable Throw if the close method throws an exception.
	 */
	@Around("execution(* org.jtheque.core.managers.*Manager.close())")
	public Object logClose(ProceedingJoinPoint joinPoint) throws Throwable{
		return trace(joinPoint, "close");
	}

	/**
	 * Trace the join point method start and finish
	 *
	 * @param joinPoint The join point to trace.
	 * @param phase The current manager phase.
	 *
	 * @return The return value of the joint point method.
	 *
	 * @throws Throwable Throw if the traced method throws an exception.
	 */
	private Object trace(ProceedingJoinPoint joinPoint, String phase) throws Throwable{
		LoggerFactory.getLogger(getClass()).trace(
				"{} {} started",
				joinPoint.getSourceLocation().getWithinType().getSimpleName(), phase);

		Object retVal = joinPoint.proceed();

		LoggerFactory.getLogger(getClass()).trace(
				"{} {} finished",
				joinPoint.getSourceLocation().getWithinType().getSimpleName(), phase);

		return retVal;
	}
}