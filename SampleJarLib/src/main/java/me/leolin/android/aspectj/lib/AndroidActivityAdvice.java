package me.leolin.android.aspectj.lib;

import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author leolin
 */
@Aspect
public class AndroidActivityAdvice {

    private static final String LOG_TAG = AndroidActivityAdvice.class.getSimpleName();

    @Around("execution(* android.app.Activity.*(..))")
    public Object aroundEveryThing(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTimeMillis = System.currentTimeMillis();
        Log.v(LOG_TAG, "begin:" + proceedingJoinPoint.getSignature().toShortString());
        Object result = proceedingJoinPoint.proceed();
        long performanceTime = System.currentTimeMillis() - startTimeMillis;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(performanceTime).append("ms,")
                .append(proceedingJoinPoint.getSignature().toShortString());
        Log.v(LOG_TAG, stringBuilder.toString());

        return result;
    }
}
