package com.davidwang.githubsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Populates overflow menu with items
    // There is only one item: 'Favorites'
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    // Handle item selection
    // On click, 'Favorites' creates a new Favorites activity
    //  which shows the repos that were selected as favorites
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                //newGame();    //go to "favorites" activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
