package com.davidwang.githubsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Repository> repositoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);     // for better performance

        // Create a Retrofit instance
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/search")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RetrieveData service = RetrofitClientInstance.getRetrofitInstance().create(RetrieveData.class);
                Call<List<Repository>> call = service.getRepositories(query);
                call.enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                        generateRepoList(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {

                    }
                });
                return true;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });


        // Use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify the adapter
        myAdapter = new RepositoryAdapter(repositoryList);
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

    // Generates list of repositories
    // Called whenever the search query changes
    private void generateRepoList(List<Repository> repositoryList) {
        recyclerView = findViewById(R.id.recyclerView);
        myAdapter = new RepositoryAdapter(repositoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
    }
}
