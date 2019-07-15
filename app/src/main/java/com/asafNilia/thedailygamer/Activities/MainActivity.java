package com.asafNilia.thedailygamer.Activities;

import android.animation.ObjectAnimator;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.asafNilia.thedailygamer.Fragments.buyNow;
import com.asafNilia.thedailygamer.Fragments.gameItem;
import com.asafNilia.thedailygamer.Fragments.menu;
import com.asafNilia.thedailygamer.Fragments.newGames;
import com.asafNilia.thedailygamer.Fragments.searching;
import com.asafNilia.thedailygamer.R;

import java.util.Currency;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        gameItem.OnFragmentInteractionListener,
        menu.OnFragmentInteractionListener, newGames.OnFragmentInteractionListener,
        searching.OnFragmentInteractionListener, buyNow.OnFragmentInteractionListener
{

    //Data

    String toSearch = "";
    public static EditText textSearchField;
    ImageView homeButton;
    ImageView menuButton;
    ImageView searchButton;
    public static ImageButton buyNow;
    boolean searchTextIsVisible = false;
    boolean menuOpen = false;
    FragmentTransaction fragmentTransaction;
    public static FrameLayout mainLayout;
    public static FrameLayout loadLayout;

    public static String url = "https://store.steampowered.com/search/?term="; //the default link.
    public static String storeUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSearchField = findViewById(R.id.textSearch);
        homeButton = findViewById(R.id.homeButton);
        menuButton = findViewById(R.id.buttonOpenMenu);
        searchButton = findViewById(R.id.buttonSearchGame);
        mainLayout = findViewById(R.id.mainView);
        loadLayout = findViewById(R.id.loadView);
        buyNow = findViewById(R.id.buyNow);



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

        homeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Home screen, lets to see all the cool games!", Toast.LENGTH_SHORT).show();
                ObjectAnimator.ofFloat(v, "rotation", 0, 360).start();

                return true;
            }
        });

        menuButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Main menu, helps you to see only the games you like!", Toast.LENGTH_SHORT).show();
                ObjectAnimator.ofFloat(v, "rotation", 0, 360).start();

                return true;
            }
        });

        searchButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Search for that game you really liked!", Toast.LENGTH_SHORT).show();
                ObjectAnimator.ofFloat(v, "rotation", 0, 360).start();

                return true;
            }
        });
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

    public void searchClicked(View view) {

        if(searchTextIsVisible)
        {
            textSearchField.setVisibility(View.GONE);
            searchTextIsVisible = false;
            //changeFragment(new newGames());
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
        buyNow.setVisibility(View.INVISIBLE);

        if (!menuOpen) {
            changeFragment(new menu());
            menuOpen = true;
        }
        else {
            changeFragment(new newGames());
            menuOpen = false;
        }

    }

    public void openHome(View view)
    {
        cancelSearch();
            changeFragment(new newGames());
        buyNow.setVisibility(View.INVISIBLE);
    }

    void cancelSearch() //removes the search bar
    {
        textSearchField.setVisibility(View.GONE);
        searchTextIsVisible = false;
    }

    public void changeFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame_layout,fragment);
        transaction.commit();

    }


    public void buyNowClicked(View view) {

        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        changeFragment(new buyNow());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
