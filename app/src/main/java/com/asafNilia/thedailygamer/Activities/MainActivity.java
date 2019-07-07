package com.asafNilia.thedailygamer.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.asafNilia.thedailygamer.Fragments.gameItem;
import com.asafNilia.thedailygamer.Fragments.menu;
import com.asafNilia.thedailygamer.Fragments.newGames;
import com.asafNilia.thedailygamer.Fragments.searching;
import com.asafNilia.thedailygamer.Fragments.upcomingGames;
import com.asafNilia.thedailygamer.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        gameItem.OnFragmentInteractionListener,
        menu.OnFragmentInteractionListener, newGames.OnFragmentInteractionListener,
        searching.OnFragmentInteractionListener, upcomingGames.OnFragmentInteractionListener
{

    //Data
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mCreator = new ArrayList<>();
    private ArrayList<String> mGenre = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImages();
        initRecylerView();
    }

    private void initImages() {
        //here we will add urls
    }

    private void initRecylerView() {
        RecyclerView view = findViewById(R.id.recyclerViewOfBestGames);
        //RecyclerViewAdapter stuff
        //TODO: Stopped here
    }

    @Override
    public void onItemFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMenuFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNewGamesFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSearchFragmentInteraction(Uri uri) {

    }

    @Override
    public void onUpcomingFragmentInteraction(Uri uri) {

    }
}
