package com.e.mvvm.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
/*
  1、什么是序列化和反序列化
  Serialization（序列化）是一种将对象以一连串的字节描述的过程；反序列化deserialization是一种将这些字节重建成一个对象的过程。
  2、什么情况下需要序列化
  a）当你想把的内存中的对象保存到一个文件中或者数据库中时候；
  b）当你想用套接字在网络上传送对象的时候；
  c）当你想通过RMI传输对象的时候；
  3、如何实现序列化
  将需要序列化的类实现Serializable接口就可以了，Serializable接口中没有任何方法，可以理解为一个标记，即表明这个类可以序列化。
*/

@Entity(tableName = "tvShows")
public class TVShow implements Serializable {

  @PrimaryKey
  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("start_date")
  private String startDate;

  @SerializedName("country")
  private String country;

  @SerializedName("network")
  private String network;

  @SerializedName("status")
  private String status;

  @SerializedName("image_thumbnail_path")
  private String imageThumbnailPath;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getNetwork() {
    return network;
  }

  public void setNetwork(String network) {
    this.network = network;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getImageThumbnailPath() {
    return imageThumbnailPath;
  }

  public void setImageThumbnailPath(String imageThumbnailPath) {
    this.imageThumbnailPath = imageThumbnailPath;
  }



}

