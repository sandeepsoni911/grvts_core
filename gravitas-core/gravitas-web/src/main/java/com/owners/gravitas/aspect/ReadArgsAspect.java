package com.owners.gravitas.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.owners.gravitas.managers.ThreadLocalManager;

/**
 * The Class ReadArgsAspect.
 *
 * @author vishwanathm
 */
@Aspect
@Component
public class ReadArgsAspect {

    /** The Constant EXECUTION. */
    private static final String EXECUTION = "execution(public * *(..)) && @annotation(com.owners.gravitas.annotation.ReadArgs)";

    /**
     * Store request parameters to thread local before the actual API execution
     * starts.
     *
     * @param joinPoint
     *            the join point
     * @throws Throwable
     *             the throwable
     */
    @Around( EXECUTION )
    public Object storeRequestParams( final ProceedingJoinPoint joinPoint ) throws Throwable {
        ThreadLocalManager.getInstance().setRequestParams( Arrays.asList( joinPoint.getArgs() ) );
        Object result = joinPoint.proceed();
        ThreadLocalManager.getInstance().removeRequestParams();
        return result;
    }

}
