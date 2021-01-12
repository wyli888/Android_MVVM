package com.e.mvvm.action;

public class Action {
  public static final int START_ACTIVITY = 0;
  private final int mAction;

  public Action(int action) {
    mAction = action;
  }

  public int getValue() {
    return mAction;
  }
}
