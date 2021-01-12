package com.e.mvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.e.mvvm.R;
import com.e.mvvm.databinding.ItemContainerTvShowBinding;
import com.e.mvvm.listeners.TVShowListener;
import com.e.mvvm.models.TVShow;
import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowHolder> {

  private List<TVShow> tvShowList;
  private LayoutInflater layoutInflater;
  private TVShowListener tvShowListener;

  public TVShowsAdapter(List<TVShow> tvShowList , TVShowListener tvShowListener) {
    this.tvShowList = tvShowList;
    this.tvShowListener = tvShowListener;
  }

  @NonNull
  @Override
  public TVShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (layoutInflater == null) {
      layoutInflater = LayoutInflater.from(parent.getContext());
    }
    ItemContainerTvShowBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show, parent, false);
    return new TVShowHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull TVShowHolder holder, int position) {
    holder.bindTVShow(tvShowList.get(position));
  }

  @Override
  public int getItemCount() {
    return tvShowList.size();
  }

   class TVShowHolder extends RecyclerView.ViewHolder {

    private final ItemContainerTvShowBinding itemContainerTvShowBinding;

    public TVShowHolder(ItemContainerTvShowBinding itemContainerTvShowBinding) {
      super(itemContainerTvShowBinding.getRoot());
      this.itemContainerTvShowBinding = itemContainerTvShowBinding;
    }

    public void bindTVShow(TVShow tvShow) {
      itemContainerTvShowBinding.setTvShow(tvShow);
      itemContainerTvShowBinding.executePendingBindings();
      itemContainerTvShowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          tvShowListener.onTVShowClicked(tvShow);
        }
      });
    }

  }
}
