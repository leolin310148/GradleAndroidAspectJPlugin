package me.leolin.aop;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import me.leolin.android.aop.general.R;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author leolin
 */
@Aspect
public class MainActivityAdvice {

    @Before("execution(* me.leolin.android.aop.general.MainActivity.onCreate(..))")
    public void beforeMainActivityOnCreate(JoinPoint joinPoint) {
        Activity activity = (Activity) joinPoint.getTarget();
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(9999,
                new NotificationCompat.Builder(activity)
                        .setTicker("Hello AspectJ")
                        .setContentTitle("Notification from aspectJ")
                        .setContentText("Only in debug")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .build()
        );
    }

}
