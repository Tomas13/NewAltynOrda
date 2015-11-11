package kz.growit.altynorda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kz.growit.altynorda.adapters.ListingRVAdapter;
import kz.growit.altynorda.adapters.ListingsRVAdapter;
import kz.growit.altynorda.models.Listings;

public class InsideListingActivity extends AppCompatActivity {

    private TextView username, description, address, totalArea, RoomCount, Price;
    private SliderLayout thumb;
    private ImageButton favorite;

    JSONObject data;

    EditText comment;
            Button sendComment;
    private int Id;
    private ArrayList<Listings> listings = new ArrayList<>();
    private ProgressView progressView;
    private int CityId;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_listing);


        final int intentData = getIntent().getIntExtra("index", 1);


        recyclerView = (RecyclerView) findViewById(R.id.activityInsideRV);

        progressView = (ProgressView) findViewById(R.id.progressInsideListingView);


        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MainActivity.MODE_PRIVATE);
        final String token =  loginPrefs.getString("Token", "");

        refreshList();


        comment = (EditText) findViewById(R.id.comment);
            sendComment = (Button) findViewById(R.id.sendComment);
            sendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), intentData + "", Toast.LENGTH_SHORT).show();
                    progressView.start();
                    postComment(comment.getText().toString(), intentData, token);
//                    Toast.makeText(v.getContext(), "sdf", Toast.LENGTH_SHORT).show();
                }
            });


        
//        Toast.makeText(InsideListingActivity.this, intentData, Toast.LENGTH_SHORT).show();
    }


    private void postComment(String comment, int id, String token ) {

        data = new JSONObject();

        try {
            data.put("Text", comment);
            data.put("ListingId", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject commentObject = new JSONObject();


        try {
            commentObject.put("Comment", data);
            commentObject.put("Token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "http://altynorda.kz/CommentsApi/SendComment";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                commentObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            msg = response.getString("errors");
//                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            if (response.getBoolean("success")) {


                                //getting the token and saving in SharedPrefs

//                                SharedPreferences loginPref = getSharedPreferences(BaseActivity.APP_PREFERENCES, MODE_PRIVATE);


//                                SharedPreferences.Editor editor = loginPrefs.edit();
//                                editor.putString(BaseActivity.GAME_PREFERENCES_TOKEN, token);
//                                editor.commit();


                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                progressView.stop();
//                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                            }else{

                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

//
//                                if(!response.getString("errors").isEmpty()){
//                                    String errors = response.getString("errors");
//                                    Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_SHORT).show();
//                                    progressView.stop();
//                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        NetworkResponse response = error.networkResponse;

                        String errors = error.getMessage();
                        Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_LONG).show();
                        progressView.stop();
                    }
                }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
    private ArrayList<Listings> myListings;

    public ArrayList<Listings> getMyListings() {
        return myListings;
    }

    public void setMyListings(ArrayList<Listings> myListings) {
        this.myListings = myListings;
    }


    private void refreshList() {
        progressView.start();


        Id = 30; //need to change according to spinner selection

        String url = "http://altynorda.kz/api/ListingsAPI/GetListing?id=30"; //+ Id ;
        JsonObjectRequest getListingsByCityReq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                                Listings listing = new Listings(response);
                                listings.add(listing);

                        setMyListings(listings);

                        ListingRVAdapter myAdapter = new ListingRVAdapter(getMyListings(), InsideListingActivity.this);

                        //set number of columns depending on orientation
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        } else {
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
        AppController.getInstance().addToRequestQueue(getListingsByCityReq, "get listings");


    }
}
