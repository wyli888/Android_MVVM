package com.e.mvvm.listeners;

import com.e.mvvm.models.TVShow;

public interface WatchlistListener {

  void onTVShowClicked(TVShow tvShow);

  void removeTVShowFromWatchList(TVShow tvShow, int position);
}
