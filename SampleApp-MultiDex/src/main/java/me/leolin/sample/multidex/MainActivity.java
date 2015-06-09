package me.leolin.sample.multidex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, getMessage(), Toast.LENGTH_SHORT).show();
    }


    private String getMessage() {
        return "Hello world";
    }
}
