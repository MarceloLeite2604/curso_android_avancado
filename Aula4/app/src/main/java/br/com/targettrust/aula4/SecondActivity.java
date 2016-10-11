package br.com.targettrust.aula4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String name = getIntent().getExtras().getString(EXTRA_NAME);

        if (!name.isEmpty()) {
            TextView textViewName = (TextView) findViewById(R.id.textview_name);
            textViewName.setText("Hello, " + name + "!");
        }
    }
}
