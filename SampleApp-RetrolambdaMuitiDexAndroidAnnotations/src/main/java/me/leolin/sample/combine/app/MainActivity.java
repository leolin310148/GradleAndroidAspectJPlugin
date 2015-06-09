package me.leolin.sample.combine.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        someBackgroundWork();

        new Handler().post(() -> {
            Log.d(LOG_TAG, ":\n\n" + getMessage() + "\n\n");
        });
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
