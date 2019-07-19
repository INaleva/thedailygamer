package com.asafNilia.thedailygamer.Adapters;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asafNilia.thedailygamer.Activities.MainActivity;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.Fragments.gameItem;
import com.asafNilia.thedailygamer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class smallItemAdapter extends RecyclerView.Adapter<smallItemAdapter.ViewHolder> {
    private ArrayList<GameItemSmall> mGameItemSmallList;
    private static FragmentTransaction fragmentTransaction;
    private GameItemSmall currentItem;

    public static class ViewHolder extends  RecyclerView.ViewHolder {

        public View view; /**these two variables are for the video playing*/
        public ClipData.Item item;
        public ImageView gameImage; /**some data variables */
        public ImageView gameFavorite;
        public TextView gameName;
        public TextView gameReleaseDate;
        public TextView gamePrice;
        public String gamePage;
        public Boolean isFavorite = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign actual items to the fragment's pieces
            view = itemView;
            gameImage = itemView.findViewById(R.id.gamePhoto);
            gameName = itemView.findViewById(R.id.gameName);
            gameReleaseDate = itemView.findViewById(R.id.gameReleaseDate);
            gamePrice = itemView.findViewById(R.id.gamePrice);
            gameFavorite = itemView.findViewById(R.id.gameFav);

            if (MainActivity.currentPage == 1) //set the first page according to the current one
                MainActivity.firstPageView.setVisibility(View.INVISIBLE);
                else
                MainActivity.firstPageView.setVisibility(View.VISIBLE);

            gameFavorite.setImageResource(R.drawable.img_heart_black);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_frame_layout, new gameItem())
                            .commit();
                    MainActivity.storeUrl = gamePage;
                    MainActivity.buyNow.setVisibility(View.VISIBLE);
                    MainActivity.pagesLayout.setVisibility(View.GONE);
                }
            });

            gameFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //like or unlike a game
                    if(isFavorite)//if liked add to the favorites array
                    {
                        isFavorite = false;
                        gameFavorite.setImageResource(R.drawable.img_heart_black);
                        getAdapterPosition();
                        //if (currentItem!=null)//TODO
                        //MainActivity.listOfFavorites.remove(currentItem);//remove from favorites array
                    }
                    else //if not liked, remove from the array
                    {
                        isFavorite = true;
                        gameFavorite.setImageResource(R.drawable.img_heart);
                        //if (currentItem!=null) //TODO
                        //MainActivity.listOfFavorites.add(currentItem);//add to the favorites array
                    }
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
        currentItem = mGameItemSmallList.get(i);

        viewHolder.gameReleaseDate.setText("Release date:\n"+currentItem.getmGameReleaseDate());
        viewHolder.gameName.setText(currentItem.getmGameName());
        //viewHolder.isFavorite = currentItem.getmIsFavorite();

        String fixedPrice = addDotAndCurrencySign(currentItem.getmGamePrice());
            if(fixedPrice.equals("0.0₪"))
        {
            viewHolder.gamePrice.setText("FREE TO PLAY");
            currentItem.setmGamePrice("FREE TO PLAY");

        }
        else
        {
            viewHolder.gamePrice.setText("Price: "+fixedPrice);
            currentItem.setmGamePrice("Price: "+fixedPrice);
        }
        viewHolder.gamePage = currentItem.getmGamePage();
        Picasso.get().load(currentItem.getImageResource()).into(viewHolder.gameImage);
        MainActivity.pagesLayout.setVisibility(View.VISIBLE);
        MainActivity.lastPageView.setText(""+MainActivity.lastPage);
        MainActivity.currentPageView.setText(""+MainActivity.currentPage);
        MainActivity.nextPageView.setText(""+MainActivity.nextPage);

    }

    private String addDotAndCurrencySign(String fullPrice) {
        try {
            int number = Integer.parseInt(fullPrice);
            int first = number / 100;
            int last = number % 100;
            String fixedPrice = Integer.toString(first) + "." + Integer.toString(last) + "₪";
            return fixedPrice;
        }
        catch (NumberFormatException e)
        {
            return "FREE TO PLAY";
        }



    }
    

    @Override
    public int getItemCount() {
        return mGameItemSmallList.size();
    }


}
