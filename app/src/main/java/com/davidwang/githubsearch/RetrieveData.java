package com.davidwang.githubsearch;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

import io.reactivex.Observable;

public interface RetrieveData {
    @GET("/search/repositories?q={name}")
    Observable<List<Repository>> getRepositories(@Path("name") String name);
}
