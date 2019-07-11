package com.asafNilia.thedailygamer.Classes;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class GameItemSmall {
    private String mImageResource;
    private String mGameName;
    private String mGameReleaseDate;
    private String mGamePrice;
    private Bitmap bitmap;

    public GameItemSmall(final String mImageResource, String mGameName, String mGameReleaseDate, String mGamePrice) {
        this.mImageResource = mImageResource;
        this.mGameName = mGameName;
        this.mGameReleaseDate = mGameReleaseDate;
        this.mGamePrice = mGamePrice;
    }

    public String getImageResource() {
        return mImageResource;
    }

    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmGameName() {
        return mGameName;
    }

    public void setmGameName(String mGameName) {
        this.mGameName = mGameName;
    }

    public String getmGameReleaseDate() {
        return mGameReleaseDate;
    }

    public void setmGameReleaseDate(String mGameReleaseDate) {
        this.mGameReleaseDate = mGameReleaseDate;
    }

    public String getmGameCreator() {
        return mGamePrice;
    }

    public void setmGameCreator(String mGameCreator) {
        this.mGamePrice = mGameCreator;
    }

    public String getmGamePrice() {
        return mGamePrice;
    }

    public void setmGamePrice(String mGamePrice) {
        this.mGamePrice = mGamePrice;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
