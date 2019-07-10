package com.asafNilia.thedailygamer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asafNilia.thedailygamer.Adapters.smallItemAdapter;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_new_games, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOfNewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<GameItemSmall> listOfGameItems = new ArrayList<>();
        listOfGameItems.add(new GameItemSmall(R.drawable.home,"half life","1999","Vavle","Shooter"));
        listOfGameItems.add(new GameItemSmall(R.drawable.home,"half life 2","2004","Vavle","Shooter"));
        listOfGameItems.add(new GameItemSmall(R.drawable.home,"half life 3","never","Vavle","Shooter"));
        listOfGameItems.add(new GameItemSmall(R.drawable.home,"portal","2004","Vavle","puzzle"));

        getHtmlCode("http://www.google.com");


        mRecyclerView = view.findViewById(R.id.recyclerViewOfNewGames);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new smallItemAdapter(listOfGameItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return  view;
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

    void getHtmlCode(String url)
    {
        Ion.with(getContext()).load(url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                Log.d("source",result);
            }
        });
    }
}
