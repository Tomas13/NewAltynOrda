package kz.growit.altynorda.adapters;

/**
 * Created by Талгат on 14.10.2015.
 */

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

import kz.growit.altynorda.MainActivity;
import kz.growit.altynorda.R;
import kz.growit.altynorda.models.Listings;

public class ListingsRVAdapter extends RecyclerView.Adapter<ListingsRVAdapter.ListingsRVViewHolder> {
    private ArrayList<Listings> listings;
    private MainActivity activity;
    private Boolean isFavorite = false;

    public ListingsRVAdapter(ArrayList<Listings> listings, MainActivity activity) {
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
        Listings item = listings.get(position);
        holder.username.setText(item.getUsername());
        holder.username.setTextColor(activity.getResources().getColor(R.color.colorAccent));
        holder.address.setText(item.getAddress());
        holder.totalArea.setText(item.getTotalArea());
//        holder.RoomCount.setText(item.getRoomCount());
        holder.Price.setText(item.getPrice());
        holder.thumb.removeAllSliders();
        for (int i = 0; i < item.getAllPictures().size(); i++) {
            if (item.getAllPictures().get(i).getPictureSize() == 1) {
                DefaultSliderView dsv = new DefaultSliderView(activity.getApplicationContext());
                dsv.image("http://altynorda.kz" + item.getAllPictures().get(i).getImageUrl());
                holder.thumb.addSlider(dsv);
            }
        }


        holder.linearLayoutCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Cliked", Snackbar.LENGTH_SHORT).show();
            }
        });


        holder.favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isFavorite) {
                    // v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate));

                    holder.favorite.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    isFavorite = false;
                } else {
                    //v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate));

                    holder.favorite.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    isFavorite = true;
                }
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

        private LinearLayout linearLayoutCardLayout;

        public ListingsRVViewHolder(View itemView) {
            super(itemView);
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
