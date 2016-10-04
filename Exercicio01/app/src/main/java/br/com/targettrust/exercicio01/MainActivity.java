package br.com.targettrust.exercicio01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String actionSelected = null;
        switch (id) {
            case R.id.action_settings: {
                actionSelected = getString(R.string.action_settings_label);
                break;
            }
            case R.id.action_friends: {
                actionSelected = getString(R.string.action_friends_label);
                break;
            }
            case R.id.action_messages: {
                actionSelected = getString(R.string.action_messages_label);
                break;
            }
            case R.id.action_search: {
                actionSelected = getString(R.string.action_search_label);
                break;
            }
            case R.id.action_content: {
                actionSelected = getString(R.string.action_content_label);
                break;
            }
            default:
                actionSelected = "Unknown action";
        }

        Toast.makeText(getApplicationContext(), actionSelected + " selected.", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}
