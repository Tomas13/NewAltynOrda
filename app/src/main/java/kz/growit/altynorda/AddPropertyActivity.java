package kz.growit.altynorda;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPropertyActivity extends AppCompatActivity {

    private String Title, Description, Address;
    private int Price, ConstructionYear, RoomCount, CeilingHeight,
            Floor, TotalFloors, TotalArea, KitchenArea, CityId,
            PropertyTypeId, ListingStatusId;

    private EditText TitleEdit, PriceEdit, AddressEdit, DescriptionEdit,
            ConstructionYearEdit, RoomCountEdit, CeilingHeightEdit, FloorEdit,
            TotalFloorsEdit, TotalAreaEdit, KitchenAreaEdit, CityIdEdit, PropertyTypeIdEdit,
            ListingStatusIdEdit;

    private Button SendProperty;
    private ProgressView progressView;

    private String token;
    private JSONObject ListingObject;
    private JSONObject ListingObjectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        TitleEdit = (EditText) findViewById(R.id.Title);
        PriceEdit = (EditText) findViewById(R.id.Price);
        AddressEdit = (EditText) findViewById(R.id.Address);
        DescriptionEdit = (EditText) findViewById(R.id.Description);
        ConstructionYearEdit = (EditText) findViewById(R.id.ConstructionYear);
        RoomCountEdit = (EditText) findViewById(R.id.RoomCount);
        CeilingHeightEdit = (EditText) findViewById(R.id.CeilingHeight);
        FloorEdit = (EditText) findViewById(R.id.Floor);
        TotalFloorsEdit = (EditText) findViewById(R.id.TotalFloors);
        TotalAreaEdit = (EditText) findViewById(R.id.TotalArea);
        KitchenAreaEdit = (EditText) findViewById(R.id.KitchenArea);
        CityIdEdit = (EditText) findViewById(R.id.CityId);
        PropertyTypeIdEdit = (EditText) findViewById(R.id.PropertyTypeId);
        ListingStatusIdEdit = (EditText) findViewById(R.id.ListingStatusId);

        SendProperty = (Button) findViewById(R.id.SendProperty);
        progressView = (ProgressView) findViewById(R.id.progressAddProperty);

        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        token = loginPrefs.getString("Token", "not logged in");


        SendProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = TitleEdit.getText().toString();
                Address = AddressEdit.getText().toString();
                Description = DescriptionEdit.getText().toString();
                Price = Integer.parseInt(PriceEdit.getText().toString());
                ConstructionYear = Integer.parseInt(ConstructionYearEdit.getText().toString());
                RoomCount = Integer.parseInt(RoomCountEdit.getText().toString());
                CeilingHeight = Integer.parseInt(CeilingHeightEdit.getText().toString());
                Floor = Integer.parseInt(FloorEdit.getText().toString());
                TotalFloors = Integer.parseInt(TotalFloorsEdit.getText().toString());
                TotalArea = Integer.parseInt(TotalAreaEdit.getText().toString());
                KitchenArea = Integer.parseInt(KitchenAreaEdit.getText().toString());
                CityId = Integer.parseInt(CityIdEdit.getText().toString());
                PropertyTypeId = Integer.parseInt(PropertyTypeIdEdit.getText().toString());
                ListingStatusId = Integer.parseInt(ListingStatusIdEdit.getText().toString());

                ListingObject = new JSONObject();
                try {
                    ListingObject.put("Title", Title);
                    ListingObject.put("Address", Address);
                    ListingObject.put("Description", Description);
                    ListingObject.put("Price", Price);
                    ListingObject.put("ConstructionYear", ConstructionYear);
                    ListingObject.put("RoomCount", RoomCount);
                    ListingObject.put("CeilingHeight", CeilingHeight);
                    ListingObject.put("Floor", Floor);
                    ListingObject.put("TotalFloors", TotalFloors);
                    ListingObject.put("TotalArea", TotalArea);
                    ListingObject.put("KitchenArea", KitchenArea);
                    ListingObject.put("CityId", CityId);
                    ListingObject.put("ExchangeId", 1);
                    ListingObject.put("PropertyTypeId", PropertyTypeId);
                    ListingObject.put("ListingStatusId", ListingStatusId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ListingObjectAll = new JSONObject();
                try {
                    ListingObjectAll.put("token", token);
                    ListingObjectAll.put("listing", ListingObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                progressView.start();
                sendPropertyRequest(ListingObjectAll);


            }
        });
    }


    private void sendPropertyRequest(JSONObject listingModel) {

        String url = "http://altynorda.kz/ListingsAPI/PostListing";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                listingModel,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.getBoolean("success")) {
                                Toast.makeText(getApplicationContext(), "Отправка успешна", Toast.LENGTH_LONG).show();
//                                Snackbar.make(findViewById(R.id.SendProperty), "Авторизация успешна", Snackbar.LENGTH_LONG).show();
                                progressView.stop();
//                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                            } else {

                                if (!response.getString("errors").isEmpty()) {
                                    String errors = response.getString("errors");
                                    Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_LONG).show();
//                                    Snackbar.make(findViewById(R.id.SendProperty), "Авторизация успешна", Snackbar.LENGTH_LONG).show();
                                    progressView.stop();
                                }

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
                        Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_LONG).show();
                        progressView.stop();
                    }
                });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}

