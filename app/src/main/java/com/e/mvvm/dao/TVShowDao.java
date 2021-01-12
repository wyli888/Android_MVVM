package com.e.mvvm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.e.mvvm.models.TVShow;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/*
  RxJava2 中的数据源类型有5种，分别是 Observable，Flowable，Single，Maybe 和 Completable，它们的区别如下，
  看到有些同学只用 Observable，其实这并不是个很好的习惯。

  类别	特点
  Observable	多个数据，不支持背压
  Flowable	多个数据，支持背压
  Single	一个数据
  Maybe	一个或没有数据
  Completable	没有数据，只有结束信号
*/

@Dao
public interface TVShowDao {

  @Query("SELECT * FROM tvShows")
  Flowable<List<TVShow>> getWatchList();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Completable insertToWatchList(TVShow tvShow);

  @Delete
  Completable deleteFromWatchList(TVShow tvShow);


}
