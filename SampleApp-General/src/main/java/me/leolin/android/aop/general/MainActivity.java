package me.leolin.android.aop.general;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author leolin
 */
public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
