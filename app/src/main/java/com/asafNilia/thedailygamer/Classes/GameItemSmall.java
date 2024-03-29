package com.asafNilia.thedailygamer.Classes;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import java.io.Serializable;

public class GameItemSmall implements Serializable {
    private String mImageResource;
    private String mGameName;
    private String mGameReleaseDate;
    private String mGamePrice;
    private String mRawPrice;
    private Boolean mIsFavorite;

    private String mGamePage;
    private Bitmap bitmap;

    public GameItemSmall(final String mImageResource, String mGameName, String mGameReleaseDate, String mGamePrice, String mGamePage, Boolean mIsFavorite) {
        this.mImageResource = mImageResource;
        this.mGameName = mGameName;
        this.mGameReleaseDate = mGameReleaseDate;
        this.mGamePrice = mGamePrice;
        this.mGamePage = mGamePage;
        this.mRawPrice = mGamePrice;
        this.mIsFavorite = mIsFavorite;
    }

    public String getmRawPrice() {
        return mRawPrice;
    }

    public void setmRawPrice(String mRawPrice) {
        this.mRawPrice = mRawPrice;
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

    public String getmGamePage() {
        return mGamePage;
    }

    public void setmGamePage(String mGamePrice) {
        this.mGamePage = mGamePage;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Boolean getmIsFavorite() {
        return mIsFavorite;
    }

    public void setmIsFavorite(Boolean mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }
}
