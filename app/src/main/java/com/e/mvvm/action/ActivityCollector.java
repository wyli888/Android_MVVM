package com.e.mvvm.action;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
  public static List<Activity> activities = new ArrayList<>();

  // 向集合中添加activity
  public static void addActivity(Activity activity){
    if (activity != null){
      activities.add(activity);
    }
  }

  // 从集合中移除activity
  public static void removeActivity(Activity activity){
    activities.remove(activity);
  }

  // 移除并关闭所有activity
  public static void finishAll(){
    for (Activity activity: activities) {
        if (!activity.isFinishing()){
          activity.finish();
        }
    }
    activities.clear();
  }



}
