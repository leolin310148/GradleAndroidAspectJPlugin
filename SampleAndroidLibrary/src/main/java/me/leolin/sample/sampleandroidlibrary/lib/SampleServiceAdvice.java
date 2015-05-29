package me.leolin.sample.sampleandroidlibrary.lib;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author leolin
 */
@Aspect
public class SampleServiceAdvice {

    private static final String LOG_TAG = SampleServiceAdvice.class.getSimpleName();

    @Before("execution(* me.leolin.sample.sampleandroidlibrary.lib.SampleService.*(..))")
    public void beforeSampleServiceAllMethods(JoinPoint joinPoint) {
        Log.d(LOG_TAG, "Aspect before:" + joinPoint.getSignature().toShortString());
    }
}
