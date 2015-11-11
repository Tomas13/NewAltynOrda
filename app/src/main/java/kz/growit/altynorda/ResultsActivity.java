package kz.growit.altynorda;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.growit.altynorda.adapters.ListingsRVAdapter;
import kz.growit.altynorda.models.Listings;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Listings> listings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Bundle intentData = getIntent().getExtras();
        if (intentData!=null){
            String ListingsArrayString = intentData.getString("bundleSearchResults");
            try {
                JSONArray ListingJsonArray = new JSONArray(ListingsArrayString);
                for (int i = 0; i < ListingJsonArray.length(); i++) {
                    JSONObject listingJsonObject = ListingJsonArray.getJSONObject(i);
                    Listings listing = new Listings(listingJsonObject);
                    listings.add(listing);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.activityResultsRV);


        setMyListings(listings);

        ListingsRVAdapter myAdapter = new ListingsRVAdapter(getMyListings(), ResultsActivity.this);

        //set number of columns depending on orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);




    }

    private ArrayList<Listings> myListings;

    public ArrayList<Listings> getMyListings() {
        return myListings;
    }

    public void setMyListings(ArrayList<Listings> myListings) {
        this.myListings = myListings;
    }
}
