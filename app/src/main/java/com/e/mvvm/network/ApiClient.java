package com.e.mvvm.network;

import android.util.Log;

import com.e.mvvm.utils.Constant;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

  private static Retrofit retrofit;


  public static Retrofit getRetrofit() {
    if (retrofit == null) {

      HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
        new HttpLoggingInterceptor.Logger() {
          @Override
          public void log(@NonNull String message) {
            try {
              String text = URLDecoder.decode(message, "utf-8");
              Log.i("OKHttp-----", text);
            } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
              Log.i("OKHttp-----", message);
            }
          }
        }
      );
      //设置日志Level
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
      //添加拦截器到OkHttp，这是最关键的
      httpClient.addInterceptor(logging);

      retrofit = new Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();
    }
    return retrofit;
  }


}
