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
                loginRequest(username.getText().toString(),password.getText().toString());

            }
        });

        final Button skipButton = (Button) findViewById(R.id.skipButton); //Deze button skipt het login process om te kunnen werken aan de rest
        skipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, NFCActivity.class)); //gaat naar nfc screen die dan naar speech screen gaat
            }
        });
    }

    private void loginRequest(String username, String password){
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);

            Api api = new Api();
            api.request(this, "/login", jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{

                        if(!response.isNull("code")) {
                            Integer errorCode = response.getInt("code");
                            switch (Api.Errors.fromInteger(errorCode)) {
                                case USER_NOT_FOUND:
                                    loginToast("User not found");
                                    break;
                                case NO_RECORDS_FOUND:
                                    break;
                                case ERROR_NOT_FOUND:
                                    break;
                                case FOUR_O_FOUR:
                                    break;
                                case ECONNREFUSED:
                                    loginToast("Connection refused");
                                    break;
                            }
                        }
                        String loginSuccess = response.getString("login");
                        if(loginSuccess=="true"){
                            setAccountId(response.getInt("id"));
                            getAccountInfo(response.getInt("id"));
                        }
                        else
                            loginToast("Login Failed");
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void getAccountInfo(int accountId){
        try {
            Api api = new Api();
            api.request(this, "/employee/"+accountId,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setEmployeeJSON(response);
                    startActivity(new Intent(LoginActivity.this, NFCActivity.class));
                }
            });
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    protected void loginToast(String text){Toast.makeText(this , text,Toast.LENGTH_LONG).show();}
    private void setAccountId(int accountId){((Ecd)this.getApplication()).setAccountId(accountId);}
    private void setEmployeeJSON(JSONObject json){((Ecd)this.getApplication()).setEmployeeJSON(json);}
}

