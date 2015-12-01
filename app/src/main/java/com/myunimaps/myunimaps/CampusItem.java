package com.myunimaps.myunimaps;

import android.graphics.drawable.Drawable;

/**
 * Created by Tyler on 4/23/2015.
 */
public class CampusItem {
    private String title;
    private String cLat;
    private String cLon;
    private String cCode;
    private Drawable info;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getcLat() {
        return cLat;
    }

    public void setcLat(String cLat) {
        this.cLat = cLat;
    }

    public String getcLon() { return cLon; }

    public void setcLon(String cLon) {
        this.cLon = cLon;
    }

    public String getcCOde() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    //public Drawable getThumbnail() {
    //  return info;
    //}

    //public void setThumbnail(Drawable thumbnail) {
    //    this.info = thumbnail;
    //}
}