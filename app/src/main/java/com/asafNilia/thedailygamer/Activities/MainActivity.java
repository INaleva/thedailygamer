package com.asafNilia.thedailygamer.Activities;

import android.animation.ObjectAnimator;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asafNilia.thedailygamer.Classes.CustomEditText;
import com.asafNilia.thedailygamer.Fragments.buyNow;
import com.asafNilia.thedailygamer.Fragments.gameItem;
import com.asafNilia.thedailygamer.Fragments.menu;
import com.asafNilia.thedailygamer.Fragments.newGames;
import com.asafNilia.thedailygamer.Fragments.searching;
import com.asafNilia.thedailygamer.R;

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
    FragmentTransaction fragmentTransaction;
    public static CustomEditText textSearchField;
    public static FrameLayout mainLayout; //hide it when loading game item
    public static FrameLayout loadLayout; //hide it when showing game item.
    public static LinearLayout pagesLayout;
    public static ImageButton buyNow;       //only visible when inside game item
    public static TextView currentPageView;
    public static TextView nextPageView;
    public static TextView lastPageView;
    public static TextView firstPageView;

    public static int nextPage;

    public static int lastPage;
    public static String defaultUrl = "https://store.steampowered.com/search/?term="; //the default link.
    public static String urlSearch = "https://store.steampowered.com/search/?term=";          //link used to search and open categories
    public static String term = ""; //for search
    public static String url = "https://store.steampowered.com/search/"; //base url
    public static String tags = ""; //for category
    public static int currentPage = 1; //for pages
    public static String storeUrl = ""; //link to the game in steam


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

        firstPageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                nextPage = 2;
                url = url + "?tags=" +tags + "&page=" + currentPage + "term=" + term;
                changeFragment(new newGames());
            }
        });

        nextPageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                nextPage++;
                url = url + "?tags=" +tags + "&page=" + currentPage + "term=" + term;
                changeFragment(new newGames());
            }
        });
        lastPageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = url + "?tags=" +tags + "&page=" + lastPage + "term=" + term;
                changeFragment(new newGames());

            }
        });

        //on click listener for key 'ENTER', as for now we only print the text that was written.
        textSearchField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    url = urlSearch + textSearchField.getText();
                    changeFragment(new newGames()); /** search games */
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

        textSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    cancelSearch();
                    return true;
                }

                return false;
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
        buyNow.setVisibility(View.INVISIBLE);
        changeFragment(new menu());
    }

    public void openHome(View view) //open home page with most popular games
    {
        cancelSearch();
        term = "";
        tags = "";
        url = defaultUrl;
        currentPage = 1;
        nextPage = 2;
            changeFragment(new newGames());
        buyNow.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
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

        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
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

        }
        MainActivity.term = "";
        MainActivity.currentPage = 1;
        MainActivity.nextPage = 2;
        url = url + "?tags=" + tags + "&page=" + currentPage;
        changeFragment(new menu()); //refresh
        changeFragment(new newGames()); //the fragment


    }

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
