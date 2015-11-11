package kz.growit.altynorda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private Spinner listingType;
    private Spinner listingCity;
    private Spinner listingStatus;
    private Spinner currency;
    private EditText priceFrom;
    private EditText priceTo;
    private Button searchButtonSearchFragment;
    String city;
    ProgressView progressView;
    Spinner listingsResult;
    ArrayList<String> titles;

String title;

    JSONObject data;
    ArrayList<String> countries;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listingsResult = (Spinner) findViewById(R.id.listingsResult);
        progressView = (ProgressView) findViewById(R.id.progressSearchView);
        listingType = (Spinner) findViewById(R.id.listingType);
        listingCity = (Spinner) findViewById(R.id.listingCity);
        listingStatus = (Spinner) findViewById(R.id.listingStatus);
        currency = (Spinner) findViewById(R.id.currency);
        priceFrom = (EditText) findViewById(R.id.priceFrom);
        priceTo = (EditText) findViewById(R.id.priceTo);
        searchButtonSearchFragment = (Button) findViewById(R.id.searchButtonSearchFragment);

        initListingCitySpinner();
        initListingStatusSpinner();
        initCurrencySpinner();

        city = (String) listingCity.getSelectedItem();

        searchButtonSearchFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SearchActivity.this, city, Toast.LENGTH_SHORT).show();
                progressView.start();
                searchListingRequest();
            }
        });
    }


    public void searchListingRequest() {

        String url = "http://altynorda.kz/SearchAPI/Index?CityId=1";

        titles = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getJSONArray("listings").length() == 0) {
                                Snackbar.make(findViewById(R.id.frameLSearch), "Ничего не найдено", Snackbar.LENGTH_SHORT).show();
                                progressView.stop();
                            }
                            else {
                                String Listings = response.getJSONArray("listings").toString();
                                Intent goToSomeActivity = new Intent(SearchActivity.this, ResultsActivity.class);

                                progressView.stop();

                                //return a number of results
                                Toast.makeText(SearchActivity.this, "Найдено недвижимостей: " +
                                        response.getJSONArray("listings").length(), Toast.LENGTH_SHORT).show();

                                //go to page rendreing results
                                goToSomeActivity.putExtra("bundleSearchResults",Listings);
                                startActivity(goToSomeActivity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String errors = error.getMessage();
//                        Toast.makeText(SearchActivity.this, "Проблема с загрузкой данных" +
//                                "Пожалуйста проверьте соединение с сетью", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        progressView.stop();

                    }
                });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    //initialize currency spinner
    public void initListingStatusSpinner() {
        List<String> list = new ArrayList<String>();

        list.add(getResources().getString(R.string.rent));
        list.add(getResources().getString(R.string.sale));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listingStatus.setAdapter(dataAdapter);
    }

    //initialize listing status spinner
    public void initCurrencySpinner() {
        List<String> list = new ArrayList<String>();

        list.add(getResources().getString(R.string.tenge));
        list.add(getResources().getString(R.string.dollar));
        list.add(getResources().getString(R.string.euro));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(dataAdapter);
    }


    //initialize listing city spinner
    public void initListingCitySpinner() {
        List<String> list = new ArrayList<String>();

        list.add("Astana");
        list.add("Almaty");
        list.add("Taraz");
        list.add("Shymkent");
        list.add("Atyrau");
        list.add("Aktau");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listingCity.setAdapter(dataAdapter);
    }
}
