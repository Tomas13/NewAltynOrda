package kz.growit.altynorda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kz.growit.altynorda.adapters.PagerAdapter;
import kz.growit.altynorda.fragments.ListAgenciesFragment;
import kz.growit.altynorda.fragments.ListAgencyFragment;

public class AgenciesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppController.getInstance().getDrawer(this, toolbar);

        //viewpager is located in activity_list_agent which opens for agent lists from nav.drawer
        ViewPager viewPager = (ViewPager) findViewById(R.id.id_viewpager);

        //Here we add two fragments
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListAgenciesFragment(), "Список агенств");
        adapter.addFragment(new ListAgencyFragment(), "Список частных агентов");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tabs);
        tabLayout.setupWithViewPager(viewPager);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
