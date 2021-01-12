package com.e.mvvm.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.e.mvvm.database.TVShowDatabase;
import com.e.mvvm.models.TVShow;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class WatchlistViewModel extends AndroidViewModel {

  private TVShowDatabase tvShowDatabase;

  public WatchlistViewModel(@NonNull Application application){
    super(application);
    tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
  }

  public Flowable<List<TVShow>> loadWatchlist(){
    return tvShowDatabase.tvShowDao().getWatchList();
  }

  public Completable removeFromWatchList(TVShow tvShow){
    return  tvShowDatabase.tvShowDao().deleteFromWatchList(tvShow);
  }

}
