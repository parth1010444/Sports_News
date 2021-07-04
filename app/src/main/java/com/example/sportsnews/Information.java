package com.example.sportsnews;

import android.graphics.Bitmap;

public class Information {

    private String author;
    private String title;
    private String desrciption;
    private String url;
    private String urltoimage;
    private String dateTime;

    private Bitmap img;

    public Information() {
        author="demo";
        title="demo";
        desrciption="demo";
        url="demo";
        urltoimage="demo";
        dateTime="demo";
        img = null;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesrciption(String desrciption) {
        this.desrciption = desrciption;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrltoimage(String urltoimage) {
        this.urltoimage = urltoimage;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDesrciption() {
        return desrciption;
    }

    public String getUrl() {
        return url;
    }

    public String getUrltoimage() {
        return urltoimage;
    }

    public String getDateTime() {
        return dateTime;
    }
}
