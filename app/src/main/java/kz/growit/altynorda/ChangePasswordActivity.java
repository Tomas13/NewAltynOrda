package kz.growit.altynorda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ChangePasswordActivity extends AppCompatActivity {
    private ProgressView progressView;
    private EditText OldPassword;
    private EditText ConfirmNewPassword;

    private EditText NewPassword;
    private Button btnChangePassword;
    private EditText UserNameChangePassword;
    private JSONObject data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        progressView = (ProgressView) findViewById(R.id.progressChangePasswordView);
        OldPassword = (EditText) findViewById(R.id.OldPassword);
        NewPassword = (EditText) findViewById(R.id.NewPassword);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        UserNameChangePassword = (EditText) findViewById(R.id.UserNameChangePassword);
        ConfirmNewPassword = (EditText) findViewById(R.id.ConfirmNewPassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = UserNameChangePassword.getText().toString().trim();
                String oldPassword = OldPassword.getText().toString().trim();
                String newPassword = NewPassword.getText().toString().trim();
                String confirmNewPassword = ConfirmNewPassword.getText().toString().trim();

                changePasswordRequest(oldPassword, newPassword, userName, confirmNewPassword);
            }
        });
    }

    private void changePasswordRequest(String OldPassword, String NewPassword, String UserName, String confirmNewPassword) {

        data = new JSONObject();

        try {
            data.put("UserName", UserName);
            data.put("OldPassword", OldPassword);
            data.put("NewPassword", NewPassword);
            data.put("ConfirmPassword", confirmNewPassword);
            data.put("Token", "a2aec220-de97-459e-828a-9ec18d490d6a");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressView.start();
        String url = "http://altynorda.kz/SGAccountAPI/ChangePassword";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        if (response.equals("OK")) {
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        }
                        try {
                            if (response.get("success").equals("true")) {
                                startActivity(new Intent(ChangePasswordActivity.this, PhoneConfirmationActivity.class));

                                Toast.makeText(getApplicationContext(), "server response is : " + response, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressView.stop();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Toast.makeText(getApplicationContext(), "server response is : " + error, Toast.LENGTH_LONG).show();
                        String json = null;

                        NetworkResponse response = error.networkResponse;
                        //if (response != null && response.data != null) {
//                            switch (response.statusCode) {
//                                case 400:
                        String erro = error.getMessage();
                        byte[] token = response.data;
                        Toast.makeText(getApplicationContext(), token.length, Toast.LENGTH_LONG).show();
                        //json = trimMessage(json, "message");
                        if (json != null) {
                            Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
                        }
//                                    break;
                        progressView.stop();
                    }
                    //}
                });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}

