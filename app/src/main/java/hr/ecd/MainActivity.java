package hr.ecd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
    Een activity die na de splash gebeurd en voor de login screen
    Hier kan gecheckt worden of de persoon al ingelogd is zodat de loginscreen overgeslagen kan worden
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}