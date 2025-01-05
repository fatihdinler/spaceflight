package com.example.spaceflight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListFragment extends Fragment {
	private RecyclerView recyclerView;
	private NewsAdapter adapter;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_list, container, false);
		recyclerView = view.findViewById(R.id.recyclerViewNews);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		fetchNews();
		return view;
	}
	
	private void fetchNews() {
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.spaceflightnewsapi.net/v4/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
		
		SpaceflightApi api = retrofit.create(SpaceflightApi.class);
		
		api.getNewsArticles(10).enqueue(new Callback<ApiResponse>() {
			@Override
			public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
				if (response.isSuccessful() && response.body() != null) {
					List<NewsArticle> articles = response.body().getResults();
					adapter = new NewsAdapter(articles, article -> {
						// Haber detayına yönlendirme
						Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
						intent.putExtra("articleId", article.getId());
						startActivity(intent);
					});
					recyclerView.setAdapter(adapter);
				} else {
					Log.d("NewsListFragment", "Failed to load news.");
				}
			}
			
			@Override
			public void onFailure(Call<ApiResponse> call, Throwable t) {
				Log.e("NewsListFragment", "Error fetching news", t);
			}
		});
	}
}
