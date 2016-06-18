package com.flowerapp.bean;

import android.support.v7.graphics.Palette;

import com.flowerapp.others.Util;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sandeep Tiwari on 6/15/2016.
 */

public class Flower implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("image_src")
    @Expose
    private String imageSrc;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("ratio")
    @Expose
    private Double ratio;
    @SerializedName("featured")
    @Expose
    private Integer featured;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("corrupt")
    @Expose
    private Integer corrupt;

    private int bookmark;

    transient private Palette.Swatch swatch;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The imageSrc
     */
    public String getImageSrc() {
        return imageSrc;
    }

    /**
     *
     * @param imageSrc
     * The image_src
     */
    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    /**
     *
     * @return
     * The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The modifiedDate
     */
    public String getModifiedDate() {
        return modifiedDate;
    }

    /**
     *
     * @param modifiedDate
     * The modified_date
     */
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     *
     * @return
     * The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     *
     * @return
     * The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The ratio
     */
    public Double getRatio() {
        return ratio;
    }

    /**
     *
     * @param ratio
     * The ratio
     */
    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    /**
     *
     * @return
     * The featured
     */
    public Integer getFeatured() {
        return featured;
    }

    /**
     *
     * @param featured
     * The featured
     */
    public void setFeatured(Integer featured) {
        this.featured = featured;
    }

    /**
     *
     * @return
     * The category
     */
    public Integer getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The corrupt
     */
    public Integer getCorrupt() {
        return corrupt;
    }

    /**
     *
     * @param corrupt
     * The corrupt
     */
    public void setCorrupt(Integer corrupt) {
        this.corrupt = corrupt;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getHighResImage(int minWidth, int minHeight) {
        String url = imageSrc + "?fm=png";

        //minimize processing costs of unsplash image hosting
        //try to eliminate the white line on top

        if (minWidth > 0 && minHeight > 0) {
            float phoneRatio = (1.0f * minWidth) / minHeight;
            if (phoneRatio < getRatio()) {
                if (minHeight < 1080) {
                    //url = url + "&h=" + minHeight;
                    url = url + "&h=" + 1080;
                }
            } else {
                if (minWidth < 1920) {
                    //url = url + "&w=" + minWidth;
                    url = url + "&w=" + 1920;
                }
            }
        }

        return url;
    }

    public String getImageSrc(int screenWidth) {
        return imageSrc + "?q=75&fm=jpg&w=" + Util.optimalImageWidth(screenWidth);

        /*
        wait with this one for now. i don't want to bring up the generation quota of unsplash
        String url = image_src + "?q=75&fit=max&fm=jpg";
        if (screenWidth > 0) {
            //it's enough if we load an image with 2/3 of the size
            url = url + "&w=" + (screenWidth / 3 * 2);
        }
        return url;
        */
    }

    public void setSwatch(Palette.Swatch swatch) {
        this.swatch = swatch;
    }

    public Palette.Swatch getSwatch() {
        return swatch;
    }
}
