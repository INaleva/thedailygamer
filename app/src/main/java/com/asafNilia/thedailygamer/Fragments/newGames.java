package com.asafNilia.thedailygamer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asafNilia.thedailygamer.Activities.MainActivity;
import com.asafNilia.thedailygamer.Adapters.smallItemAdapter;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link newGames.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link newGames#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newGames extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<GameItemSmall> listOfGameItems;
    boolean firstOpen = true;

    private OnFragmentInteractionListener mListener;

    public newGames() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newGames.
     */
    // TODO: Rename and change types and number of parameters
    public static newGames newInstance(String param1, String param2) {
        newGames fragment = new newGames();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        listOfGameItems = new ArrayList<>();



        Ion.with(getContext()).load(MainActivity.url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                mRecyclerView = getView().findViewById(R.id.recyclerViewOfNewGames);
                mAdapter = new smallItemAdapter(listOfGameItems);
                mLayoutManager = new LinearLayoutManager(getView().getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                fillArrayWithDataFromSourceCode(result);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_new_games, container, false);

        return  view;
    }

    private void fillArrayWithDataFromSourceCode(String sourceCode) {

        final ArrayList<String> allImages = new ArrayList<>();
        final ArrayList<String> allNames = new ArrayList<>();
        final ArrayList<String> allReleaseDates = new ArrayList<>();
        final ArrayList<String> allPrices= new ArrayList<>();
        final ArrayList<String> allExpands = new ArrayList<>();
        ArrayList<String> allPages = new ArrayList<>();


        /**1 param is image is between    capsule"><img src="    and   "></div>
        //2 param is game name is between   <span class="title">    and     </span>
        //3 param is release date is between    col search_released responsive_secondrow">   and   </div>
        //4 param is price is between       data-price-final=" and      ">
        //5 param is the url of the inner store page is between <a href=" and "  data-ds-appid=
         **/

        Pattern patternForImages = Pattern.compile("capsule\"><img src=\"(.*?)\"></div>");
        Pattern patternForNames = Pattern.compile("<span class=\"title\">(.*?)</span>");
        Pattern patternForReleaseDate = Pattern.compile("col search_released responsive_secondrow\">(.*?)</div>");
        Pattern pattenForPrice = Pattern.compile("data-price-final=\"(.*?)\">");
        final Pattern patternForExpand= Pattern.compile("<a href=\"(.*?)\"  data-ds-appid=|<a href=\"(.*?)\"  data-ds-bundleid=");
        Pattern pattenPages = Pattern.compile("this (.); return false;\">([0-9]+)</a>");


        /**the last one should give us the amount of pages, there will be more then one patten match, so we need to find the max of them,
        // so we can later manipulte the url with pages for example https://store.steampowered.com/search/?page=2
        */
        Matcher matcherForImages = patternForImages.matcher(sourceCode);
        Matcher matcherForNames = patternForNames.matcher(sourceCode);
        Matcher matcherForReleaseDate = patternForReleaseDate.matcher(sourceCode);
        Matcher matcherForPrice = pattenForPrice.matcher(sourceCode);
        Matcher matcherForExpand = patternForExpand.matcher(sourceCode);
        Matcher matcherForPages = pattenPages.matcher(sourceCode);

        while (matcherForImages.find())
        {
            /** At this point we get url like this:
            https://steamcdn-a.akamaihd.net/steam/apps/730/capsule_sm_120.jpg?t=1554409309
            its a low quality logo (not the capsule_sm)
            we can get much better quality by replacing it to:
            https://steamcdn-a.akamaihd.net/steam/apps/730/header.jpg
            */

            String tempImage = matcherForImages.group(1);
            String fixedImage = tempImage.replaceAll("capsule_sm_120.jpg","header.jpg");
            allImages.add(fixedImage);
        }

        while (matcherForNames.find())
        {
            allNames.add(matcherForNames.group(1)); /** add all names to array */
        }

        while (matcherForReleaseDate.find())
        {
            allReleaseDates.add(matcherForReleaseDate.group(1)); /** add all release dates to array */
        }

        while (matcherForPrice.find())
        {
            allPrices.add(matcherForPrice.group(1)); /**add all prices to array */
        }

        while (matcherForExpand.find())
        {
            allExpands.add(matcherForExpand.group(1)); /**add all store pages to array */
        }

        while (matcherForPages.find())
        {
            allPages.add(matcherForPages.group(1)); /** add all pages to array
          array "allPages" is string array, we need to convert it to int, and get the maximum value. */
        }

        for(int i=0; i<allImages.size(); i++)
        {
            listOfGameItems.add(new GameItemSmall(allImages.get(i),allNames.get(i),allReleaseDates.get(i),allPrices.get(i),allExpands.get(i))); /** add items to main list */
        }



    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNewGamesFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other com.asafNilia.thedailygamer.Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNewGamesFragmentInteraction(Uri uri);
    }


}
