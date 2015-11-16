package kz.growit.altynorda;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.growit.altynorda.adapters.ListingsRVAdapter;
import kz.growit.altynorda.models.Listings;

public class FavoritesActivity extends AppCompatActivity {

    private ArrayList<Listings> listings = new ArrayList<>();
    private ProgressView progressView;
    private RecyclerView recyclerView;
    JSONObject FavoriteJsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        progressView = (ProgressView) findViewById(R.id.progressFavoritesView);
        recyclerView = (RecyclerView) findViewById(R.id.activityFavoritesRV);

        SharedPreferences favorites = getSharedPreferences("FavoritesPrefs", MODE_PRIVATE);

        int FavId = favorites.getInt("favoriteId", -1);
        refreshFavoritesList(FavId);

        progressView.stop();

//        String fav = favorites.getString("favoriteId", "");

//        try {
//            FavoriteJsonArray = new JSONObject(fav);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < FavoriteJsonArray.length(); i++) {

        }
//        int id = favorites.getInt("favoriteId", 0);

//        if(id!=0){
//            refreshFavoritesList(id);
//        }






    private void refreshFavoritesList(int id) {
        progressView.start();

        if(id == -1){
            Toast.makeText(FavoritesActivity.this, "У вас нет избранных недвижимостей", Toast.LENGTH_SHORT).show();

            return;
        }

        String url = "http://altynorda.kz/api/ListingsAPI/GetListing?id=" + id ;
        JsonObjectRequest getListingsById = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                                Listings listing = new Listings(response);
                                listings.add(listing);


                        setMyListings(listings);
                        ListingsRVAdapter myAdapter = new ListingsRVAdapter(listings, FavoritesActivity.this);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));


                        //set number of columns depending on orientation
                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        }
                        else{
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        }

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(myAdapter);


                        progressView.stop();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getListingsById, "get listings");
    }


    private ArrayList<Listings> myListings;

    public ArrayList<Listings> getMyListings() {
        return myListings;
    }

    public void setMyListings(ArrayList<Listings> myListings) {
        this.myListings = myListings;
    }

}
