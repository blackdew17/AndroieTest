package com.eskim.picturesample.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PictureInfo implements Serializable {

    /*
{"id":"1025",
"author":"Matthew Wiebe",
"width":4951,
"height":3301
,"url":"https://unsplash.com/photos/U5rMrSI7Pn4"
,"download_url":"https://picsum.photos/id/1025/4951/3301"}]
     */

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("url")
    private String url;

    @SerializedName("download_url")
    private String download_url;

    public PictureInfo(String id, String author, int width, int height, String url, String download_url) {
        this.id = id;
        this.author = author;
        this.width = width;
        this.height = height;
        this.url = url;
        this.download_url = download_url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public String getDownloadUrl() {
        return download_url;
    }

}
