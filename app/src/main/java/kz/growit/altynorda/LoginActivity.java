package kz.growit.altynorda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    String msg;
    //private static final String TAG = RegisterActivity.class.getSimpleName();

    private Button btnLogin, btnLinkToForgetPassword, btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressView progressView;
    //private SessionManager session;
    private JSONObject data;

    private String token;
    private SharedPreferences loginPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPrefs = getSharedPreferences("LoginPrefs", LoginActivity.MODE_PRIVATE);


        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnLinkToForgetPassword = (Button) findViewById(R.id.btnLinkToForgetPassword);

        btnLinkToForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        progressView = (ProgressView) findViewById(R.id.progressLoginView);


        // Session manager
        //session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
//        if (session.isLoggedIn()) {
//            // User is already logged in. Take him to main activity
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                progressView.start();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {

                    loginPostRequest(email, password);

                } else {
                    progressView.stop();
                    // Prompt user to enter credentials

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.LLayout),
                            "Пожалуйста заполните все поля!", Snackbar.LENGTH_LONG);

                            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.color_snack));
                            snackbar.show();
                }


            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void loginPostRequest(String email, String password) {

        data = new JSONObject();

        try {
            data.put("UserName", email);
            data.put("Password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://altynorda.kz/SGAccountAPI/Login";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            msg = response.getString("errors");
//                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            if (response.getBoolean("success")) {


                                //getting the token and saving in SharedPrefs
                                token = response.getString("token");

//                                SharedPreferences loginPref = getSharedPreferences(BaseActivity.APP_PREFERENCES, MODE_PRIVATE);


                                SharedPreferences.Editor editor = loginPrefs.edit();
                                editor.putString(BaseActivity.GAME_PREFERENCES_TOKEN, token);
                                editor.commit();


                                Toast.makeText(getApplicationContext(), "Авторизация успешна", Toast.LENGTH_LONG).show();
                                progressView.stop();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                            }else{

                                if(!response.getString("errors").isEmpty()){
                                    String errors = response.getString("errors");
                                    Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_SHORT).show();
                                    progressView.stop();
                                }
                                //user needs to confirm the phone number
                                if (response.getString("redirect").equals("PhoneConfirmation")) {
                                    progressView.stop();
                                    startActivity(new Intent(LoginActivity.this, PhoneConfirmationActivity.class));

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

//                        NetworkResponse response = error.networkResponse;

                        String errors = error.getMessage();
                        Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_LONG).show();
                        progressView.stop();
                    }
                }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
