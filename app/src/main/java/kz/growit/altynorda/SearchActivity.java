package kz.growit.altynorda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private EditText fromPrice;
    private EditText untilPrice;
    private Button searchButtonSearchFragment;
    private ProgressView progressView;
    private Spinner listingsResult;
    private ArrayList<String> titles;
    private String city;
    private int cityId = 1;
    private String PropertyType;
    private int PropertyTypeId; //1 - for sale, 2 - for rent
    private String Exchange;
    private int ExchangeId;
    private ArrayList<String> listStatus;
    private Integer fromPriceInt;
    private Integer untilPriceInt;
    private String url;

String title;

    JSONObject data;
    ArrayList<String> countries;
    ArrayAdapter<String> adapter;

    String Status;
    int StatusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        listingsResult = (Spinner) findViewById(R.id.listingsResult);
        progressView = (ProgressView) findViewById(R.id.progressSearchView);
        listingType = (Spinner) findViewById(R.id.listingType);
        listingCity = (Spinner) findViewById(R.id.listingCity);
        listingStatus = (Spinner) findViewById(R.id.listingStatus);
        currency = (Spinner) findViewById(R.id.exchange);
        fromPrice = (EditText) findViewById(R.id.priceFrom);
        untilPrice = (EditText) findViewById(R.id.priceTo);
        searchButtonSearchFragment = (Button) findViewById(R.id.searchButtonSearchFragment);

        initListingCitySpinner();
        initListingStatusSpinner();
        initCurrencySpinner();



        city = (String) listingCity.getSelectedItem();

        listingCity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                city = (String) listingCity.getSelectedItem();
                //Toast.makeText(SearchActivity.this, "pos: " + position, Toast.LENGTH_SHORT).show();
            }
        });


//        PropertyType = (String) listin.getSelectedItem();
//
//        listingCity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(Spinner parent, View view, int position, long id) {
//                city = (String) listingCity.getSelectedItem();
//                //Toast.makeText(SearchActivity.this, "pos: " + position, Toast.LENGTH_SHORT).show();
//            }
//        });

        Exchange = (String) currency.getSelectedItem();

        currency.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                Exchange = (String) currency.getSelectedItem();
                //Toast.makeText(SearchActivity.this, "pos: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        Status = (String) listingStatus.getSelectedItem();

        listingStatus.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                Status = (String) listingStatus.getSelectedItem();
//                Toast.makeText(SearchActivity.this, "Status: " + Status, Toast.LENGTH_SHORT).show();
            }
        });

        searchButtonSearchFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(untilPrice.getText().toString().equals("") && fromPrice.getText().toString().equals("")){

//                    Toast.makeText(SearchActivity.this, "Both are empty", Toast.LENGTH_SHORT).show();

                    untilPriceInt = -1; fromPriceInt=-1;

                }else if(untilPrice.getText().toString().equals("")){
                    untilPriceInt = -1;
                    fromPriceInt = Integer.parseInt(fromPrice.getText().toString());
//                    Toast.makeText(SearchActivity.this, "priceTo is empty " + fromPriceInt, Toast.LENGTH_SHORT).show();

                }else if(fromPrice.getText().toString().equals("")) {

                    fromPriceInt = -1;
                    untilPriceInt = Integer.parseInt(untilPrice.getText().toString());
//                    Toast.makeText(SearchActivity.this, "priceFrom is empty " + untilPriceInt, Toast.LENGTH_SHORT).show();
                }else{
                    //both fields are filled
                    fromPriceInt = Integer.parseInt(fromPrice.getText().toString());
                    untilPriceInt = Integer.parseInt(untilPrice.getText().toString());

//                    Toast.makeText(SearchActivity.this, fromPriceInt + " + " + untilPriceInt, Toast.LENGTH_SHORT).show();

                }


                switch (Status){
                    case "Аренда": StatusId = 1; break;
                    case "Продажа": StatusId= 2; break;
                    default: StatusId = 1; break;
                }

                switch (Exchange){
                    case "₸": ExchangeId = 1; break;
                    case "$": ExchangeId= 2; break;
                    case "€": ExchangeId = 3; break;
                    default: ExchangeId = 1; break;
                }
//                Toast.makeText(SearchActivity.this, ExchangeId + " Exchange", Toast.LENGTH_SHORT).show();



                switch (city){
                    case "Astana": cityId = 1; break;
                    case "Almaty": cityId = 2; break;
                    case "Karaganda": cityId = 3; break;
                    case "Aktau": cityId = 4; break;
                    case "Zhezkazgan": cityId = 5; break;
                    default: cityId = -1; break;
                }
//                Toast.makeText(SearchActivity.this, cityId + "", Toast.LENGTH_SHORT).show();
//                progressView.start();


                searchListingRequest(cityId, fromPriceInt, untilPriceInt, ExchangeId, StatusId);
            }
        });
    }


    public void searchListingRequest(int cityId, int fromPriceSend, int untilPriceSend, int exchangeId, int StatusId) {

        if(fromPriceSend != -1 && untilPriceSend !=-1){
            url = "http://altynorda.kz/SearchAPI/Index?CityId=" + cityId + "&ExchangeId=" + exchangeId +
                    "&fromPrice=" + fromPriceSend + "&untilPrice=" + untilPriceSend + "&ListingStatusId=" + StatusId;
        }else if(fromPriceSend != -1){
            url = "http://altynorda.kz/SearchAPI/Index?CityId=" + cityId + "&ExchangeId=" + exchangeId +
                    "&fromPrice=" + fromPriceSend + "&ListingStatusId=" + StatusId;
        }else if (untilPriceSend != -1){
            url = "http://altynorda.kz/SearchAPI/Index?CityId=" + cityId + "&ExchangeId=" + exchangeId +
                    "&untilPrice=" + untilPriceSend + "&ListingStatusId=" + StatusId;
        }else{
            url = "http://altynorda.kz/SearchAPI/Index?CityId=" + cityId + "&ExchangeId=" + exchangeId + "&ListingStatusId=" + StatusId;
        }

//        String url = "http://altynorda.kz/SearchAPI/Index?CityId=" + cityId;

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


//    //initialize currency spinner
//    public void initListingStatusSpinner() {
//        List<String> list = new ArrayList<String>();
//
//        list.add(getResources().getString(R.string.rent));
//        list.add(getResources().getString(R.string.sale));
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        listingStatus.setAdapter(dataAdapter);
//    }

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


    //initialize listing type spinner
    public void initListingStatusSpinner() {
        listStatus = new ArrayList<String>();
        listStatus.add("Аренда");
        listStatus.add("Продажа");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listingStatus.setAdapter(dataAdapter);

//        String url = "http://altynorda.kz/api/ListingStatusesAPI/GetListingStatuses";
//
//        JsonArrayRequest listingStatusRequest = new JsonArrayRequest(Request.Method.GET,
//                url,
//                new JSONObject(),
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                listStatus.add(i, jsonObject.getString("Name"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                                    android.R.layout.simple_spinner_item, listStatus);
//                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            listingStatus.setAdapter(dataAdapter);
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
////                        NetworkResponse response = error.networkResponse;
//
//                        String errors = error.getMessage();
//                        Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_LONG).show();
//
//                    }
//                });
//        AppController.getInstance().addToRequestQueue(listingStatusRequest);




    }
}
