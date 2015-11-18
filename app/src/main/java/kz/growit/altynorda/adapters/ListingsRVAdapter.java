package kz.growit.altynorda.adapters;

/**
 * Created by Талгат on 14.10.2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.Iterator;

import kz.growit.altynorda.InsideListingActivity;
import kz.growit.altynorda.LoginActivity;
import kz.growit.altynorda.R;
import kz.growit.altynorda.models.Listings;

public class ListingsRVAdapter extends RecyclerView.Adapter<ListingsRVAdapter.ListingsRVViewHolder> {
    private ArrayList<Listings> listings;
    private Activity activity;
    private Boolean isFavorite = false;

    private ArrayList<Integer> favoritesId = new ArrayList<>();

    public ListingsRVAdapter(ArrayList<Listings> listings, Activity activity) {
        this.activity = activity;
        this.listings = listings;
    }

    @Override
    public ListingsRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.my_listing_card_layout, parent, false);


        return new ListingsRVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListingsRVViewHolder holder, int position) {
        final Listings item = listings.get(position);
        holder.username.setText(item.getUsername());
        holder.username.setTextColor(activity.getResources().getColor(R.color.colorAccent));
        holder.address.setText(item.getAddress());
        holder.totalArea.setText(item.getTotalArea());
//        holder.RoomCount.setText(item.getRoomCount());
        holder.Price.setText(item.getPrice());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToInside = new Intent(v.getContext(), InsideListingActivity.class);
                goToInside.putExtra("index", holder.Id);
                goToInside.putExtra("callNumber", item.getUsername());
                v.getContext().startActivity(goToInside);
            }
        });

        holder.Id = item.getId();
        holder.thumb.removeAllSliders();
        for (int i = 0; i < item.getAllPictures().size(); i++) {
            if (item.getAllPictures().get(i).getPictureSize() == 1) {
                DefaultSliderView dsv = new DefaultSliderView(activity.getApplicationContext());
                dsv.image("http://altynorda.kz" + item.getAllPictures().get(i).getImageUrl());
                holder.thumb.addSlider(dsv);
            }
        }


//        holder.linearLayoutCardLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Intent intent = new Intent(v.getContext(), InsideListingActivity.class);
////                intent.putExtra("IdToInside", holder.Id);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                v.getContext().getApplicationContext().startActivity(intent);
//                Snackbar.make(v, "Cliked", Snackbar.LENGTH_SHORT).show();
//            }
//        });


        favoritesId.add(26);
        favoritesId.add(28);


        holder.favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//                int id = holder.Id;

                if (isFavorite) {

//                    favoritesId.add(id);


                    SharedPreferences sp = activity.getSharedPreferences("FavoritesPrefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("favoriteId", holder.Id);
                    editor.commit();

                    holder.favorite.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    isFavorite = false;
                } else {

//                    Iterator<Integer> iterator = favoritesId.iterator();
//                    while(iterator.hasNext())
//                    {
//                        int value = iterator.next();
//                        if (id == value) {
//                            iterator.remove();
//                            break;
//                        }
//                    }

                    SharedPreferences sp = activity.getSharedPreferences("FavoritesPrefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("favoriteId");
                    editor.commit();

                    holder.favorite.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    isFavorite = true;
                }


//                String favorites = favoritesId.toString();
//
//                SharedPreferences sp = activity.getSharedPreferences("FavoritesPrefs", Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("favoriteId", favorites);
//                editor.commit();
            }
        });


//        holder.thumb.setImageUrl("http://altynorda.kz"+item.getAllPictures().get(0).getImageUrl(), AppController.getInstance().getImageLoader());
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public static class ListingsRVViewHolder extends RecyclerView.ViewHolder {
        private TextView username, address, totalArea, RoomCount, Price;
        private SliderLayout thumb;
        private ImageButton favorite;
        private CardView cardView;
        private int Id;

        private LinearLayout linearLayoutCardLayout;

        public ListingsRVViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view_my_listing_card_layout);
            thumb = (SliderLayout) itemView.findViewById(R.id.thumbnailImageViewListingRow);
            username = (TextView) itemView.findViewById(R.id.usernameTVListingRow);
            address = (TextView) itemView.findViewById(R.id.addressTextViewListingRow);
            totalArea = (TextView) itemView.findViewById(R.id.areaTextViewListingRow);
            //  RoomCount = (TextView) itemView.findViewById(R.id.roomsTextViewListingRow);
            Price = (TextView) itemView.findViewById(R.id.priceTextViewListingRow);
            favorite = (ImageButton) itemView.findViewById(R.id.favorite);


            linearLayoutCardLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutCardLayout);
        }
    }
}
