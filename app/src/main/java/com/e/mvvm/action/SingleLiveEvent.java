package com.e.mvvm.action;


import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* 一个事件指发送一次的动作。ViewModels 暴露了数据，但什么是事件呢？例如，导航事件或者展示 Snackbar 消息都是应该只执行一次的动作。
 事件的概念并不能很好的展示 LiveData 是如何存储和恢复数据的。来看一下下面的 ViewModel：

 LiveData<String> snackbarMessage = new MutableLiveData<>();

 一个 Activity 开始观察这个数据，并且 ViewModel 完成了一个操作之后它需要更新这条消息：
 snackbarMessage.setValue("Item saved!");
 Activity 收到这条消息，并展示在 Snackbar 中。这显然没毛病。
 然而，如果用户旋转手机，创建了新的 Activity 并开始观察。当 LiveData 观察发生后，Activity 立即收到了旧的值，这时消息再次展示了！
 我们扩展了 LiveData，并创建了一个类叫 SingleLiveEvent，作为刚刚问题的解决方案。它仅仅发送订阅之后出现的更新。注意它只支持一个观察者。
*
 */

public class SingleLiveEvent<T> extends MutableLiveData<T> {

  private static final String TAG = "SingleLiveEvent";

  private final AtomicBoolean mPending = new AtomicBoolean(false);

  @MainThread
  public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {

    if (hasActiveObservers()) {
      Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
    }

    // Observe the internal MutableLiveData
    super.observe(owner, new Observer<T>() {
      @Override
      public void onChanged(@Nullable T t) {
        if (mPending.compareAndSet(true, false)) {
          observer.onChanged(t);
        }
      }
    });
  }

  @MainThread
  public void setValue(@Nullable T t) {
    mPending.set(true);
    super.setValue(t);
  }

  /**
   * Used for cases where T is Void, to make calls cleaner.
   */
  @MainThread
  public void call() {
    setValue(null);
  }
}
