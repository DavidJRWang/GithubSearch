package com.davidwang.githubsearch;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.github.com/";

    private TextInputLayout textInputView;
    private Button searchButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private List<Repository> repositoryList;

    private CompositeDisposable compositeDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable = new CompositeDisposable();
        repositoryList = new ArrayList<>();
        initRecyclerView(); // Set up RecyclerView
    }

    // HELPER FUNCTIONS
    // Initialize recyclerView
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        myAdapter = new RepositoryAdapter(repositoryList);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void search(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String query = editText.getText().toString();
        loadJSON(query);
    }

    // Called every time a search is made
    // Remakes list of repositories
    private void loadJSON(String query) {
        RetrieveData requestInterface = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RetrieveData.class);

        compositeDisposable.add(requestInterface.getRepositories("4da8a01b33b9a31934210efb4d729697a6303c34", query) // Putting token in code is probably bad practice
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<Repository> list) {
        repositoryList = list;
        generateRepoList();
    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    // Generates list of repositories
    // Called whenever the search query changes
    private void generateRepoList() {
        recyclerView = findViewById(R.id.recyclerView);
        myAdapter = new RepositoryAdapter(repositoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
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
                startActivity(new Intent(this, FavoritesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
