package com.asafNilia.thedailygamer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asafNilia.thedailygamer.R;

import java.util.ArrayList;

public class gameItemAdapter extends RecyclerView.Adapter<gameItemAdapter.ViewHolder>{

    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mCreator = new ArrayList<>();
    private ArrayList<String> mGenre = new ArrayList<>();
    private Context mContext;

    public gameItemAdapter(ArrayList<String> mImages, ArrayList<String> mNames,
                           ArrayList<String> mDates, ArrayList<String> mCreator,
                           ArrayList<String> mGenre, Context mContext) {
        this.mImages = mImages;
        this.mNames = mNames;
        this.mDates = mDates;
        this.mCreator = mCreator;
        this.mGenre = mGenre;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_game_in_list, viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int index) {
        //here we also get the bitmaps using some library like glide

        viewHolder.textName.setText(mNames.get(index));
        viewHolder.textReleaseDate.setText(mDates.get(index));
        viewHolder.textCreator.setText(mCreator.get(index));
        viewHolder.textGenre.setText(mGenre.get(index));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we will go to the fragment screen dedicated for the game expanded info
                Toast.makeText(mContext,mNames.get(index),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Declaring stored data
        ImageView imageView;
        TextView textName;
        TextView textReleaseDate;
        TextView textCreator;
        TextView textGenre;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gamePhoto);
            textName = itemView.findViewById(R.id.gameName);
            textReleaseDate = itemView.findViewById(R.id.gameReleaseDate);
            textCreator = itemView.findViewById(R.id.gameCreator);
            textGenre = itemView.findViewById(R.id.gameGenre);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
