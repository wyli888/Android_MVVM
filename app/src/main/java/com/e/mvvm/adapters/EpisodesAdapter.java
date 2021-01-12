package com.e.mvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.mvvm.R;
import com.e.mvvm.databinding.ItemContainerEpisodeBinding;
import com.e.mvvm.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>{

  private List<Episode> episodeList;
  private LayoutInflater layoutInflater;

  public EpisodesAdapter(List<Episode> episodeList) {
    this.episodeList = episodeList;
  }



  @NonNull
  @Override
  public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (layoutInflater == null){
      layoutInflater = LayoutInflater.from(parent.getContext());
    }
    ItemContainerEpisodeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_episode,parent,false);
    return new EpisodeViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
    holder.bindEpisode(episodeList.get(position));
  }

  @Override
  public int getItemCount() {
    return episodeList.size();
  }

  static class EpisodeViewHolder extends RecyclerView.ViewHolder{
    private ItemContainerEpisodeBinding binding;


    public EpisodeViewHolder(ItemContainerEpisodeBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bindEpisode(Episode episode){
      String title = "S";
      String season = episode.getSeason();
      if (season.length() == 1){
        season = "0".concat(season);
      }
      String episodeNumber = episode.getEpisode();
      if (episodeNumber.length() == 1){
        episodeNumber = "0".concat(episodeNumber);
      }
      episodeNumber = "E".concat(episodeNumber);
      title = title.concat(season).concat(episodeNumber);
      binding.setTitle(title);
      binding.setName(episode.getName());
      binding.setAirDate(episode.getAirDate());
    }
  }




}
