package com.e.mvvm.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.mvvm.R;
import com.e.mvvm.adapters.EpisodesAdapter;
import com.e.mvvm.adapters.ImageSliderAdapter;
import com.e.mvvm.databinding.ActivityTVShowDetailsBinding;
import com.e.mvvm.databinding.LayoutEpisodesBottomSheetBinding;
import com.e.mvvm.models.TVShow;
import com.e.mvvm.response.TVShowDetailsResponse;
import com.e.mvvm.viewmodels.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class TVShowDetailsActivity extends AppCompatActivity {

  private ActivityTVShowDetailsBinding binding;
  private TVShowDetailsViewModel tvShowDetailsViewModel;
  private BottomSheetDialog dialog;
  private LayoutEpisodesBottomSheetBinding bottomSheetBinding;
  private TVShow tvShow;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
    doInitialization();
  }


  private void doInitialization() {
    tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
    // 从MainActivity中获取传递过来的数据
    tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
    binding.setViewModel(tvShowDetailsViewModel);
    binding.setTvShow(tvShow);
    binding.setLifecycleOwner(this);
    // 从调用viewModel中方法获取数据源
    tvShowDetailsViewModel.getDetails(String.valueOf(tvShow.getId()));
    // 观察viewModel中的data数据源 来改变ui
    tvShowDetailsViewModel.data.observe(this, new Observer<TVShowDetailsResponse>() {
      @Override
      public void onChanged(TVShowDetailsResponse tvShowDetailsResponse) {
        if (tvShowDetailsResponse != null){
          loadImageSliders(tvShowDetailsResponse.getTvShowDetails().getPictures());
          initListener(tvShowDetailsResponse);
        }
      }
    });
  }

  // 监听
  private void initListener(TVShowDetailsResponse response){
    binding.imageBack.setOnClickListener(v -> onBackPressed());
    binding.readMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (binding.readMore.getText().toString().equals("Read More")){
          binding.description.setMaxLines(Integer.MAX_VALUE);
          binding.description.setEllipsize(null);
          binding.readMore.setText(R.string.read_less);
        }else {
          binding.description.setMaxLines(4);
          binding.description.setEllipsize(TextUtils.TruncateAt.END);
          binding.readMore.setText(R.string.read_more);
        }
      }
    });
    binding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(response.getTvShowDetails().getUrl()));
        startActivity(intent);
      }
    });
    binding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (dialog == null){
          dialog = new BottomSheetDialog(TVShowDetailsActivity.this);
          bottomSheetBinding = DataBindingUtil.inflate(
            LayoutInflater.from(TVShowDetailsActivity.this),
            R.layout.layout_episodes_bottom_sheet,
            findViewById(R.id.episodesContainer),
            false
          );
          dialog.setContentView(bottomSheetBinding.getRoot());
          bottomSheetBinding.episodesRecycle.setAdapter(new EpisodesAdapter(response.getTvShowDetails().getEpisodes()));
          bottomSheetBinding.textTitle.setText(String.format("Episodes | %s", tvShow.getName()));
          bottomSheetBinding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialog.dismiss();
            }
          });
        }
        // ---option select start--- //
        FrameLayout frameLayout = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (frameLayout != null){
          BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
          bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
          bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        // ---option select end--- //
        dialog.show();
      }
    });
    binding.imageWatchList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
            tvShowDetailsViewModel.addToWatchList(tvShow);
            runOnUiThread(()->{
              //binding.imageWatchList.setImageResource(R.drawable.ic_added);
              Toast.makeText(TVShowDetailsActivity.this, "已添加", Toast.LENGTH_SHORT).show();
            });
//            new CompositeDisposable().add(tvShowDetailsViewModel.addToWatchList(tvShow)
//              .subscribeOn(Schedulers.io())
//              .observeOn(AndroidSchedulers.mainThread())
//              .subscribe(()->{
//                binding.imageWatchList.setImageResource(R.drawable.ic_added);
//                  Toast.makeText(TVShowDetailsActivity.this, "已添加", Toast.LENGTH_SHORT).show();
//              })
//            );

      }
    });
  }

  // 初始化viewPager轮播
  private void loadImageSliders(String[] sliders) {
    binding.sliderViewPager.setOffscreenPageLimit(1);
    binding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliders));
    binding.sliderViewPager.setVisibility(View.VISIBLE);
    binding.viewFadingEdge.setVisibility(View.VISIBLE);
    setupSliderIndicators(sliders.length);
    binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        setCurrentSliderIndicator(position);
      }
    });
  }

  private void setupSliderIndicators(int count){
    ImageView[] indicators = new ImageView[count];
    LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    layoutParams.setMargins(8,0,8,0);
    for (int i = 0; i<indicators.length;i++){
      indicators[i] = new ImageView(getApplicationContext());
      indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));
      indicators[i].setLayoutParams(layoutParams);
      binding.layoutSliderIndicators.addView(indicators[i]);
    }
    binding.layoutSliderIndicators.setVisibility(View.VISIBLE);
    setCurrentSliderIndicator(0);
  }

  private void setCurrentSliderIndicator(int position){
    int childCount = binding.layoutSliderIndicators.getChildCount();
    for (int i = 0; i<childCount; i++){
      ImageView imageView = (ImageView) binding.layoutSliderIndicators.getChildAt(i);
      if (i == position){
        imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_active));
      }else {
        imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));

      }
    }
  }

}