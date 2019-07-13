package com.asafNilia.thedailygamer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.asafNilia.thedailygamer.Activities.MainActivity;
import com.asafNilia.thedailygamer.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.asafNilia.thedailygamer.Activities.MainActivity.loadLayout;
import static com.asafNilia.thedailygamer.Activities.MainActivity.mainLayout;

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
    private String mGameDescription;

    private VideoView Video;
    private ImageView Image;
    private TextView  Title;
    private TextView  Description;

    private ImageButton buyNowButton;


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


    private void fillGameItemData(final String sourceCode) throws IOException {

        Pattern patternForAgeVerified = Pattern.compile("agecheck"); /** check if there ia agecheck in the source code */
        final Matcher matcherForAgeVerified = patternForAgeVerified.matcher(sourceCode);

                if(matcherForAgeVerified.find())
                {
                    Video.setVisibility(View.INVISIBLE);
                    Image.setVisibility(View.VISIBLE);
                    getDataForProtectedGame(sourceCode); /** case when game is age protected  */
                }

                else
                {
                    Video.setVisibility(View.VISIBLE);
                    Image.setVisibility(View.INVISIBLE);
                    getDataForUnprotectedGame(sourceCode);/** case when game is not age protected  */
                }
            }

    private void getDataForProtectedGame(String sourceCode) {
        Pattern patternForDescription = Pattern.compile("Description\" content=\"(.*?)\">");
        Pattern patternForTitle = Pattern.compile("<meta property=\"og:title\" content=\"(.*?)\">");
        Pattern patternForImage = Pattern.compile("image_src\" href=\"(.*?)\">");

        final Matcher matcherForDescription = patternForDescription.matcher(sourceCode);
        final Matcher matcherForTitle = patternForTitle.matcher(sourceCode);
        final Matcher matcherForImage = patternForImage.matcher(sourceCode);

                if(matcherForDescription.find())
                {
                    mGameDescription = matcherForDescription.group(1);
                    mGameDescription = Jsoup.parse(mGameDescription).text();
                    String withNewLines = mGameDescription.replaceAll("\\.","\\.\n\n");
                    Description.setText(withNewLines);
                }

                if(matcherForTitle.find())
                {
                    mGameTitle = matcherForTitle.group(1);
                    Title.setText(mGameTitle);
                }

                if(matcherForImage.find())
                {
                    String url = matcherForImage.group(1);
                    Picasso.get().load(url).into(Image);
                }
    }

    private void getDataForUnprotectedGame(String sourceCode) {
        Pattern patternForVideo = Pattern.compile("data-webm-source=\"(.*?)\"");
        Pattern patternForTitle = Pattern.compile("<title>(.*?) on Steam</title>");
        Pattern patternForDescription = Pattern.compile("(?s)(?<=<h2>)About This Game(?=</h2>)(.+?)<div class=\"");

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
            mGameDescription = Jsoup.parse(mGameDescription).text();
            String withNewLines = mGameDescription.replaceAll("\\.","\\.\n\n");
            Description.setText(withNewLines);

        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        Video = (VideoView) getView().findViewById(R.id.gameVideo);
        Image = (ImageView) getView().findViewById(R.id.gameImage);
        Title = (TextView) getView().findViewById(R.id.gameTitle);
        loadLayout = view.findViewById(R.id.loadView);
        mainLayout = view.findViewById(R.id.mainView);
        //MainActivity.buyNow = view.findViewById(R.id.buyNow);


        MainActivity.buyNow.setVisibility(View.VISIBLE);
        MainActivity.textSearchField.setVisibility(View.GONE);
        loadLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);


        //TODO Here we access the current store url
        Ion.with(getContext()).load(MainActivity.storeUrl).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    fillGameItemData(result); /** fills all needed info according to html source */
                    loadLayout.setVisibility(View.INVISIBLE); /** make loading invisible */
                    mainLayout.setVisibility(View.VISIBLE); /** make rest of the fragment visible */
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        Description = (TextView) getView().findViewById(R.id.gameDescription);
        Description.setMovementMethod(new ScrollingMovementMethod());



        //comments just for a reminder:
        //private TextView gameReleaseDate;
        //private TextView gamePrice;
    }
}
