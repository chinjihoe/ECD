package hr.ecd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 De login activity
 */
public class LoginActivity extends AppCompatActivity{

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        // Enable debugging
        ((Ecd)this.getApplication()).setDebugging(true);
        //


        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = (EditText)findViewById(R.id.usernameInputText);
        password = (EditText)findViewById(R.id.passwordInputText);

        buttonListeners();
    }

    private void buttonListeners(){
        final Button loginButton = (Button) findViewById(R.id.loginButton); //als login succesvol is, moet het doorgaan naar de volgende page.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(username.getText().toString(),password.getText().toString());

            }
        });

        final Button skipButton = (Button) findViewById(R.id.skipButton); //Deze button skipt het login process om te kunnen werken aan de rest
        skipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,NFCActivity.class)); //gaat naar nfc screen die dan naar speech screen gaat
            }
        });
    }

    private void sendRequest(String username, String password){
        final String URL = "http://80.57.4.176:523/login";
        final JSONObject jsonBody;
        try{
            jsonBody = new JSONObject("{\"username\":\""+username+"\",\"password\":\""+password+"\"}");

            JsonObjectRequest jsonReq = new JsonObjectRequest(URL, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                VolleyLog.v("Response:%n %s", response.toString(4));
                                String loginSuccess = response.getString("login");
                                if(loginSuccess=="true")
                                    startActivity(new Intent(LoginActivity.this,DossierActivity.class));
                                else
                                    loginToast("Login Failed");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.v("ERROR:%n %s", error.getMessage());
                }
            });

            // Adding request to volley request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonReq);
        }
        catch (Exception e){

        }
    }



    protected void loginToast(String text){
        Toast.makeText(this , text,Toast.LENGTH_LONG).show();
    }
}

