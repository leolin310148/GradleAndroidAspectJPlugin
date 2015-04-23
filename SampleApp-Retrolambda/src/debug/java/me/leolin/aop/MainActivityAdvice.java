package me.leolin.aop;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import me.leolin.android.aop.retrolambda.R;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author leolin
 */
@Aspect
public class MainActivityAdvice {

    @Before("execution(* me.leolin.android.aop.retrolambda.MainActivity.onCreate(..))")
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

    @After("execution(* me.leolin.android.aop.retrolambda.MainActivity.onCreate(..))")
    public void afterMainActivityOnCreate(JoinPoint joinPoint) {
        Activity activity = (Activity) joinPoint.getTarget();
        activity.findViewById(R.id.textView)
                .setOnClickListener(v ->
                        Toast.makeText(activity, "click from aspectJ", Toast.LENGTH_SHORT).show());

    }

}
