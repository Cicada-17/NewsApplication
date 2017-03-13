package com.liuzhao.Bean;

/**
 * Created by Administrator on 2月08日0008.
 */

public class ImageDataBean  {
    private String title;
    private String href;
    private String imageurl;

    @Override
    public String toString() {
        return "ImageDataBean{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
