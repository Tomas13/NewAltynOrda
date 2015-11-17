package kz.growit.altynorda;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class ReserveActivity extends AppCompatActivity {
    private Button sendReserve;
    private EditText NumberOfPeople;
    int numberPeopleForReserve;
    JSONObject request, ListingObjectAll;
    ProgressView progressView;


    private String FromReserveString;
    private String UntilReserveString;

    private LinearLayout selectArrivalDateLinear;
    private LinearLayout selectDepartueDateLinear;
    private TextView arrivalDate;
    private TextView departureDate;
    private Dialog.Builder builder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);


        Bundle intentData = getIntent().getExtras();
        final int listingId = intentData.getInt("ListingIdFromListingRVAdapter");
        Toast.makeText(ReserveActivity.this, "" + listingId, Toast.LENGTH_SHORT).show();


        NumberOfPeople = (EditText) findViewById(R.id.NumberOfPeople);


        sendReserve = (Button) findViewById(R.id.sendReserve);

//        numberPeopleForReserve = 3;


        progressView = (ProgressView) findViewById(R.id.progressReserve);


        arrivalDate = (TextView) findViewById(R.id.arrivalDate);
        departureDate = (TextView) findViewById(R.id.departureDate);

        //Select arrival date
        selectArrivalDateLinear = (LinearLayout) findViewById(R.id.selectArrivalDateLinear);


        //Select departure date
        selectDepartueDateLinear = (LinearLayout) findViewById(R.id.selectDepartureDateLinear);

        selectArrivalDateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(0);
                onDatePick(v);

            }
        });

        selectDepartueDateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(1);
                onDatePick(v);

            }
        });




        sendReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FromReserveString == null) {
                    Snackbar.make(v, "Заполните дату заезда", Snackbar.LENGTH_LONG).show();
                } else if (UntilReserveString == null) {
                    Snackbar.make(v, "Заполните дату выезда", Snackbar.LENGTH_LONG).show();

                } else if (NumberOfPeople.getText().toString().isEmpty()) {
                    Snackbar.make(v, "Заполните поле количество человек", Snackbar.LENGTH_LONG).show();

                } else {
                    numberPeopleForReserve = Integer.parseInt(NumberOfPeople.getText().toString());
                    progressView.start();
                    postReserveRequest(FromReserveString, UntilReserveString, numberPeopleForReserve, listingId);
                }
            }
        });
    }


    private void postReserveRequest(String fromReserve, String untilReserve, int NumberOfPeople, int listingId) {
        String url = "http://altynorda.kz/ReservesAPI/RequestForReserve";


        request = new JSONObject();
        try {
            request.put("ListingId", listingId); //listingId   //нужно бандлом передавать из предыдущего активити
            request.put("FromReserve", fromReserve); //"18.11.2015");
            request.put("UntilReserve", untilReserve); //"19.11.2015");
            request.put("NumberPeopleForReserve", NumberOfPeople);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String token = loginPrefs.getString("Token", "not logged in");

        ListingObjectAll = new JSONObject();
        try {
            ListingObjectAll.put("token", "a95c508e-cc0f-40f5-8d71-391f83f451b4");
            ListingObjectAll.put("request", request);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                ListingObjectAll,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.getBoolean("success")) {
                                Toast.makeText(getApplicationContext(), response.getString("errors"), Toast.LENGTH_LONG).show();
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


    public void onDatePick(final View v) {

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;

        builder = new DatePickerDialog.Builder(isLightTheme ?
                R.style.Material_App_Dialog_DatePicker_Light : R.style.Material_App_Dialog_DatePicker) {
            @Override
            public void onPositiveActionClicked(com.rey.material.app.DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                if (v.getTag().equals(0)) {
                    arrivalDate.setText(date);
                    FromReserveString = date;
                } else {
                    departureDate.setText(date);
                    UntilReserveString = date;
                }
                Toast.makeText(v.getContext(), "Date is " + date, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }


            @Override
            public void onNegativeActionClicked(com.rey.material.app.DialogFragment fragment) {
                Toast.makeText(v.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment1 = DialogFragment.newInstance(builder);
        fragment1.show(getSupportFragmentManager(), null);
    }
}

