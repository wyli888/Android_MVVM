package com.e.mvvm.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableMap;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.e.mvvm.R;
import com.e.mvvm.adapters.WatchlistAdapter;
import com.e.mvvm.databinding.ActivityWatchlistBinding;
import com.e.mvvm.listeners.WatchlistListener;
import com.e.mvvm.models.TVShow;
import com.e.mvvm.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class WatchlistActivity extends AppCompatActivity implements WatchlistListener {

  private ActivityWatchlistBinding binding;
  private WatchlistViewModel viewModel;
  private WatchlistAdapter watchlistAdapter;
  private List<TVShow> watchList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);
    doInitialization();
  }

  private void doInitialization() {
    viewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
    binding.imageBack.setOnClickListener(v -> onBackPressed());
    watchList = new ArrayList<>();
  }


  @Override
  protected void onResume() {
    super.onResume();
    loadWatchlist();
  }

  private void loadWatchlist() {
    binding.setIsLoading(true);
    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
//      .observeOn(AndroidSchedulers.mainThread())
//      .subscribe(tvShows -> {
//        binding.setIsLoading(false);
//        if (watchList.size() > 0) {
//          watchList.clear();
//        }
//        watchList.addAll(tvShows);
//        watchlistAdapter = new WatchlistAdapter(watchList, this);
//        binding.recycle.setAdapter(watchlistAdapter);
//        compositeDisposable.dispose();
//      })
//    );
  }


  @Override
  public void onTVShowClicked(TVShow tvShow) {
    Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
    intent.putExtra("tvShow", tvShow);
    startActivity(intent);
  }

  @Override
  public void removeTVShowFromWatchList(TVShow tvShow, int position) {

//    CompositeDisposable delete = new CompositeDisposable();
//    delete.add(viewModel.removeFromWatchList(tvShow)
//      .subscribeOn(Schedulers.computation())
//      .observeOn(AndroidSchedulers.mainThread())
//      .subscribe(() -> {
//          watchList.remove(position);
//          watchlistAdapter.notifyItemRemoved(position);
//          watchlistAdapter.notifyItemRangeRemoved(position,watchlistAdapter.getItemCount());
//          delete.dispose();
//      })
//    );
  }
}