package com.asafNilia.thedailygamer.Activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asafNilia.thedailygamer.Classes.CustomEditText;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.Fragments.buyNow;
import com.asafNilia.thedailygamer.Fragments.gameItem;
import com.asafNilia.thedailygamer.Fragments.menu;
import com.asafNilia.thedailygamer.Fragments.newGames;
import com.asafNilia.thedailygamer.Fragments.searching;
import com.asafNilia.thedailygamer.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        gameItem.OnFragmentInteractionListener,
        menu.OnFragmentInteractionListener, newGames.OnFragmentInteractionListener,
        searching.OnFragmentInteractionListener, buyNow.OnFragmentInteractionListener
{

    //Data

    ImageView homeButton;
    ImageView menuButton;
    ImageView searchButton;
    static boolean searchTextIsVisible = false; //to make the menu smaller
    public static boolean isInFavorite = false;
    public static EditText textSearchField;
    public static FrameLayout mainLayout; //hide it when loading game item
    public static FrameLayout loadLayout; //hide it when showing game item.
    public static LinearLayout pagesLayout;
    public static ImageButton buyNow;       //only visible when inside game item
    public static Button currentPageView;
    public static Button nextPageView;
    public static Button lastPageView;
    public static Button firstPageView;

    public static int nextPage;

    public static int lastPage;
    public static String defaultUrl = "https://store.steampowered.com/search/?term="; //the default link.
    public static String urlSearch = "https://store.steampowered.com/search/?term=";          //link used to search and open categories
    public static String term = ""; //for search
    public static String url = "https://store.steampowered.com/search/"; //base url
    public static String tags = ""; //for category
    public static int currentPage = 1; //for pages
    public static String storeUrl = ""; //link to the game in steam
    public static ArrayList<GameItemSmall> listOfFavorites;
    public static ArrayList<GameItemSmall> listOfGameItems;


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
        pagesLayout = findViewById(R.id.numberLayout);
        buyNow = findViewById(R.id.buyNow);
        currentPageView = findViewById(R.id.curPage);
        nextPageView = findViewById(R.id.nextPage);
        lastPageView = findViewById(R.id.lastPage);
        firstPageView = findViewById(R.id.firstPage);
        currentPage = 1;
        nextPage = 2;
        listOfFavorites = new ArrayList<>(); //init favorites array to empty

        if (currentPage==1)
            firstPageView.setVisibility(View.INVISIBLE);

        firstPageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                nextPage = 2;
                url = "https://store.steampowered.com/search/";
                if(!tags.equals(""))
                url = url + "?tags=" +tags + "&page=" + currentPage + "term=" + term;
                else
                    url = url + "?page=" + currentPage + "term=" + term;
                changeFragment(new newGames());
            }
        });

        nextPageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                nextPage++;
                url = "https://store.steampowered.com/search/";
                if(term!=null) {
                    if(!tags.equals(""))
                    url = url + "?tags=" + tags + "&page=" + currentPage; //case when we are in search
                    else
                        url = url + "?page=" + currentPage; //case when we are in search

                }
                changeFragment(new newGames());
            }
        });
        lastPageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tags.equals(""))
                url = url + "?tags=" +tags + "&page=" + lastPage + "term=" + term;
                else
                    url = url + "&page=" + lastPage + "term=" + term;
                changeFragment(new newGames());

            }
        });

        //on click listener for key 'ENTER', as for now we only print the text that was written.
        textSearchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                    isInFavorite = false;
                    url = "https://store.steampowered.com/search/";
                    url = urlSearch + textSearchField.getText();
                    hideSoftKeyboard(MainActivity.this);
                    textSearchField.setVisibility(View.GONE);
                    changeFragment(new newGames()); /** search games */
                    return true;
                        default:
                            break;
                    }
                }
                return false;

            }
        });

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


        changeFragment(new newGames());
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

    public void searchClicked(View view) { //minimize menu before search and return to normal after search

        if(searchTextIsVisible)
        {
            textSearchField.setVisibility(View.GONE);
            searchTextIsVisible = false;
        }
        else
        {
            textSearchField.setVisibility(View.VISIBLE);
            searchTextIsVisible = true;
            textSearchField.setFocusableInTouchMode(true);
            textSearchField.requestFocus();
            showKeyboard();
        }


    }



    public void openMenu(View view) //open menu with game categories
    {
        cancelSearch();
        isInFavorite = false;
        buyNow.setVisibility(View.GONE);
        changeFragment(new menu());
    }

    public void openHome(View view) //open home page with most popular games
    {
        cancelSearch();
        buyNow.setVisibility(View.GONE);
        isInFavorite = false;
        term = "";
        tags = "";
        url = defaultUrl; //was = defaulturl
        currentPage = 1;
        nextPage = 2;
            changeFragment(new newGames());
    }

    @Override
    public void onBackPressed() {
        cancelSearch();
    }

    public static void cancelSearch() //removes the search bar
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


    public void buyNowClicked(View view) { //opens the buyNow fragment, it has a webview that opens steam game page

        changeFragment(new buyNow());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void categoryClick(View view) {
        String category = getResources().getResourceEntryName(view.getId());

        switch (category)
        {
            case "actionGames":
                tags = "19";
                break;
            case "adventureGames":
                tags = "21";
                break;
            case "casualGames":
                tags = "597";
                break;
            case "simulatorGames":
                tags = "599";
                break;
            case "strategyGames":
                tags = "9";
                break;
            case "buildingGames":
                tags = "1643";
                break;
            case "racingGames":
                tags = "699";
                break;
            case "twoDimensionGames":
                tags = "3871";
                break;
            case "freeToPlayGames":
                tags = "113";
                break;
            case "fantasyGames":
                tags = "1684";
                break;
            case "funnyGames":
                tags = "4136";
                break;
            case "rpgGames":
                tags = "122";
                break;
            case "indieGames":
                tags = "492";
                break;
            case "shooterGames":
                tags = "1774";
                break;
            case "mmorpgGames":
                tags = "1754";
                break;
            case "openWorldGames":
                tags = "1695";
                break;
            case "puzzleGames":
                tags = "1664";
                break;
            case "sportGames":
                tags = "701";
                break;
            case "favoriteGames":
                tags = "1";
                break;
        }
        MainActivity.term = "";
        MainActivity.currentPage = 1;
        MainActivity.nextPage = 2;
        url = "https://store.steampowered.com/search/"; //reset url to the basic one
        if (!tags.equals("1")) //change url only if its not favorites category
            url = url + "?tags=" + tags + "&page=" + currentPage;
        changeFragment(new menu()); //refresh
        changeFragment(new newGames()); //the fragment


    }

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }



}
