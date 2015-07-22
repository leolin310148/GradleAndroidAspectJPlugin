package me.leolin.sample05.app;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @SystemService
    NotificationManager notificationManager;

    @SystemService
    WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
