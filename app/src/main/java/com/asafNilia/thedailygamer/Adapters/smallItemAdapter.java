package com.asafNilia.thedailygamer.Adapters;

import android.content.ClipData;
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

import com.asafNilia.thedailygamer.Activities.MainActivity;
import com.asafNilia.thedailygamer.Classes.GameItemSmall;
import com.asafNilia.thedailygamer.Fragments.gameItem;
import com.asafNilia.thedailygamer.Fragments.menu;
import com.asafNilia.thedailygamer.Fragments.newGames;
import com.asafNilia.thedailygamer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.asafNilia.thedailygamer.Activities.MainActivity.isInFavorite;
import static com.asafNilia.thedailygamer.Activities.MainActivity.listOfFavorites;
import static com.asafNilia.thedailygamer.Activities.MainActivity.listOfGameItems;
import static com.asafNilia.thedailygamer.Activities.MainActivity.tags;

public class smallItemAdapter extends RecyclerView.Adapter<smallItemAdapter.ViewHolder> {
    private ArrayList<GameItemSmall> mGameItemSmallList;
    private GameItemSmall currentItem;

    public class ViewHolder extends  RecyclerView.ViewHolder {

        public View view; /**these two variables are for the video playing*/
        public ImageView gameImage; /**some data variables */
        public ImageView gameFavorite;
        public TextView gameName;
        public TextView gameReleaseDate;
        public TextView gamePrice;
        public String gamePage;
        public Boolean isFavorite = false;

        public ViewHolder(@NonNull final View itemView) {
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
                    int i = getAdapterPosition();

                    if(isFavorite) //already liked, need to remove from liked.
                    {
                        isFavorite = false;
                        gameFavorite.setImageResource(R.drawable.img_heart_black);
                        listOfGameItems.get(i).setmIsFavorite(false);

                        //need to check if we are in the same list, list (that shows all games) or we are in the favorites list, to know how to delete
                        listOfFavorites.remove(listOfGameItems.get(i)); //remove from favorites
                        if (tags.equals("1")) {
                            listOfGameItems.remove(listOfGameItems.get(i)); //remove from current list
                            //remove from recycler view
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), listOfGameItems.size());
                            notifyDataSetChanged();
                        }
                    }
                    else //is not liked, need to add to like
                    {
                        isFavorite = true;
                        listOfGameItems.get(i).setmIsFavorite(true);
                        gameFavorite.setImageResource(R.drawable.img_heart);
                        listOfFavorites.add(listOfGameItems.get(i));//add to the favorites array
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

        for(int j = 0; j < listOfFavorites.size(); j++)
        {
            if(listOfFavorites.get(j).getmGameName().equals(currentItem.getmGameName()))
            {
                viewHolder.isFavorite = true;
                currentItem.setmIsFavorite(true);
            }
        }

        if(currentItem.getmIsFavorite())
        {
            viewHolder.gameFavorite.setImageResource(R.drawable.img_heart);
        }
        else
        {
            viewHolder.gameFavorite.setImageResource(R.drawable.img_heart_black);
        }

        String fixedPrice = addDotAndCurrencySign(currentItem.getmGamePrice());
            if(fixedPrice.equals("0.0₪"))
        {
            viewHolder.gamePrice.setText("FREE TO PLAY");

        }
        else
        {
            viewHolder.gamePrice.setText("Price: "+fixedPrice);
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

