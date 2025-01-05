package com.example.spaceflight;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpaceflightApi {
	@GET("articles/")
	Call<ApiResponse> getNewsArticles(@Query("limit") int limit);
	
	@GET("articles/{id}/")
	Call<NewsArticle> getArticleById(@Path("id") int id);
}