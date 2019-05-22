package com.davidwang.githubsearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrieveData {
    @GET("/repositories?q={name}")
    Call<List<Repository>> getRepositories(@Path("name") String name);
}
