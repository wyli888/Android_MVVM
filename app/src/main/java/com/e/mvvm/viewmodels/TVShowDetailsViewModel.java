package com.e.mvvm.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.mvvm.database.TVShowDatabase;
import com.e.mvvm.models.TVShow;
import com.e.mvvm.network.ApiClient;
import com.e.mvvm.network.ApiService;
import com.e.mvvm.response.TVShowDetailsResponse;
import com.e.mvvm.respository.TVShowDetailsRepository;

import io.reactivex.Completable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* 如果需要在视图模型中使用上下文，则应使用AndroidViewModel，因为它包含应用程序上下文(以检索上下文调用getApplication())，否则请使用常规ViewModel。
* AndroidViewModel具有应用程序上下文。
  我们都知道拥有静态上下文实例是邪恶的！(可能导致内存泄漏！)
  但是，拥有静态应用程序实例并不像您想象的那样糟糕
  因为，我们正在运行的应用程序中只有一个Application实例。
  因此，在特定的类中使用并拥有Application实例通常不是问题。
  但是，如果应用程序实例引用了它们，则由于引用周期问题而成为问
*
* AndroidViewModel的唯一区别是它与应用程序上下文一起提供，如果您需要上下文来获取系统服务或有类似要求，这将很有帮助
*
*AndroidViewModel：

public class PriceViewModel extends AndroidViewModel {
private PriceRepository priceRepository;

public PriceViewModel(@NonNull Application application) {
    super(application);
    priceRepository= new PriceRepository(application);
    allPrices = priceRepository.getAllPrices();
}
*
*
ViewModel：

public class PriceViewModel extends ViewModel {
public PriceViewModel() {
    super();
}

* */

public class TVShowDetailsViewModel extends AndroidViewModel {

  private TVShowDetailsRepository tvShowDetailsRepository;
  private TVShowDatabase tvShowDatabase;
  public  MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();

  public  TVShowDetailsViewModel(@NonNull Application application){
    super(application);
    tvShowDetailsRepository = new TVShowDetailsRepository();
    tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
  }

//  public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
//   return tvShowDetailsRepository.getTVShowDetails(tvShowId);
//  }

  public Completable addToWatchList(TVShow tvShow){
    return tvShowDatabase.tvShowDao().insertToWatchList(tvShow);
  }

  // 从接口获取数据
  public void getDetails(String tvShowId){
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
    apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowDetailsResponse>() {
      @Override
      public void onResponse(@io.reactivex.annotations.NonNull Call<TVShowDetailsResponse> call, @io.reactivex.annotations.NonNull Response<TVShowDetailsResponse> response) {
        data.setValue(response.body());
      }

      @Override
      public void onFailure(@io.reactivex.annotations.NonNull Call<TVShowDetailsResponse> call, @io.reactivex.annotations.NonNull Throwable t) {
        data.setValue(null);
      }
    });
  }







}
