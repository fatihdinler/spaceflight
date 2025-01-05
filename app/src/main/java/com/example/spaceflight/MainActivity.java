package com.example.spaceflight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
	
	private RecyclerView recyclerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		getNews();
	}
	
	private void getNews() {
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.spaceflightnewsapi.net/v4/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
		
		SpaceflightApi api = retrofit.create(SpaceflightApi.class);
		
		api.getNewsArticles(20).enqueue(new Callback<ApiResponse>() {
			@Override
			public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
				if (response.isSuccessful() && response.body() != null) {
					List<NewsArticle> articles = response.body().getResults();
					NewsAdapter adapter = new NewsAdapter(articles, article -> {
						Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
						intent.putExtra("articleId", article.getId());
						startActivity(intent);
					});
					recyclerView.setAdapter(adapter);
				} else {
					Toast.makeText(MainActivity.this, "Failed to load news", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFailure(Call<ApiResponse> call, Throwable t) {
				Log.e("MainActivity", "Error fetching news", t);
			}
		});
	}
}
