package com.asafNilia.thedailygamer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.asafNilia.thedailygamer.Activities.MainActivity;
import com.asafNilia.thedailygamer.Adapters.smallItemAdapter;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link gameItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link gameItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gameItem extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;
    private String mVideoResource;
    private String mGameTitle;
    private String mGameReleaseDate;
    private String mGamePrice;
    private String mGameDescription;

    private VideoView Video;
    private TextView  Title;
    private TextView  ReleaseDate;
    private TextView  Price;
    private TextView  Description;

    private OnFragmentInteractionListener mListener;

    public gameItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gameItem.
     */
    // TODO: Rename and change types and number of parameters
    public static gameItem newInstance(String param1, String param2) {

        gameItem fragment = new gameItem();
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
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //TODO Here we access the current store url
        Ion.with(getContext()).load(MainActivity.storeUrl).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                fillArrayWithDataFromSourceCode(result);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_item, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onItemFragmentInteraction(uri);
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
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onItemFragmentInteraction(Uri uri);
    }

    private void fillArrayWithDataFromSourceCode(String sourceCode) {
        /**1 param is video resource is between    data-webm-source="    and   "
         //2 param is game title is between   <title>    and      on Steam</title>
         //3 param is release date is between    col search_released responsive_secondrow">   and   </div>
         //4 param is price is between
         //5 param is description is between       About This Game</h2> and      <div class=
         **/

        Pattern patternForVideo = Pattern.compile("data-webm-source=\"(.*?)\"");
        Pattern patternForTitle = Pattern.compile("<title>(.*?) on Steam</title>");
        //Pattern patternForReleaseDate = Pattern.compile("col search_released responsive_secondrow\">(.*?)</div>");
        Pattern patternForDescription = Pattern.compile("(?s)(?<=<h2>)About This Game(?=</h2>)(.+?)<div class=\"");

        //Pattern patternForDescription = Pattern.compile("<meta name=\"Description\" content=\"(.*?)\">");

        Matcher matcherForVideos = patternForVideo.matcher(sourceCode);
        Matcher matcherForTitles = patternForTitle.matcher(sourceCode);
        Matcher matcherForDescription = patternForDescription.matcher(sourceCode);

        if (matcherForVideos.find()) {
            mVideoResource = matcherForVideos.group(1);
            //set video url and play it
            Video.setVideoPath(mVideoResource);
            Video.start();
        }

        if (matcherForTitles.find()) {
            mGameTitle = matcherForTitles.group(1);
            Title.setText(mGameTitle);
        }

        if (matcherForDescription.find()) {
            mGameDescription = matcherForDescription.group(1);
            //fix some html code string issues
            mGameDescription.replaceAll("<br ?/?>","\n"); //TODO: I wanted to replace every <br /> with a new line maybe you know how?
            mGameDescription = Jsoup.parse(mGameDescription).text();
            Description.setText(mGameDescription);

        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Video = (VideoView) getView().findViewById(R.id.gameVideo);
        Title = (TextView) getView().findViewById(R.id.gameTitle);
        Description = (TextView) getView().findViewById(R.id.gameDescription);
        Description.setMovementMethod(new ScrollingMovementMethod());

        //private TextView gameReleaseDate;
        //private TextView gamePrice;
        //private TextView gameDescription;
    }
}
