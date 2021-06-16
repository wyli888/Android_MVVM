package com.e.mvvm.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.carlt.networklibs.NetType;
import com.carlt.networklibs.NetworkManager;
import com.carlt.networklibs.annotation.NetWork;
import com.e.mvvm.R;
import com.e.mvvm.action.ActivityCollector;
import com.e.mvvm.viewmodels.BaseViewModel;

import static com.e.mvvm.base.BaseApplication.APP_NAME;
import static com.e.mvvm.base.BaseApplication.isDebug;

public  class BaseActivity extends AppCompatActivity {


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    // 添加activity 到收集器中
    ActivityCollector.addActivity(this);
    //注册
    NetworkManager.getInstance().registerObserver(this);

  }


  // log isDebug作为总开关，然后控制是否显示调试信息
  public void TLog(String msg) {
    if (isDebug) {
      Log.i(APP_NAME, msg);
    }
  }

  @NetWork(netType = NetType.AUTO)
  public void network(NetType netType) {
    switch (netType) {
      case WIFI:
        TLog("WIFI");
        break;
      case CMNET:
      case CMWAP:
        TLog("4G");
        break;
      case NONE:
        TLog("无网络");
        break;
      default:
        break;
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

  }

  @Override
  protected void onPause() {
    super.onPause();

  }

  @Override
  protected void onStop() {
    super.onStop();

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityCollector.removeActivity(this);
    //注销
    //NetworkManager.getInstance().unRegisterObserver(this);
    //注销所有
    NetworkManager.getInstance().unRegisterAllObserver();
  }
}