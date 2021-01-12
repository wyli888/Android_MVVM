package com.e.mvvm.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.mvvm.network.ApiClient;
import com.e.mvvm.network.ApiService;
import com.e.mvvm.response.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {
  private final ApiService apiService;

  public MostPopularTVShowsRepository() {
    apiService = ApiClient.getRetrofit().create(ApiService.class);
  }


  public MutableLiveData<TVShowResponse> getMostPopularTVShows(int page) {
    MutableLiveData<TVShowResponse> data = new MutableLiveData<>();

    apiService.getMostPopularTvShows(page).enqueue(new Callback<TVShowResponse>() {
      @Override
      public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
        Log.i("=======onResponse======","================");
        data.setValue(response.body());
      }

      @Override
      public void onFailure(Call<TVShowResponse> call, Throwable t) {
        Log.i("=======onFailure======","================");
        data.setValue(null);
      }
    });
    return data;
  }
}
