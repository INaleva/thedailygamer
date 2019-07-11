package com.asafNilia.thedailygamer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asafNilia.thedailygamer.Activities.MainActivity;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class smallItemAdapter extends RecyclerView.Adapter<smallItemAdapter.ViewHolder> {
    private ArrayList<GameItemSmall> mGameItemSmallList;



    public static class ViewHolder extends  RecyclerView.ViewHolder {

        public ImageView gameImage;
        public TextView gameName;
        public TextView gameReleaseDate;
        public TextView gameCreator;
        public TextView gameGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.gamePhoto);
            gameName = itemView.findViewById(R.id.gameName);
            gameReleaseDate = itemView.findViewById(R.id.gameReleaseDate);
            gameCreator = itemView.findViewById(R.id.gamePrice);
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

        //viewHolder.gameImage.setImageBitmap(currentItem.getBitmap());
        viewHolder.gameCreator.setText(currentItem.getmGameCreator());
        viewHolder.gameReleaseDate.setText(currentItem.getmGameReleaseDate());
        viewHolder.gameName.setText(currentItem.getmGameName());
        Picasso.get().load(currentItem.getImageResource()).into(viewHolder.gameImage);
    }

    @Override
    public int getItemCount() {
        return mGameItemSmallList.size();
    }


}
