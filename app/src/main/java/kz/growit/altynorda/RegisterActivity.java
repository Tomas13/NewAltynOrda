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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.CompoundButton;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private TextView messageRegister;
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


    private android.widget.CompoundButton.OnCheckedChangeListener listener = new android.widget.CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                switch (buttonView.getId()) {
                    case R.id.privateCheckRegister:
                        AccountType = "1";
                        privateCheckBoxRegister.setChecked(true);
                        agentCheckBoxRegister.setChecked(false);
                        agencyCheckBoxRegister.setChecked(false);
                        break;
                    case R.id.agentCheckRegister:
                        AccountType = "2";
                        privateCheckBoxRegister.setChecked(false);
                        agentCheckBoxRegister.setChecked(true);
                        agencyCheckBoxRegister.setChecked(false);
                        break;
                    case R.id.agencyCheckRegister:
                        AccountType = "3";
                        privateCheckBoxRegister.setChecked(false);
                        agentCheckBoxRegister.setChecked(false);
                        agencyCheckBoxRegister.setChecked(true);
                        break;
                }
            }
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        messageRegister = (TextView) findViewById(R.id.messageRegister);
        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        progressView = (ProgressView) findViewById(R.id.progressRegisterView);
        privateCheckBoxRegister = (CheckBox) findViewById(R.id.privateCheckRegister);
        agencyCheckBoxRegister = (CheckBox) findViewById(R.id.agencyCheckRegister);
        agentCheckBoxRegister = (CheckBox) findViewById(R.id.agentCheckRegister);



        privateCheckBoxRegister.setChecked(true);
        AccountType = "1";

        privateCheckBoxRegister.setOnCheckedChangeListener(listener);
        agencyCheckBoxRegister.setOnCheckedChangeListener(listener);
        agentCheckBoxRegister.setOnCheckedChangeListener(listener);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
   // showDialog();
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
                progressView.start();
//                Toast.makeText(RegisterActivity.this, AccountType, Toast.LENGTH_SHORT).show();
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
//                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty()) { //&& !password.isEmpty()) {

                    //progressView.start();


                    registrationPostRequest(name, email, AccountType);


                } else {
                    progressView.stop();
                    messageRegister.setText("Заполните все поля!");
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

                        try {
                            if (response.getBoolean("success")){
                               // hideDialog();
                                progressView.stop();
                                Toast.makeText(RegisterActivity.this, "Вы успешно зарегистрированы. Ждите СМС с кодом " +
                                        "подтверждения", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(
                                        RegisterActivity.this,
                                        PhoneConfirmationActivity.class));
                            }else{
                                progressView.stop();
                                String error = response.getString("errors");
                                messageRegister.setText(error);
                                Snackbar.make(findViewById(R.id.LLRegister), error, Snackbar.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressView.stop();
                        //finish();

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
}
