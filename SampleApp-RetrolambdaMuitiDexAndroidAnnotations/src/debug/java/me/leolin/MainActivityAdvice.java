package me.leolin;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author leolin
 */
@Aspect
public class MainActivityAdvice {

    @Around("execution(* me.leolin.sample.combine.app.MainActivity.getMessage())")
    public Object aroundMainActivityGetMessage(ProceedingJoinPoint proceedingJoinPoint) {
        return "Aspect!";
    }
}
