package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author leolin
 */
@Aspect
public class MyServiceAdvice {

    @Around("execution(* me.leolin.sample02.lib.MyService.getHelloString(..))")
    public Object afterOnCreate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return "Hello AspectJ!";
    }
}
