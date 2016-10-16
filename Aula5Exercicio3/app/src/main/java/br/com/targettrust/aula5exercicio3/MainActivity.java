package br.com.targettrust.aula5exercicio3;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static final int CONTACT_LOADER_ID = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This app need permission to read device contacts.
        // Obs: Go on device settings and grant permission to read contacts to this app!
        SimpleCursorAdapter simpleCursorAdapter = setupCursorAdapter();
        ListView listView = (ListView) findViewById(R.id.list_item_contacts);
        listView.setAdapter(simpleCursorAdapter);
        ContactsLoader contactsLoader = new ContactsLoader(this, simpleCursorAdapter);
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(), contactsLoader);
    }

    // Create simple cursor simpleCursorAdapter to connect the cursor dataset we load with a ListView
    private SimpleCursorAdapter setupCursorAdapter() {

        // Column data from cursor to bind views from
        String[] uiBindFrom = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_URI};

        // View IDs which will have the respective column data inserted
        int[] uiBindTo = {android.R.id.text1, android.R.id.text2};

        // Create the simple cursor simpleCursorAdapter to use for our list
        // specifying the template to inflate (item_contact),
        return new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                null, uiBindFrom, uiBindTo, 0);
    }


}