package com.davidwang.githubsearch;

import java.util.List;

import retrofit2.http.GET;

import io.reactivex.Observable;
import retrofit2.http.Query;

public interface RetrieveData {
    @GET("/search/repositories")
    Observable<List<Repository>> getRepositories(@Query("name") String name);
}
