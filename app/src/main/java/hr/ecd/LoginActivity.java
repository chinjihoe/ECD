package hr.ecd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 De login activity
 */
public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        // Enable debugging
        ((Ecd)this.getApplication()).setDebugging(true);
        //


        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button loginButton = (Button) findViewById(R.id.loginButton); //als login succesvol is, moet het doorgaan naar de volgende page.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PopupActivity.class)); //voor nu zal het altijd een Failure popup geven
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
}
