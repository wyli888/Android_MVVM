package com.e.mvvm.network;

import com.e.mvvm.models.TVShowDetails;
import com.e.mvvm.response.TVShowDetailsResponse;
import com.e.mvvm.response.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

  @GET("most-popular")
  Call<TVShowResponse> getMostPopularTvShows(@Query("page") int page);

  @GET("show-details")
  Call<TVShowDetailsResponse> getTVShowDetails(@Query("q") String tvShowId);

}
