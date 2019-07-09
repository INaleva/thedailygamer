package com.asafNilia.thedailygamer.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    String toSearch;
    EditText textSearchField;
    boolean searchTextIsVisible = false;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSearchField = findViewById(R.id.textSearch);

        //on click listener for key 'ENTER', as for now we only print the text that was written.
        textSearchField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(MainActivity.this,"Searching for: " + textSearchField.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });        //apply newGames fragment to the fragment on start.
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame_layout, new newGames());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    private void initImages() {
        //here we will add urls
    }

    private void initRecylerView() {
        RecyclerView view = findViewById(R.id.recyclerViewOfNewGames);
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

    public void searchClicked(View view) {

        if(searchTextIsVisible)
        {
            textSearchField.setVisibility(View.GONE);
            searchTextIsVisible = false;
            changeFragment(new newGames());
        }
        else
        {
            textSearchField.setVisibility(View.VISIBLE);
            searchTextIsVisible = true;
        }


    }

    public void openMenu(View view)
    {
        cancelSearch();
        changeFragment(new menu());

    }

    public void openHome(View view)
    {
        cancelSearch();
        changeFragment(new newGames());
    }

    void cancelSearch() //removes the search bar
    {
        textSearchField.setVisibility(View.GONE);
        searchTextIsVisible = false;
    }

    void changeFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame_layout,fragment);
        transaction.commit();

    }


}
