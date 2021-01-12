package com.e.mvvm.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableMap;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.e.mvvm.R;
import com.e.mvvm.action.Action;
import com.e.mvvm.adapters.TVShowsAdapter;
import com.e.mvvm.databinding.ActivityMainBinding;
import com.e.mvvm.listeners.TVShowListener;
import com.e.mvvm.models.TVShow;
import com.e.mvvm.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import static com.e.mvvm.BR.tvShow;

public class MainActivity extends AppCompatActivity {

  private MostPopularTVShowsViewModel mostPopularTVShowsViewModel;
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    doInitialization();
    initListener();
  }

  // 初始化
  private void doInitialization() {
    binding.tvShowRecycle.setHasFixedSize(true);
    mostPopularTVShowsViewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
    mostPopularTVShowsViewModel.makeApiCall();
    binding.setPopularViewModel(mostPopularTVShowsViewModel);
    binding.setLifecycleOwner(this);
  }

  // 监听
  public void initListener() {
    binding.tvShowRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!binding.tvShowRecycle.canScrollVertically(1)) {
          if (mostPopularTVShowsViewModel.getCurrentPage() <= mostPopularTVShowsViewModel.getTotalAvailablePages()) {
            mostPopularTVShowsViewModel.setCurrentPage(mostPopularTVShowsViewModel.getCurrentPage() + 1);
            mostPopularTVShowsViewModel.loadingMore.setValue(true);
            mostPopularTVShowsViewModel.makeApiCall();
          }
        }
      }
    });
    binding.imageWatchList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), WatchlistActivity.class));
      }
    });
    mostPopularTVShowsViewModel.getAction().observe(this, new Observer<TVShow>() {
      @Override
      public void onChanged(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
      }
    });
  }

}