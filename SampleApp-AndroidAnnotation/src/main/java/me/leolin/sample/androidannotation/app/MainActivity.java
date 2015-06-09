package me.leolin.sample.androidannotation.app;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.widget.Toast;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @SystemService
    NotificationManager notificationManager;

    @SystemService
    WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        someBackgroundWork();
    }


    @Background
    void someBackgroundWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        doUi(getMessage());
    }

    @UiThread
    void doUi(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getMessage() {
        return "Hello world";
    }
}
