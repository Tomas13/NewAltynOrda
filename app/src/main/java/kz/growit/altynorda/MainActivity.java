package kz.growit.altynorda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import kz.growit.altynorda.adapters.ListingsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Spinner spinner;

    private Button FiltersButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_agencies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListingsList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences main = getSharedPreferences(BaseActivity.APP_PREFERENCES, MODE_PRIVATE);



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
        if(token!="not logged in"){
            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
        }

        AppController.getInstance().getDrawer(this, toolbar);


        List<String> list = new ArrayList<String>();
        list.add("Astana");
        list.add("Almaty");
        list.add("Taraz");
        list.add("Shymkent");
        list.add("Atyrau");
        list.add("Aktau");
//        final ArrayList<String> arrayList = new ;
//        for (int i = 0; i < arrayList.size(); i++) {
//            list.add(arrayList.get(i).getName());
//        }
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(MainActivity.this, AddPropertyActivity.class));
            }
        });
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
