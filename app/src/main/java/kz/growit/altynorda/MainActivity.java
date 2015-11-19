package kz.growit.altynorda;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kz.growit.altynorda.adapters.ListingsPagerAdapter;
import kz.growit.altynorda.models.Cities;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Spinner spinner;
    MaterialDialog.Builder builder;
    private Button FiltersButton;
    private Boolean isLoggedIn = false;


    private ArrayList<Cities> citiesArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_agencies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListingsList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Bundle intentData = getIntent().getExtras();
        if (intentData!=null){
            String cities = intentData.getString("citiesToBundle");
            try {
                JSONArray citiesArray = new JSONArray(cities);
                for (int i = 0; i < citiesArray.length(); i++) {
                    JSONObject citiesObject = citiesArray.getJSONObject(i);
                    Cities citiesModel = new Cities(citiesObject);
                    citiesArrayList.add(i, citiesModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



//        SharedPreferences main = getSharedPreferences(BaseActivity.APP_PREFERENCES, MODE_PRIVATE);


        FiltersButton = (Button) findViewById(R.id.FiltersButton);
        FiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });


        spinner = (Spinner) findViewById(R.id.toolbarActivitySpinner);

        //just cheking for token. Need to DELETE this two lines
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MainActivity.MODE_PRIVATE);
//        Toast.makeText(getApplicationContext(), loginPrefs.getString("Token", "not logged in"), Toast.LENGTH_SHORT).show();


        String token = loginPrefs.getString("Token", "not logged in");
        if (token != "not logged in") {
            isLoggedIn = true;
            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
        } else {
            isLoggedIn = false;
            Toast.makeText(MainActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
        }

        AppController.getInstance().getDrawer(this, toolbar);


        List<String> list = new ArrayList<String>();

        for (int i = 0; i < citiesArrayList.size(); i++) {
            list.add(i, citiesArrayList.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpagerOneCard);
        viewPager.setAdapter(new ListingsPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });


        builder = new MaterialDialog.Builder(this)
                .title("Добавление недвижимости")
                .content("Чтобы добавить недвижимость " +
                        "вы должны быть зарегистрированы. Желаете перейти на" +
                        "страницу регистрации")
                .positiveText("Перейти")
                .negativeText("Закрыть")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        startActivity(new Intent(MainActivity.this, AddPropertyActivity.class));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });


        citiesArrayList = new ArrayList<>();
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
//                citiesArrayList.get(position)
                Toast.makeText(MainActivity.this, "Надо доделать еще здесь;)", Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isLoggedIn) {

                    MaterialDialog dialog = builder.build();
                    dialog.show();
                } else {

                    view.getContext().startActivity(new Intent(MainActivity.this, AddPropertyActivity.class));

                }
//                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();

//            }

            }
        });
    }



//    private void initCitySpinner() {
////        progressView.start();
//
//        String url = "http://altynorda.kz/api/citiesapi/getcities";
//        JsonObjectRequest getListingsById = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                new JSONObject(),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Listings listing = new Listings(response);
//                        listings.add(listing);
//
//
//                        setMyListings(listings);
//                        ListingsRVAdapter myAdapter = new ListingsRVAdapter(listings, FavoritesActivity.this);
////                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//
//
//                        //set number of columns depending on orientation
//                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
//                        }
//                        else{
//                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//                        }
//
//                        recyclerView.setHasFixedSize(true);
//                        recyclerView.setAdapter(myAdapter);
//
//
//                        progressView.stop();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                }
//        );
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(getListingsById, "get listings");
//    }

    private ArrayList<Cities> myCities;

    public ArrayList<Cities> getMyCities() {
        return myCities;
    }

    public void setMyCities(ArrayList<Cities> myCities) {
        this.myCities = myCities;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
