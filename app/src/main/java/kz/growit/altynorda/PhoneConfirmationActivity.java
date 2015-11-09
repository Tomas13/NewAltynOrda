package kz.growit.altynorda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneConfirmationActivity extends AppCompatActivity {

    private ProgressView progressView;
    private EditText phoneConfirmationCode;
    private Button btnSendPhoneConfirmation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);

        progressView = (ProgressView) findViewById(R.id.progressPhoneConfirmationView);
        phoneConfirmationCode = (EditText) findViewById(R.id.phoneConfirmationCode);



        btnSendPhoneConfirmation = (Button) findViewById(R.id.btnSendPhoneConfirmation);
        btnSendPhoneConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = phoneConfirmationCode.getText().toString();
                phoneConfirmationRequest(code);
            }
        });

    }


    private void phoneConfirmationRequest(String code){
        progressView.start();
        String url = "http://altynorda.kz/SGAccountAPI/PhoneConfirmation";
        JSONObject data = new JSONObject();
        try {
            data.put("PhoneConfirmationCode", code);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")){
                                progressView.stop();
                                Toast.makeText(PhoneConfirmationActivity.this, "Ваш код подтвержден! " +
                                        "Добро пожаловать в Алтын Орду! " +
                                        "Пожалуйста выполните ВХОД в систему", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PhoneConfirmationActivity.this, LoginActivity.class));
                            }else{
                                String errorMessage = response.getString("errors");
                                progressView.stop();
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(getApplicationContext(), "server response is : " + response, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(), ""+ response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Toast.makeText(getApplicationContext(), "server response is : " + error, Toast.LENGTH_LONG).show();

                    }
                });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
