package kz.growit.altynorda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONObject;



public class SplashActivity extends AppCompatActivity {

    ProgressView progressView;
    String urlGetCities = "http://altynorda.kz/api/citiesapi/getcities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressView = (ProgressView) findViewById(R.id.progressSplash);
        progressView.start();

        CitiesRequest();
    }

    public void CitiesRequest() {
        JsonArrayRequest jsonArrayRequestSplash = new JsonArrayRequest(
                Request.Method.GET,
                urlGetCities,
                new JSONObject(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressView.stop();
                        String citiesToBundle = response.toString();

                        Intent goToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                        goToMainActivity.putExtra("citiesToBundle", citiesToBundle);
                        startActivity(goToMainActivity);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressView.stop();
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonArrayRequestSplash, "Cities Request");
    }
}