package com.e.mvvm.response;

import com.e.mvvm.models.TVShowDetails;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailsResponse {

  @SerializedName("tvShow")
  private TVShowDetails tvShowDetails;


  public TVShowDetails getTvShowDetails() {
    return tvShowDetails;
  }
}
