package com.davidwang.githubsearch;

import java.util.List;

import retrofit2.http.GET;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrieveData {
    @GET("/search/repositories")
    Observable<List<Repository>> getRepositories(@Header("Authorization") String authKey, @Query("name") String name);
}
