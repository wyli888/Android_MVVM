package com.e.mvvm.base;


import android.app.Application;
import com.carlt.networklibs.NetworkManager;
import com.hjq.toast.ToastUtils;


public class BaseApplication extends Application {

  public static boolean isDebug = true ;
  public static String APP_NAME = "BaseApplication"  ;



  @Override
  public void onCreate() {
    super.onCreate();
    // 网络监听
    NetworkManager.getInstance().init(this);
    // Toast弹消息初始化
    ToastUtils.init(this);
  }

  @Override
  public void onTerminate() {

    super.onTerminate();

  }

  // 在内存低时,发送广播可以释放一些内存
  @Override
  public void onLowMemory() {
    super.onLowMemory();
  }


}
