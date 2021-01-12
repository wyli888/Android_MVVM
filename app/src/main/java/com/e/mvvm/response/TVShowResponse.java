package com.e.mvvm.response;

import com.e.mvvm.models.TVShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowResponse {

  @SerializedName("page")
  private int page;

  @SerializedName("pages")
  private int totalPages;

  @SerializedName("tv_shows")
  private List<TVShow> tvShows;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public List<TVShow> getTvShows() {
    return tvShows;
  }

  public void setTvShows(List<TVShow> tvShows) {
    this.tvShows = tvShows;
  }

  @Override
  public String toString() {
    return "TVShowResponse{" +
      "page=" + page +
      ", totalPages=" + totalPages +
      ", tvShows=" + tvShows +
      '}';
  }
}
