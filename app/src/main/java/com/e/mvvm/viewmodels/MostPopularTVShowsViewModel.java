package com.e.mvvm.viewmodels;


import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.mvvm.BR;
import com.e.mvvm.R;
import com.e.mvvm.action.Action;
import com.e.mvvm.action.SingleLiveEvent;
import com.e.mvvm.activites.TVShowDetailsActivity;
import com.e.mvvm.listeners.TVShowListener;
import com.e.mvvm.models.TVShow;
import com.e.mvvm.network.ApiClient;
import com.e.mvvm.network.ApiService;
import com.e.mvvm.response.TVShowResponse;

import io.reactivex.annotations.NonNull;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MostPopularTVShowsViewModel extends ViewModel {
  //Stores actions for view.
  private SingleLiveEvent<TVShow> mAction = new SingleLiveEvent<>();

  public SingleLiveEvent<TVShow> getAction() {
    return mAction;
  }

  // 绑定的点击事件
  public TVShowListener onClickListener = new TVShowListener() {
    @Override
    public void onTVShowClicked(TVShow tvShow) {
      mAction.setValue(tvShow);
    }
  };

  // 如果变量要在布局文件中使用的话要用MutableLiveData<>来包裹才能实现观察
  public final ObservableList<TVShow> items = new ObservableArrayList<>();
  // 这个地方要用Object因为类型不一样  tvShow是TVShow类型 点击事件是TVShowListener类型 如果没有点击事件可以将Object改为TVShow
  public final ItemBinding<Object> itemBinding = ItemBinding.of(BR.tvShow, R.layout.item_container_tv_show)
    .bindExtra(BR.listener, onClickListener);
  private int currentPage = 1;
  private int totalAvailablePages;
  public MutableLiveData<Boolean> loadingMore = new MutableLiveData<>();
  private MutableLiveData<TVShowResponse> responseMutableLiveData;

//  public MostPopularTVShowsViewModel(@androidx.annotation.NonNull Application application) {
//    super(application);
//    this.application = application;
//    responseMutableLiveData = new MutableLiveData<>();
//  }


  public int getTotalAvailablePages() {
    return totalAvailablePages;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public MostPopularTVShowsViewModel() {
    responseMutableLiveData = new MutableLiveData<>();
  }

  public MutableLiveData<TVShowResponse> getResponseMutableLiveData() {
    return responseMutableLiveData;
  }

  public void makeApiCall() {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
    apiService.getMostPopularTvShows(currentPage).enqueue(new Callback<TVShowResponse>() {
      @Override
      public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
        Log.i("=======onResponse======", "================");
        responseMutableLiveData.setValue(response.body());
        items.addAll(responseMutableLiveData.getValue().getTvShows());
        totalAvailablePages = responseMutableLiveData.getValue().getTotalPages();
        loadingMore.setValue(false);
      }

      @Override
      public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
        Log.i("=======onFailure======", "================");
        responseMutableLiveData.setValue(null);
      }
    });
  }

}
