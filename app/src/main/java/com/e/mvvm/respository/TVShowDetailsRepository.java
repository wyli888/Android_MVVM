package com.e.mvvm.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.mvvm.network.ApiClient;
import com.e.mvvm.network.ApiService;
import com.e.mvvm.response.TVShowDetailsResponse;
import com.e.mvvm.response.TVShowResponse;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

  private ApiService apiService;

  public TVShowDetailsRepository() {
    apiService = ApiClient.getRetrofit().create(ApiService.class);
  }

  public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
    MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();

    apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowDetailsResponse>() {
      @Override
      public void onResponse(@NonNull Call<TVShowDetailsResponse> call, @NonNull Response<TVShowDetailsResponse> response) {
        data.setValue(response.body());
      }

      @Override
      public void onFailure(@NonNull Call<TVShowDetailsResponse> call,@NonNull Throwable t) {
        data.setValue(null);
      }
    });
    return data;
  }


}
