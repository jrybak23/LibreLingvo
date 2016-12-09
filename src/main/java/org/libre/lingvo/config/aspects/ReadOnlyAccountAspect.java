package org.libre.lingvo.config.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.libre.lingvo.utils.ReadOnlyAccountUtil;

/**
 * Created by igorek2312 on 09.12.16.
 */
@Aspect
public class ReadOnlyAccountAspect {
    @Before("execution (* org.libre.lingvo.services.*.*(..)) && @annotation(org.libre.lingvo.config.aspects.annotaions.NotForReadOnly)")
    public void checkIfReadOnly(JoinPoint joinPoint) {
        ReadOnlyAccountUtil.throwIfReadOnly();
    }
}
