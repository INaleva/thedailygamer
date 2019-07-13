package com.asafNilia.thedailygamer.Adapters;

import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asafNilia.thedailygamer.Activities.MainActivity;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.Fragments.gameItem;
import com.asafNilia.thedailygamer.Fragments.newGames;
import com.asafNilia.thedailygamer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class smallItemAdapter extends RecyclerView.Adapter<smallItemAdapter.ViewHolder> {
    private ArrayList<GameItemSmall> mGameItemSmallList;
    private static FragmentTransaction fragmentTransaction;

    public static class ViewHolder extends  RecyclerView.ViewHolder {

        public View view; /**these two variables are for the video playing*/
        public ClipData.Item item;
        public ImageView gameImage; /**some data variables */
        public TextView gameName;
        public TextView gameReleaseDate;
        public TextView gamePrice;
        public String gamePage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            gameImage = itemView.findViewById(R.id.gamePhoto);
            gameName = itemView.findViewById(R.id.gameName);
            gameReleaseDate = itemView.findViewById(R.id.gameReleaseDate);
            gamePrice = itemView.findViewById(R.id.gamePrice);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_frame_layout, new gameItem())
                            .commit();
                    MainActivity.storeUrl = gamePage;
                    MainActivity.buyNow.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public smallItemAdapter(ArrayList<GameItemSmall> smallList){
        mGameItemSmallList = smallList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_game_in_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GameItemSmall currentItem = mGameItemSmallList.get(i);

        //viewHolder.gameCreator.setText(currentItem.getmGameCreator());
        viewHolder.gameReleaseDate.setText(currentItem.getmGameReleaseDate());
        viewHolder.gameName.setText(currentItem.getmGameName());
        viewHolder.gamePrice.setText(currentItem.getmGamePrice());
        viewHolder.gamePage = currentItem.getmGamePage();
        Picasso.get().load(currentItem.getImageResource()).into(viewHolder.gameImage);

    }

    @Override
    public int getItemCount() {
        return mGameItemSmallList.size();
    }


}
