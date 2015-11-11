package kz.growit.altynorda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ProgressView progressView;
    private EditText phoneForgotPassword;
    private Button btnSendPhoneForgotPassword;
    private String UserName;
    private String serverMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        progressView = (ProgressView) findViewById(R.id.progressForgotPasswordView);
        phoneForgotPassword = (EditText) findViewById(R.id.phoneForgotPassword);
        btnSendPhoneForgotPassword = (Button) findViewById(R.id.btnSendPhoneForgotPassword);

        btnSendPhoneForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserName = phoneForgotPassword.getText().toString().trim();
                progressView.start();
                forgotPasswordRequest(UserName);


            }
        });


    }


    private void forgotPasswordRequest(String UserName) {

        String url = "http://altynorda.kz/SGAccountAPI/ForgotPassword?UserName="+UserName;

        JSONObject data = new JSONObject();
        try {
            data.put("UserName", UserName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                serverMessage = response.getString("redirect");
                                Toast.makeText(getApplicationContext(), serverMessage, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                progressView.stop();
                            } else {
                                String errorMessage = response.getString("errors");
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                                progressView.stop();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            progressView.stop();
                            Toast.makeText(getApplicationContext(), serverMessage, Toast.LENGTH_LONG).show();
                        }
                });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

//        Toast.makeText(getApplicationContext(), serverMessage, Toast.LENGTH_LONG).show();


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
}

