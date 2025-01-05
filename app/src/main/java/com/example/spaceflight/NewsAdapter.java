package com.example.spaceflight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
	
	private final List<NewsArticle> articles;
	private final OnNewsClickListener listener;
	
	public interface OnNewsClickListener {
		void onClick(NewsArticle article);
	}
	
	public NewsAdapter(List<NewsArticle> articles, OnNewsClickListener listener) {
		this.articles = articles;
		this.listener = listener;
	}
	
	@NonNull
	@Override
	public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
		return new NewsViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
		NewsArticle article = articles.get(position);
		holder.titleTextView.setText(article.getTitle());
		holder.summaryTextView.setText(article.getSummary());
		
		if (article.getImageUrl() != null && !article.getImageUrl().isEmpty()) {
			Glide.with(holder.itemView.getContext())
				.load(article.getImageUrl())
				.into(holder.newsImageView);
		}
		
		holder.itemView.setOnClickListener(v -> listener.onClick(article));
	}
	
	@Override
	public int getItemCount() {
		return articles.size();
	}
	
	static class NewsViewHolder extends RecyclerView.ViewHolder {
		TextView titleTextView, summaryTextView;
		ImageView newsImageView;
		
		public NewsViewHolder(@NonNull View itemView) {
			super(itemView);
			titleTextView = itemView.findViewById(R.id.titleTextView);
			summaryTextView = itemView.findViewById(R.id.summaryTextView);
			newsImageView = itemView.findViewById(R.id.newsImageView);
		}
	}
}