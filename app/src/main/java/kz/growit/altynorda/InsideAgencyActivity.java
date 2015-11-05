package kz.growit.altynorda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.mikepenz.materialdrawer.Drawer;

import kz.growit.altynorda.models.Agencies;

public class InsideAgencyActivity extends AppCompatActivity {

    TextView txtAddress;
    TextView txtName;
    TextView txtLogin;
    NetworkImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_agency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        txtAddress = (TextView) findViewById(R.id.txtAddress);
//        txtName = (TextView) findViewById(R.id.txtAgencyName);
//        txtLogin = (TextView) findViewById(R.id.txtLogin);
//        imageView = (NetworkImageView) findViewById(R.id.imageContentInside);


        Intent intent = getIntent();
        int i = 0;
        i = intent.getExtras().getInt("index");

//        Agencies agencies = AppController.getInstance().getAgencies().get(i);
        Toast.makeText(getApplicationContext(), i + "", Toast.LENGTH_LONG).show();
//        txtAddress.setText(agencies.getAddress());
//        txtName.setText(agencies.getAgencyname());
//        txtLogin.setText(agencies.getLogin());
//        //imageView.setImageIcon(agencies.getImageFileName());
//        String url = AppController.SERVERURL + agencies.getImageFileName();
//        imageView.setImageUrl(url, AppController.getInstance().getImageLoader());
//        Drawer drawer = AppController.getInstance().getDrawer(this, toolbar);



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
