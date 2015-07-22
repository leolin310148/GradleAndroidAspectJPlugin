package me.leolin.sample02.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import me.leolin.sample02.lib.MyService;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyService myService = new MyService();

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(myService.getHelloString());
    }

}