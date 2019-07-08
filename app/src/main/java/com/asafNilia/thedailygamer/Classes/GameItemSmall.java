package com.asafNilia.thedailygamer.Classes;

public class GameItemSmall {
    private int mImageResource;
    private String mGameName;
    private String mGameReleaseDate;
    private String mGameCreator;
    private String mGameGenre;

    public GameItemSmall(int mImageResource, String mGameName, String mGameReleaseDate, String mGameCreator, String mGameGenre) {
        this.mImageResource = mImageResource;
        this.mGameName = mGameName;
        this.mGameReleaseDate = mGameReleaseDate;
        this.mGameCreator = mGameCreator;
        this.mGameGenre = mGameGenre;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
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
        return mGameCreator;
    }

    public void setmGameCreator(String mGameCreator) {
        this.mGameCreator = mGameCreator;
    }

    public String getmGameGenre() {
        return mGameGenre;
    }

    public void setmGameGenre(String mGameGenre) {
        this.mGameGenre = mGameGenre;
    }
}
