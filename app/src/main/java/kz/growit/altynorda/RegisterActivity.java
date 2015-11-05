package kz.growit.altynorda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    //private SessionManager session;
    private String url = "http://altynorda.kz/SGAccountAPI/Register";
    private ProgressView progressView;

    private CheckBox privateCheckBoxRegister;
    private CheckBox agentCheckBoxRegister;
    private CheckBox agencyCheckBoxRegister;

    private String AccountType;

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.privateCheckRegister:
                if (checked)
                    AccountType = "1";
                break;
            case R.id.agentCheckRegister:
                if (checked)
                    AccountType = "2";
                break;
            case R.id.agencyCheckRegister:
                if (checked)
                    AccountType = "3";
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        progressView = (ProgressView) findViewById(R.id.progressRegisterView);

        privateCheckBoxRegister = (CheckBox) findViewById(R.id.privateCheckRegister);
        agencyCheckBoxRegister = (CheckBox) findViewById(R.id.agencyCheckRegister);
        agentCheckBoxRegister = (CheckBox) findViewById(R.id.agentCheckRegister);


//        View view = LayoutInflater
//                .from(getApplicationContext())
//                .inflate(R.layout.activity_register, RegisterActivity.this, false);

//        onCheckboxClicked(view);
//        Toast.makeText(getApplicationContext(), AccountType, Toast.LENGTH_SHORT).show();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
//        session = new SessionManager(getApplicationContext());


        // Check if user is already logged in or not
//        if (session.isLoggedIn()) {
//            // User is already logged in. Take him to main activity
//            Intent intent = new Intent(RegisterActivity.this,
//                    MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
//                String password = inputPassword.getText().toString().trim();
                String accountType = "1";

                if (!name.isEmpty() && !email.isEmpty()) { //&& !password.isEmpty()) {
                    //        registerUser(name, email, password);
                    Snackbar.make(findViewById(R.id.LLRegister),
                            "Clicked!", Snackbar.LENGTH_LONG)
                            .show();

                    progressView.start();

                    registrationPostRequest(name, email, accountType);


                } else {
                    Snackbar.make(findViewById(R.id.LLRegister),
                            "Please enter your details!", Snackbar.LENGTH_LONG)
                            .show();
                }


            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void registrationPostRequest(final String name, final String email, final String accountType) {



        JSONObject data = new JSONObject();
        try {
            data.put("UserName", name);
            data.put("Email", email);
            data.put("AccountType", accountType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "server response is : " + response, Toast.LENGTH_LONG).show();

                        try {
                            if (response.getBoolean("success")){
                                startActivity(new Intent(
                                        RegisterActivity.this,
                                        PhoneConfirmationActivity.class));
                            }else{
                                String error = response.getString("errors");
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "server response is : " + error, Toast.LENGTH_LONG).show();
                        String json = null;

                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
//                            switch (response.statusCode) {
//                                case 400:
                                    json = new String(response.data);
                                    json = trimMessage(json, "message");
                                    if (json != null) {
                                        Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
                                        displayMessage(json);
                                    }
//                                    break;
                            }
                            //Additional cases
                        }
//                    }
                });
        progressView.stop();
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    //Somewhere that has access to a context
    public void displayMessage(String toastString){
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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


//    *//**
//     * Function to store user in MySQL database will post params(tag, name,
//     * email, password) to register url
//     * *//*
//    private void registerUser(final String name, final String email, final Integer accountType) {
//        // Tag used to cancel the request
//
//        JSONObject data = new JSONObject();
//        try {
//            data.put("Mobile",name);
//            data.put("Email", email);
//            data.put("AccountType", accountType);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        String tag_string_req = "req_register";
//
//        pDialog.setMessage("Registering ...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    if (!error) {
//                        JSONObject user = jObj.getJSONObject("user");
//                        String name = user.getString("name");
//                        String email = user.getString("email");
//
//
//                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
//
//                        // Launch login activity
//                        Intent intent = new Intent(
//                                RegisterActivity.this,
//                                LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//
//                        // Error occurred in registration. Get the error
//                        // message
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
//                params.put("email", email);
////                params.put("password", password);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }


}
