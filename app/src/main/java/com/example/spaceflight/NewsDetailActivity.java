package com.example.spaceflight;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDetailActivity extends AppCompatActivity {
	
	private int articleId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		
		articleId = getIntent().getIntExtra("articleId", -1);
		if (articleId == -1) {
			Toast.makeText(this, "Invalid article ID", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		fetchArticleDetails();
	}
	
	private void fetchArticleDetails() {
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.spaceflightnewsapi.net/v4/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
		
		SpaceflightApi api = retrofit.create(SpaceflightApi.class);
		
		api.getArticleById(articleId).enqueue(new Callback<NewsArticle>() {
			@Override
			public void onResponse(Call<NewsArticle> call, Response<NewsArticle> response) {
				if (response.isSuccessful() && response.body() != null) {
					showArticleDetails(response.body());
				} else {
					Toast.makeText(NewsDetailActivity.this, "Failed to load article details", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFailure(Call<NewsArticle> call, Throwable t) {
				Toast.makeText(NewsDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void showArticleDetails(NewsArticle article) {
		TextView titleTextView = findViewById(R.id.detailTitleTextView);
		TextView summaryTextView = findViewById(R.id.detailSummaryTextView);
		ImageView imageView = findViewById(R.id.detailImageView);
		Button readMoreButton = findViewById(R.id.readMoreButton);
		
		titleTextView.setText(article.getTitle());
		summaryTextView.setText(article.getSummary());
		
		if (article.getImageUrl() != null) {
			Glide.with(this).load(article.getImageUrl()).into(imageView);
		}
		
		readMoreButton.setOnClickListener(v -> {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
			startActivity(intent);
		});
	}
}