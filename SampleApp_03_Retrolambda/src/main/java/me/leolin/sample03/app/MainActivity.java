package me.leolin.sample03.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setOnClickListener(v -> Toast.makeText(this, "World hello", Toast.LENGTH_SHORT).show());
    }
}
